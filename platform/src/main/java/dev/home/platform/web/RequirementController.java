package dev.home.platform.web;

import dev.home.platform.domain.PipelineInstance;
import dev.home.platform.domain.PipelineInstanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Optional<PipelineInstance> opt = repository.findByInstanceId(id);
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.delete(opt.get());
        return ResponseEntity.noContent().build();
    }

    private RequirementSummary toSummary(PipelineInstance p) {
        RequirementSummary s = new RequirementSummary();
        s.setId(p.getInstanceId());
        s.setExcerpt(p.getRequirementText().length() > 100
                ? p.getRequirementText().substring(0, 100) + "..."
                : p.getRequirementText());
        s.setCreatedAt(p.getCreatedAt().toString());
        s.setStatus(p.getStatus());
        applyStageStatus(p, s);
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
        applyStageStatus(p, r);
        return r;
    }

    private void applyStageStatus(PipelineInstance p, StageStatusTarget target) {
        boolean requirement = p.getRequirementText() != null && !p.getRequirementText().trim().isEmpty();
        boolean design = (p.getDesignContent() != null && !p.getDesignContent().trim().isEmpty())
                || "DESIGN_GENERATED".equals(p.getStatus())
                || "ARTIFACTS_READY".equals(p.getStatus())
                || "CODING_READY".equals(p.getStatus());
        boolean coding = "ARTIFACTS_READY".equals(p.getStatus()) || "CODING_READY".equals(p.getStatus());
        boolean specModeEnabled = p.getSpecModeEnabled() != null
                ? p.getSpecModeEnabled().booleanValue()
                : (p.getChangePath() != null && !p.getChangePath().trim().isEmpty());
        target.setRequirement(requirement);
        target.setDesign(design);
        target.setSpecModeEnabled(specModeEnabled);
        target.setCoding(coding);
    }

    private interface StageStatusTarget {
        void setRequirement(boolean requirement);
        void setDesign(boolean design);
        void setSpecModeEnabled(boolean specModeEnabled);
        void setCoding(boolean coding);
    }

    public static class RequirementRequest {
        private String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    public static class RequirementSummary implements StageStatusTarget {
        private String id;
        private String excerpt;
        private String createdAt;
        private String status;
        private boolean requirement;
        private boolean design;
        private boolean specModeEnabled;
        private boolean coding;
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getExcerpt() { return excerpt; }
        public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public boolean isRequirement() { return requirement; }
        public void setRequirement(boolean requirement) { this.requirement = requirement; }
        public boolean isDesign() { return design; }
        public void setDesign(boolean design) { this.design = design; }
        public boolean isSpecModeEnabled() { return specModeEnabled; }
        public void setSpecModeEnabled(boolean specModeEnabled) { this.specModeEnabled = specModeEnabled; }
        public boolean isCoding() { return coding; }
        public void setCoding(boolean coding) { this.coding = coding; }
    }

    public static class RequirementResponse implements StageStatusTarget {
        private String id;
        private String text;
        private String createdAt;
        private String status;
        private String changeName;
        private String designPath;
        private String changePath;
        private String designContent;
        private boolean requirement;
        private boolean design;
        private boolean specModeEnabled;
        private boolean coding;
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
        public boolean isRequirement() { return requirement; }
        public void setRequirement(boolean requirement) { this.requirement = requirement; }
        public boolean isDesign() { return design; }
        public void setDesign(boolean design) { this.design = design; }
        public boolean isSpecModeEnabled() { return specModeEnabled; }
        public void setSpecModeEnabled(boolean specModeEnabled) { this.specModeEnabled = specModeEnabled; }
        public boolean isCoding() { return coding; }
        public void setCoding(boolean coding) { this.coding = coding; }
    }
}
