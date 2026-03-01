package dev.home.platform.domain;

import javax.persistence.*;
import java.time.Instant;

/**
 * Tech improvement task (vulnerability fix, component upgrade, DB compliance, DB performance, etc.).
 */
@Entity
@Table(name = "tech_improvement_task")
public class TechImprovementTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private TechImprovementType type;

    @Column(nullable = false, length = 512)
    private String title;

    @Lob
    private String description;

    @Column(length = 16)
    private String priority = "NORMAL";

    @Column(nullable = false, length = 32)
    private String status = "PENDING";

    @Column(length = 128)
    private String source;

    @Column(nullable = false)
    private Instant createdAt;

    /** Pipeline instance id after dispatch (optional, for link to dev module) */
    @Column(length = 64)
    private String pipelineInstanceId;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TechImprovementType getType() { return type; }
    public void setType(TechImprovementType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getPipelineInstanceId() { return pipelineInstanceId; }
    public void setPipelineInstanceId(String pipelineInstanceId) { this.pipelineInstanceId = pipelineInstanceId; }
}
