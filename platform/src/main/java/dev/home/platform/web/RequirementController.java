package dev.home.platform.web;

import dev.home.platform.domain.PipelineInstance;
import dev.home.platform.domain.PipelineInstanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requirements")
public class RequirementController {

    private final PipelineInstanceRepository repository;

    public RequirementController(PipelineInstanceRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<RequirementResponse> submit(@RequestBody RequirementRequest req) {
        if (req.getText() == null || req.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        PipelineInstance p = new PipelineInstance();
        p.setInstanceId("req-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        p.setRequirementText(req.getText().trim());
        p.setCreatedAt(Instant.now());
        p.setStatus("SUBMITTED");
        p = repository.save(p);
        return ResponseEntity.ok(toResponse(p));
    }

    @GetMapping
    public ResponseEntity<List<RequirementSummary>> list() {
        List<RequirementSummary> list = repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toSummary)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequirementResponse> get(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> ResponseEntity.ok(toResponse(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    private RequirementSummary toSummary(PipelineInstance p) {
        RequirementSummary s = new RequirementSummary();
        s.setId(p.getInstanceId());
        s.setExcerpt(p.getRequirementText().length() > 100
                ? p.getRequirementText().substring(0, 100) + "..."
                : p.getRequirementText());
        s.setCreatedAt(p.getCreatedAt().toString());
        s.setStatus(p.getStatus());
        return s;
    }

    private RequirementResponse toResponse(PipelineInstance p) {
        RequirementResponse r = new RequirementResponse();
        r.setId(p.getInstanceId());
        r.setText(p.getRequirementText());
        r.setCreatedAt(p.getCreatedAt().toString());
        r.setStatus(p.getStatus());
        r.setChangeName(p.getChangeName());
        r.setDesignPath(p.getDesignPath());
        r.setChangePath(p.getChangePath());
        r.setDesignContent(p.getDesignContent());
        return r;
    }

    public static class RequirementRequest {
        private String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    public static class RequirementSummary {
        private String id;
        private String excerpt;
        private String createdAt;
        private String status;
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getExcerpt() { return excerpt; }
        public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class RequirementResponse {
        private String id;
        private String text;
        private String createdAt;
        private String status;
        private String changeName;
        private String designPath;
        private String changePath;
        private String designContent;
        public String getDesignContent() { return designContent; }
        public void setDesignContent(String designContent) { this.designContent = designContent; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getChangeName() { return changeName; }
        public void setChangeName(String changeName) { this.changeName = changeName; }
        public String getDesignPath() { return designPath; }
        public void setDesignPath(String designPath) { this.designPath = designPath; }
        public String getChangePath() { return changePath; }
        public void setChangePath(String changePath) { this.changePath = changePath; }
    }
}
