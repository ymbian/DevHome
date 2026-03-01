## 1. MySQL 数据源与依赖

- [x] 1.1 在 platform 中增加 MySQL 驱动依赖（如 mysql-connector-j 或 spring-boot-starter-data-jpa 使用 MySQL）；配置数据源（URL、用户名、密码），可与 H2 并存为多数据源或替换技改列表所用数据源
- [x] 1.2 约定或配置技改类型与 MySQL 表名映射（如 VULNERABILITY_FIX → tech_task_vulnerability_fix）；若表尚未存在，提供建表 SQL 或迁移脚本（字段至少：id, application_name, issue_description, deadline）

## 2. 按类型分表查询与 API

- [x] 2.1 为每种技改类型定义实体或统一实体+表名映射，对应 MySQL 表字段：应用名、整改问题描述、整改截止时间（及 id 等）；实现按 type 选择对应表/Repository 的查询逻辑
- [x] 2.2 技改列表接口（GET /api/tech-improvement/tasks?type=xxx）改为从 MySQL 对应表查询，返回 DTO 列表，每项包含 id、applicationName（应用名）、issueDescription（整改问题描述）、deadline（整改截止时间）及可选 type；type 为空或「全部」时的行为约定（空列表或聚合多表）
- [x] 2.3 保持 GET /api/tech-improvement/tasks/types 返回类型列表不变，供前端 Tab 使用

## 3. 前端列表展示

- [x] 3.1 技改页任务列表区域改为展示应用名、整改问题描述、整改截止时间；请求列表 API 后渲染上述字段（与现有 Tab 切换逻辑兼容，仅展示结构变化）
- [x] 3.2 若列表项仍保留多选与「处理选中」，确保返回的 id 与后端派发或后续逻辑兼容（可选本变更内实现派发对接 MySQL 记录，或留待后续）

## 4. 验证

- [x] 4.1 在 MySQL 中准备各类型表及测试数据；切换「漏洞修复」「组件版本升级」等 Tab，确认列表来自对应表且展示应用名、整改问题描述、整改截止时间正确
- [x] 4.2 确认现有类型 Tab、刷新按钮、新增任务（若保留）与列表展示无回归
