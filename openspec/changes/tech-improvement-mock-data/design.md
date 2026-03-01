## Context

技改任务列表来自 GET /api/tech-improvement/tasks；未配置 MySQL 时数据来自 H2 单表 tech_improvement_task（type, title, description, priority, status 等）。当前无预置数据时列表为空，页面显示「暂无任务」。

## Goals / Non-Goals

**Goals:**

- 在 H2 且技改任务表为空时，自动插入若干条示例任务，覆盖各类型（漏洞修复、组件版本升级、数据库规范整改、数据库性能优化、其他），使列表页有数据可展示。
- 示例数据字段与现有实体一致（title 对应应用名展示、description 对应整改问题描述）；不新增实体字段。
- 不改变 API 与前端；仅数据源多出预置记录。

**Non-Goals:**

- 不修改 MySQL 路径；若已配置 MySQL，不写入 mock 数据。
- 不提供“清空 mock”的专门入口；若需清空可直连 H2 或后续加管理接口。

## Decisions

1. **触发时机**  
   **选择**：应用启动时执行一次检查（如 `CommandLineRunner` 或 `ApplicationRunner`），若 `TechImprovementTaskRepository.count() == 0` 则插入预置数据。  
   **备选**：使用 `data.sql` 或 Flyway — 需保证在 JPA 建表之后执行，且仅执行一次；Runner 更易与“仅当表空”逻辑结合。

2. **预置条数与内容**  
   **选择**：每类型 1～2 条，共约 6～10 条；title 使用中文应用名/场景，description 使用简短整改描述；priority/status 使用合法枚举值。不设置 deadline（H2 实体无该字段，前端显示时为空即可）。  
   **备选**：大量数据 — 不利于快速浏览，故少量即可。

3. **与 MySQL 并存**  
   **选择**：仅当未启用 MySQL 技改数据源时（即无 `TechImprovementTaskMysqlService` 或未配置 `spring.datasource.mysql.url`）才执行 H2 预置；若已配置 MySQL，列表来自 MySQL，不插入 H2。可通过在 Runner 中判断是否存在 MySQL 相关 Bean 或配置决定是否执行插入。

## Risks / Trade-offs

- **重复启动**：若用户清空表后重启，会再次插入 mock 数据 → 可接受，用于演示。
- **与真实数据混合**：若用户先在 H2 中手动删掉部分 mock 再新增真实数据，列表会混合 → 可接受。

## Open Questions

- 无。
