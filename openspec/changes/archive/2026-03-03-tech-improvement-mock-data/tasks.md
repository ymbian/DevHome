## 1. 预置逻辑与条件

- [x] 1.1 新增启动时执行的数据初始化组件（如 `CommandLineRunner` 或 `ApplicationRunner`）：仅在未配置 MySQL 技改数据源（无 `spring.datasource.mysql.url` 或无 `TechImprovementTaskMysqlService` Bean）且 `TechImprovementTaskRepository.count() == 0` 时执行插入
- [x] 1.2 在上述条件下，向 `TechImprovementTask` 表插入若干条示例任务：覆盖漏洞修复、组件版本升级、数据库规范整改、数据库性能优化、其他等类型，每条设置 type、title（应用名/场景）、description（整改问题描述）、priority、status、createdAt

## 2. Mock 数据内容

- [x] 2.1 为每种技改类型准备 1～2 条示例记录（中文 title 与 description），使切换类型 Tab 时均有数据可展示；总条数约 6～10 条
- [x] 2.2 确认列表 API 与前端无需改动，预置后 GET /api/tech-improvement/tasks 及按 type 筛选返回 mock 数据，页面展示应用名、整改问题描述等

## 3. 验证

- [x] 3.1 清空 H2 技改表或使用全新数据目录启动，确认启动后技改页列表非空且各类型 Tab 有数据
- [x] 3.2 配置 MySQL 技改数据源后启动，确认不会向 H2 插入 mock 数据（列表来自 MySQL）
