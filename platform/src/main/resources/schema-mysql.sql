-- MySQL schema for platform (run once before first start when using MySQL as primary datasource).
-- Tables match JPA entities: PipelineInstance, TechImprovementTask.

CREATE TABLE IF NOT EXISTS pipeline_instance (
  id BIGINT NOT NULL AUTO_INCREMENT,
  instance_id VARCHAR(64) NOT NULL,
  requirement_text TEXT NOT NULL,
  created_at TIMESTAMP(6) NOT NULL,
  status VARCHAR(32) NOT NULL,
  change_name VARCHAR(128),
  design_path VARCHAR(512),
  change_path VARCHAR(512),
  design_content TEXT,
  PRIMARY KEY (id),
  UNIQUE KEY uk_instance_id (instance_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS tech_improvement_task (
  id BIGINT NOT NULL AUTO_INCREMENT,
  type VARCHAR(32) NOT NULL,
  title VARCHAR(512) NOT NULL,
  description TEXT,
  priority VARCHAR(16),
  status VARCHAR(32) NOT NULL,
  source VARCHAR(128),
  created_at TIMESTAMP(6) NOT NULL,
  pipeline_instance_id VARCHAR(64),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
