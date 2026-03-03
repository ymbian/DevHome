## Context

平台技改模块当前从 H2 单表 `TechImprovementTask`（type, title, description, priority, status 等）提供列表，API 为 `GET /api/tech-improvement/tasks?type=xxx`。需求改为：数据来自 MySQL，每种技改类型一张表，每表字段含应用名、整改问题描述、整改截止时间；切换 Tab 时展示对应表数据。

## Goals / Non-Goals

**Goals:**

- 技改任务列表数据源改为 MySQL；按技改类型分表（如漏洞修复、组件升级等各一张表），每表至少包含应用名、整改问题描述、整改截止时间。
- 保留「按 type 筛选」的 API 语义；后端根据 type 查询对应 MySQL 表，返回统一结构的列表（含应用名、整改问题描述、整改截止时间）。
- 前端技改页切换 Tab 时请求该接口并展示上述字段。

**Non-Goals:**

- 本阶段不强制迁移或删除现有 H2 技改表；可与 MySQL 并存（如列表只读来自 MySQL，新增/派发仍用现有逻辑或后续迭代写入 MySQL）。
- 表名与 type 枚举的映射方式（配置 vs 约定）可在实现时选定。

## Decisions

1. **表与 type 映射**  
   **选择**：约定每种技改类型对应一张表，表名可配置或按类型命名（如 `tech_task_vulnerability_fix`、`tech_task_component_upgrade`）；type 枚举值（如 VULNERABILITY_FIX）与表名一一对应。  
   **备选**：单表多 type 列——与「每种类型一张表」需求不符，故不采用。

2. **后端实现方式**  
   **选择**：为每种类型建实体 + JPA Repository 指向对应表，或使用单实体 + `@Table(name = "…")` 动态/多实体；列表 API 根据 type 参数选择对应 Repository 查询并映射为统一 DTO（appName, issueDescription, deadline）。若类型较多，可用泛型或表名配置 + JDBC/JdbcTemplate 按表名查询，减少重复代码。  
   **备选**：纯 JDBC 动态 SQL——可行但需注意注入与表名白名单。

3. **MySQL 与 H2 并存**  
   **选择**：Spring 多数据源或主数据源切 MySQL；技改列表只读来自 MySQL；现有 H2 可保留用于 pipeline/需求等，技改列表接口单独走 MySQL。  
   **备选**：全量迁 MySQL——可后续做，本变更仅保证技改列表读 MySQL。

4. **DTO 与前端字段**  
   **选择**：列表接口返回字段包含 applicationName（应用名）、issueDescription（整改问题描述）、deadline（整改截止时间）及 id、可选 type；前端任务列表区域渲染这三项为主信息，与现有 Tab 筛选逻辑兼容。

## Risks / Trade-offs

- **多数据源**：若同时保留 H2 与 MySQL，需配置 DataSource、事务边界，避免误用数据源。  
- **表结构一致性**：各类型表字段需统一（至少应用名、整改问题描述、整改截止时间），否则 DTO 映射需兼容多结构或按类型分支。  
- **新增/派发**：若后续「新增任务」要写 MySQL，需确定写哪张表；派发需能解析 MySQL 记录 id/类型，本变更可只读列表，写入与派发可后续迭代。

## Migration Plan

1. 配置 MySQL 数据源（URL、用户名、密码、驱动）；可选保留 H2 为 secondary。
2. 创建或对接现有 MySQL 表结构（按类型分表，字段含应用名、整改问题描述、整改截止时间）。
3. 实现按 type 查对应表的逻辑及统一 DTO；将现有 `GET /api/tech-improvement/tasks` 改为从 MySQL 按 type 返回上述字段（或新增专用接口，前端改调新接口）。
4. 前端技改页列表区域改为展示应用名、整改问题描述、整改截止时间；Tab 切换与现有一致。
5. 验证：切换各类型 Tab，确认列表来自对应 MySQL 表且字段正确。

## Open Questions

- 各类型 MySQL 表是否已存在、表名与字段名是否由 DBA 给定；若未建表，是否由本变更在迁移或启动时建表（需与运维约定）。
