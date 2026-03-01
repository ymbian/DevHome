package dev.home.platform.web;

import dev.home.platform.domain.TechImprovementTask;
import dev.home.platform.domain.TechImprovementTaskRepository;
import dev.home.platform.domain.TechImprovementType;
import dev.home.platform.service.TechDispatchService;
import dev.home.platform.service.TechImprovementTaskMysqlService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tech-improvement")
public class TechImprovementController {

    private final TechImprovementTaskRepository repository;
    private final TechDispatchService dispatchService;
    private final Optional<TechImprovementTaskMysqlService> mysqlService;

    public TechImprovementController(TechImprovementTaskRepository repository,
                                     TechDispatchService dispatchService,
                                     Optional<TechImprovementTaskMysqlService> mysqlService) {
        this.repository = repository;
        this.dispatchService = dispatchService;
        this.mysqlService = mysqlService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> list(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (mysqlService.isPresent()) {
            List<TechImprovementTaskMysqlService.TechTaskRowDto> rows;
            if (type != null && !type.trim().isEmpty()) {
                TechImprovementType t = parseType(type);
                rows = t != null ? mysqlService.get().listByType(t) : mysqlService.get().listAll();
            } else {
                rows = mysqlService.get().listAll();
            }
            List<TaskDto> body = rows.stream().map(this::fromMysqlRow).collect(Collectors.toList());
            return ResponseEntity.ok(body);
        }
        List<TechImprovementTask> list;
        if (page != null && size != null && page >= 0 && size > 0) {
            Pageable pageable = PageRequest.of(page, size);
            Page<TechImprovementTask> p;
            if (type != null && !type.trim().isEmpty()) {
                TechImprovementType t = parseType(type);
                p = t == null ? repository.findAllByOrderByCreatedAtDesc(pageable)
                        : repository.findByTypeOrderByCreatedAtDesc(t, pageable);
            } else {
                p = repository.findAllByOrderByCreatedAtDesc(pageable);
            }
            list = p.getContent();
        } else {
            if (type != null && !type.trim().isEmpty()) {
                TechImprovementType t = parseType(type);
                if (t == null) list = repository.findAllByOrderByCreatedAtDesc();
                else list = repository.findByTypeOrderByCreatedAtDesc(t);
            } else {
                list = repository.findAllByOrderByCreatedAtDesc();
            }
        }
        List<TaskDto> body = list.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/tasks/types")
    public ResponseEntity<List<Map<String, String>>> types() {
        List<Map<String, String>> out = new ArrayList<Map<String, String>>();
        for (TechImprovementType t : TechImprovementType.values()) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("value", t.name());
            m.put("label", t.getLabel());
            out.add(m);
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> create(@RequestBody TaskCreateRequest req) {
        if (req.getTitle() == null || req.getTitle().trim().isEmpty())
            return ResponseEntity.badRequest().build();
        TechImprovementType t = parseType(req.getType());
        if (t == null) t = TechImprovementType.OTHER;
        TechImprovementTask task = new TechImprovementTask();
        task.setType(t);
        task.setTitle(req.getTitle().trim());
        task.setDescription(req.getDescription() != null ? req.getDescription().trim() : "");
        task.setPriority(req.getPriority() != null ? req.getPriority() : "NORMAL");
        task.setStatus("PENDING");
        task.setSource(req.getSource());
        task = repository.save(task);
        return ResponseEntity.ok(toDto(task));
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body != null ? body.get("status") : null;
        return repository.findById(id)
                .map(task -> {
                    if (status != null) task.setStatus(status);
                    task = repository.save(task);
                    return ResponseEntity.ok(toDto(task));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/dispatch")
    public ResponseEntity<List<TechDispatchService.DispatchResult>> dispatch(@RequestBody Map<String, List<Long>> body) {
        List<Long> taskIds = body != null ? body.get("taskIds") : null;
        if (taskIds == null || taskIds.isEmpty())
            return ResponseEntity.badRequest().build();
        List<TechDispatchService.DispatchResult> results = dispatchService.dispatch(taskIds);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/tasks/batch")
    public ResponseEntity<List<TaskDto>> batchCreate(@RequestBody List<TaskCreateRequest> requests) {
        if (requests == null) return ResponseEntity.ok(new ArrayList<>());
        List<TaskDto> out = new ArrayList<>();
        for (TaskCreateRequest req : requests) {
            if (req.getTitle() == null || req.getTitle().trim().isEmpty()) continue;
            TechImprovementType t = parseType(req.getType());
            if (t == null) t = TechImprovementType.OTHER;
            TechImprovementTask task = new TechImprovementTask();
            task.setType(t);
            task.setTitle(req.getTitle().trim());
            task.setDescription(req.getDescription() != null ? req.getDescription().trim() : "");
            task.setPriority(req.getPriority() != null ? req.getPriority() : "NORMAL");
            task.setStatus("PENDING");
            task.setSource(req.getSource());
            task = repository.save(task);
            out.add(toDto(task));
        }
        return ResponseEntity.ok(out);
    }

    private TaskDto toDto(TechImprovementTask t) {
        TaskDto d = new TaskDto();
        d.setId(t.getId());
        d.setType(t.getType() != null ? t.getType().name() : "");
        d.setTypeLabel(t.getType() != null ? t.getType().getLabel() : "");
        d.setTitle(t.getTitle());
        d.setDescription(t.getDescription());
        d.setApplicationName(t.getTitle());
        d.setIssueDescription(t.getDescription());
        d.setDeadline(null);
        d.setPriority(t.getPriority());
        d.setStatus(t.getStatus());
        d.setCreatedAt(t.getCreatedAt() != null ? t.getCreatedAt().toString() : "");
        d.setPipelineInstanceId(t.getPipelineInstanceId());
        return d;
    }

    private TaskDto fromMysqlRow(TechImprovementTaskMysqlService.TechTaskRowDto r) {
        TaskDto d = new TaskDto();
        d.setId(r.getId());
        d.setType(r.getType());
        d.setTypeLabel(r.getTypeLabel());
        d.setApplicationName(r.getApplicationName());
        d.setIssueDescription(r.getIssueDescription());
        d.setDeadline(r.getDeadline());
        d.setTitle(r.getApplicationName());
        d.setDescription(r.getIssueDescription());
        return d;
    }

    private static TechImprovementType parseType(String s) {
        if (s == null) return null;
        try {
            return TechImprovementType.valueOf(s.toUpperCase().replace("-", "_"));
        } catch (Exception e) {
            return null;
        }
    }

    public static class TaskDto {
        private Long id;
        private String type;
        private String typeLabel;
        private String title;
        private String description;
        private String priority;
        private String status;
        private String createdAt;
        private String pipelineInstanceId;
        /** 应用名 (MySQL per-type table or mapping from title) */
        private String applicationName;
        /** 整改问题描述 (MySQL or mapping from description) */
        private String issueDescription;
        /** 整改截止时间 (MySQL per-type table) */
        private String deadline;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getTypeLabel() { return typeLabel; }
        public void setTypeLabel(String typeLabel) { this.typeLabel = typeLabel; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
        public String getPipelineInstanceId() { return pipelineInstanceId; }
        public void setPipelineInstanceId(String pipelineInstanceId) { this.pipelineInstanceId = pipelineInstanceId; }
        public String getApplicationName() { return applicationName; }
        public void setApplicationName(String applicationName) { this.applicationName = applicationName; }
        public String getIssueDescription() { return issueDescription; }
        public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
        public String getDeadline() { return deadline; }
        public void setDeadline(String deadline) { this.deadline = deadline; }
    }

    public static class TaskCreateRequest {
        private String type;
        private String title;
        private String description;
        private String priority;
        private String source;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }
}
