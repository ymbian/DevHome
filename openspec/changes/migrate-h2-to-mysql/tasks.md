## 1. 依赖与配置

- [x] 1.1 确认 platform 的 pom.xml 包含 MySQL 驱动（mysql-connector-j 或 mysql:mysql-connector-java），主数据源不再默认使用 H2
- [x] 1.2 将 application.properties 默认数据源改为 MySQL：设置 spring.datasource.url、driver-class-name、username、password 的示例或占位，并注释/移除 H2 的 url 与 driver
- [x] 1.3 在 README 或配置说明中补充：如何配置 MySQL、如何（可选）使用 dev-h2 profile 启用 H2 做本地开发

## 2. MySQL 建表脚本

- [x] 2.1 新增 MySQL 建表 DDL（如 platform/src/main/resources/schema-mysql.sql 或 db/migration）：创建 pipeline_instance 表，字段与 PipelineInstance 实体一致（id、instance_id、requirement_text、created_at、status、change_name、design_path、change_path、design_content）
- [x] 2.2 在同一 DDL 或迁移中创建 tech_improvement_task 表，字段与 TechImprovementTask 实体一致（id、type、title、description、priority、status、source、created_at、pipeline_instance_id）
- [x] 2.3 确保 CLOB 在 MySQL 中映射为 TEXT/LONGTEXT，VARCHAR 长度与实体注解一致

## 3. 数据源与 H2

- [x] 3.1 移除默认配置中 H2 的 spring.datasource.url 与 driver-class-name，确保未激活 H2 profile 时不会连接 H2
- [x] 3.2 （可选）新增 profile（如 dev-h2）及对应 application-dev-h2.properties，在无 MySQL 时启用 H2 数据源；或将 H2 依赖改为 test scope，仅测试使用

## 4. 验证

- [ ] 4.1 使用 MySQL 配置启动应用，执行建表脚本后验证启动成功（本地先创建库、执行 `schema-mysql.sql`，再 `mvn spring-boot:run`）
- [ ] 4.2 验证流水线实例的创建与查询走 MySQL（pipeline_instance 表）
- [ ] 4.3 验证技改任务的创建与查询走 MySQL（tech_improvement_task 表）；若保留技改 MySQL 第二数据源，确认主库与第二数据源行为符合预期
