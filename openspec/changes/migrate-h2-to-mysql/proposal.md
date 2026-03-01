## Why

平台当前默认使用 H2 文件库作为主数据源，承载流水线实例（pipeline_instance）与技改任务（tech_improvement_task）；技改列表可选用 MySQL 按类型分表。为统一运维、备份与多实例部署，将**所有使用 H2 的持久化改为 MySQL**，使主数据源仅依赖 MySQL，H2 仅保留为可选（如本地无 MySQL 时的开发或测试）。

## What Changes

- **主数据源**：`spring.datasource` 从 H2 改为 MySQL；即 `spring.datasource.url`、`driver-class-name`、`username`、`password` 指向 MySQL，JPA 实体（`PipelineInstance`、`TechImprovementTask`）均在 MySQL 上建表与读写。
- **建表与迁移**：提供 MySQL 建表 DDL（或 Flyway/Liquibase 脚本），包含 `pipeline_instance`、`tech_improvement_task` 表结构，与现有 JPA 字段一致；若有存量 H2 数据需迁移，可提供迁移步骤或脚本（本变更可仅支持“新库建表 + 应用启动”，迁移存量数据为可选）。
- **H2**：从默认主数据源移除；H2 依赖可保留为 `runtime` 可选，或通过 profile 在无 MySQL 时启用 H2（如 `spring.profiles.include=dev-h2`）；生产配置仅使用 MySQL。
- **技改与 MySQL**：若当前已配置 `spring.datasource.mysql.url` 用于技改按类型分表，可与主数据源合并为同一 MySQL 实例（不同 schema 或同库不同表），或保留为第二数据源由产品决定；本变更以“主数据源改为 MySQL、原 H2 表迁至 MySQL”为核心。

## Capabilities

### New Capabilities

- `platform-mysql-primary`: 平台主数据源为 MySQL；流水线实例与技改任务等原 H2 表在 MySQL 上创建与访问；提供配置说明与建表脚本。

### Modified Capabilities

- （无：技改目录、派发等能力不变，仅数据源从 H2 改为 MySQL。）

## Impact

- **配置**：`application.properties` 及环境变量改为 MySQL URL/驱动/账号密码；需提供示例与文档。
- **依赖**：主运行时依赖 MySQL 驱动；H2 改为可选或仅测试/开发 profile。
- **部署**：需事先创建 MySQL 实例与库（及执行建表）；本地开发需安装 MySQL 或使用 Docker/远程库。
- **兼容**：现有 API 与前端无行为变化；仅底层存储从 H2 换为 MySQL。
