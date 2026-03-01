## Context

平台（Spring Boot 2.7）当前以 H2 文件库为默认主数据源（`spring.datasource.url=jdbc:h2:file:./data/platform`），JPA 实体 `PipelineInstance`、`TechImprovementTask` 均落在 H2 上。另有可选 MySQL 配置（`spring.datasource.mysql.*`）仅用于技改列表按类型分表读取。为统一生产数据源、便于备份与多实例部署，需将主数据源从 H2 迁至 MySQL，H2 仅作可选（如本地无 MySQL 时）。

## Goals / Non-Goals

**Goals:**

- 主数据源改为 MySQL：`spring.datasource` 指向 MySQL，所有 JPA 实体在 MySQL 上建表与读写。
- 提供 MySQL 建表 DDL（或 Flyway/Liquibase 脚本），与现有实体字段一致。
- 从默认运行时移除 H2；H2 可保留为可选 profile（如 `dev-h2`）供本地无 MySQL 时使用。
- 配置示例与 README 更新，说明如何配置 MySQL 及（可选）H2 profile。

**Non-Goals:**

- 本设计不强制迁移存量 H2 数据；可仅支持“新库 + 建表 + 启动”，存量迁移为可选后续任务。
- 技改“按类型分表”的 MySQL 第二数据源是否与主库合并，由实现/产品决定；设计上允许主库即 MySQL 单实例。

## Decisions

1. **单主数据源 MySQL**
   - 使用 Spring 标准 `spring.datasource.*` 配置 MySQL（url、driver-class-name、username、password）。
   - JPA/Hibernate 继续管理 `PipelineInstance`、`TechImprovementTask` 表；`ddl-auto` 建议 `validate` 或 `update`（生产建议 `validate` + 显式 DDL 脚本）。
   - **替代方案**：保留 H2 为默认、MySQL 为可选——未采纳，因目标为“将使用 H2 的地方改为 MySQL”。

2. **MySQL 表结构**
   - `pipeline_instance`：id (BIGINT PK AUTO_INCREMENT)、instance_id (VARCHAR(64) UNIQUE NOT NULL)、requirement_text (TEXT)、created_at (TIMESTAMP)、status (VARCHAR(32))、change_name、design_path、change_path、design_content (TEXT) 等，与现有实体一致。
   - `tech_improvement_task`：id、type (VARCHAR(32) 对应枚举)、title、description (TEXT)、priority、status、source、created_at、pipeline_instance_id 等，与现有实体一致。
   - 提供独立 DDL 脚本（如 `schema-mysql.sql`）便于先建库再启动；CLOB 在 MySQL 用 TEXT/LONGTEXT。

3. **H2 处理**
   - 从默认 `application.properties` 中移除 H2 的 url/driver；主配置仅含 MySQL 或占位说明。
   - H2 依赖可保留在 pom.xml，通过 profile（如 `dev-h2`）在无 MySQL 时激活 H2 数据源；或改为 test scope。决策：保留可选 profile，便于本地开发。

4. **技改 MySQL 第二数据源**
   - 当前 `TechMysqlConfig` 在存在 `spring.datasource.mysql.url` 时创建第二数据源，用于按类型分表。主数据源改为 MySQL 后，可选择：同一实例下主库 + 技改分表（不同 schema 或同库），或继续保留第二数据源指向同一/不同 MySQL。本设计不强制合并，实现时若同实例可简化配置。

## Risks / Trade-offs

- **本地开发依赖 MySQL**：开发者需本机 MySQL 或 Docker/远程库；通过可选 H2 profile 缓解。
- **驱动与版本**：使用已有 `mysql-connector-j`，确保与 Spring Boot 2.7 兼容；若存在时区问题，URL 参数可加 `serverTimezone=UTC` 等。
- **CLOB 映射**：H2 的 CLOB 与 MySQL TEXT/LONGTEXT 一致，无需改实体注解；若需限制长度可统一为 `columnDefinition = "TEXT"` 或 `LONGTEXT`。

## Migration Plan

1. 添加/确认 MySQL 驱动依赖；默认 `application.properties` 改为 MySQL 配置（或占位 + 文档）。
2. 提供 `schema-mysql.sql`（或 Flyway/Liquibase migration），在目标库执行建表。
3. 若有存量 H2 数据需迁移：可后续提供导出/导入脚本；本变更可不包含。
4. 移除默认 H2 url/driver；可选：增加 `application-dev-h2.properties` 与 profile `dev-h2` 启用 H2。
5. 启动验证：应用以 MySQL 为主数据源启动，流水线与技改 CRUD 正常。

## Open Questions

- 是否在本变更内提供 H2 → MySQL 的数据迁移脚本（例如导出 CSV/SQL 再导入），还是仅“新库建表”？（当前按“仅新库建表”推进。）
