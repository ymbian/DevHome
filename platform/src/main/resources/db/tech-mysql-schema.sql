
-- =========================================================
-- 2) 主流程表：pipeline_instance
--    对应实体：PipelineInstance
-- =========================================================
CREATE TABLE IF NOT EXISTS pipeline_instance (
                                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                                 instance_id VARCHAR(64) NOT NULL,
    requirement_text TEXT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    status VARCHAR(32) NOT NULL,
    change_name VARCHAR(128) DEFAULT NULL,
    design_path VARCHAR(512) DEFAULT NULL,
    change_path VARCHAR(512) DEFAULT NULL,
    design_content TEXT,
    PRIMARY KEY (id),
    UNIQUE KEY uk_pipeline_instance_instance_id (instance_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Pipeline 实例';

-- =========================================================
-- 3) 主流程表：tech_improvement_task
--    对应实体：TechImprovementTask
-- =========================================================
CREATE TABLE IF NOT EXISTS tech_improvement_task (
                                                     id BIGINT NOT NULL AUTO_INCREMENT,
                                                     type VARCHAR(32) NOT NULL,
    title VARCHAR(512) NOT NULL,
    description TEXT,
    priority VARCHAR(16) DEFAULT NULL,
    status VARCHAR(32) NOT NULL,
    source VARCHAR(128) DEFAULT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    pipeline_instance_id VARCHAR(64) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY idx_tech_task_type (type),
    KEY idx_tech_task_status (status),
    KEY idx_tech_task_pipeline_instance_id (pipeline_instance_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='技改任务主表';

-- =========================================================
-- 4) 技改按类型分表（你当前 V1__init_table.sql 的内容）
-- =========================================================
CREATE TABLE IF NOT EXISTS tech_task_vulnerability_fix (
                                                           id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                           application_name VARCHAR(255) DEFAULT NULL COMMENT '应用名',
    issue_description TEXT COMMENT '问题/描述',
    deadline DATE DEFAULT NULL COMMENT '整改截止时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='漏洞修复';

CREATE TABLE IF NOT EXISTS tech_task_component_upgrade (
                                                           id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                           application_name VARCHAR(255) DEFAULT NULL COMMENT '应用名',
    issue_description TEXT COMMENT '问题/描述',
    deadline DATE DEFAULT NULL COMMENT '整改截止时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件升级';

CREATE TABLE IF NOT EXISTS tech_task_database_compliance (
                                                             id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                             application_name VARCHAR(255) DEFAULT NULL COMMENT '应用名',
    issue_description TEXT COMMENT '问题/描述',
    deadline DATE DEFAULT NULL COMMENT '整改截止时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库规范';

CREATE TABLE IF NOT EXISTS tech_task_database_performance (
                                                              id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                              application_name VARCHAR(255) DEFAULT NULL COMMENT '应用名',
    issue_description TEXT COMMENT '问题/描述',
    deadline DATE DEFAULT NULL COMMENT '整改截止时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库性能';

CREATE TABLE IF NOT EXISTS tech_task_other (
                                               id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                               application_name VARCHAR(255) DEFAULT NULL COMMENT '应用名',
    issue_description TEXT COMMENT '问题/描述',
    deadline DATE DEFAULT NULL COMMENT '整改截止时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='其他';
