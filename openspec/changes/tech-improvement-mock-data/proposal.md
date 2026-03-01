## Why

技改任务列表页在无数据时仅显示「暂无任务」，不利于演示与联调。为便于体验筛选、列表展示与界面效果，需要在不依赖外部 MySQL 或真实数据的前提下，为页面提供一批 mock/示例数据。

## What Changes

- 在**未配置 MySQL**（列表来自 H2）时，应用启动或首次访问前**预置若干条技改任务**，覆盖各类型（漏洞修复、组件版本升级、数据库规范整改、数据库性能优化、其他），每条包含应用名、整改问题描述、整改截止时间（或等效字段）。
- 预置方式：启动时若 H2 中技改任务表为空，则执行一次插入；或提供独立的数据初始化脚本/组件，不改变现有 API 与前端逻辑。
- 使用 MySQL 时可不预置（数据由业务维护）；仅 H2 场景下填充 mock 数据。

## Capabilities

### New Capabilities

- `tech-improvement-demo-data`: 技改任务列表在无真实数据时可展示预置的示例数据（mock），便于演示与开发联调；预置仅在使用内置存储（如 H2）且表为空时执行。

### Modified Capabilities

- （无）

## Impact

- **代码**：platform 内新增数据初始化逻辑（如 `CommandLineRunner`、`ApplicationRunner` 或 `@PostConstruct` 中判断表空后插入若干条 `TechImprovementTask`，或使用 `data.sql` / Flyway 等）；需与现有 H2 实体字段一致（title/description 等，若 DTO 有 applicationName/issueDescription/deadline 则预置数据与之对应）。
- **数据**：H2 库中会多出若干条示例记录；不影响已有数据。
- **API/前端**：无变更；列表接口与页面逻辑不变，仅返回数据非空。
