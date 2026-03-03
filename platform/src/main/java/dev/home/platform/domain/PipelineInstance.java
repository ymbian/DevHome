package dev.home.platform.domain;

import javax.persistence.*;
import java.time.Instant;

/**
 * One pipeline instance per requirement submission.
 * Unique id and optional change name for openspec.
 */
@Entity
@Table(name = "pipeline_instance")
public class PipelineInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64, unique = true)
    private String instanceId;

    @Lob
    @Column(nullable = false)
    private String requirementText;

    @Column(nullable = false)
    private Instant createdAt;

    /** Pipeline state: SUBMITTED, DESIGN_GENERATED, CHANGE_CREATED, ARTIFACTS_READY, CODING_READY, FAILED */
    @Column(nullable = false, length = 32)
    private String status = "SUBMITTED";

    /** Openspec change name (kebab-case) when created */
    @Column(length = 128)
    private String changeName;

    /** Path to design artifact (file or stored content) */
    @Column(length = 512)
    private String designPath;

    /** Path to openspec change dir */
    @Column(length = 512)
    private String changePath;

    /** Generated design document content (Markdown) */
    @Lob
    private String designContent;

    /** Whether latest design generation enabled spec mode. */
    @Column
    private Boolean specModeEnabled;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }

    public String getRequirementText() { return requirementText; }
    public void setRequirementText(String requirementText) { this.requirementText = requirementText; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getChangeName() { return changeName; }
    public void setChangeName(String changeName) { this.changeName = changeName; }

    public String getDesignPath() { return designPath; }
    public void setDesignPath(String designPath) { this.designPath = designPath; }

    public String getChangePath() { return changePath; }
    public void setChangePath(String changePath) { this.changePath = changePath; }

    public String getDesignContent() { return designContent; }
    public void setDesignContent(String designContent) { this.designContent = designContent; }

    public Boolean getSpecModeEnabled() { return specModeEnabled; }
    public void setSpecModeEnabled(Boolean specModeEnabled) { this.specModeEnabled = specModeEnabled; }
}
