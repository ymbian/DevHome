## 1. 技改任务数据与 API

- [x] 1.1 新增实体 TechImprovementTask（type, title, description, priority, status, source 等）及 JPA Repository
- [x] 1.2 定义技改类型枚举或配置：漏洞修复、组件版本升级、数据库规范整改、数据库性能优化、其他
- [x] 1.3 实现技改任务 CRUD API（列表、筛选按 type、创建、更新状态）及可选批量导入/录入接口

## 2. 技改目录与筛选

- [x] 2.1 实现 GET /api/tech-improvement/tasks 支持按 type 筛选与分页
- [x] 2.2 技改任务列表返回字段包含 type、title、description、priority、status，便于前端展示

## 3. 派发与开发模块编排

- [x] 3.1 实现派发服务：根据选中的技改任务生成需求描述（标题+描述+类型），调用 POST /api/requirements 创建 pipeline 实例
- [x] 3.2 派发服务对创建的 instanceId 依次调用 POST /api/instances/{id}/design/generate 与 POST /api/instances/{id}/openspec/run
- [x] 3.3 支持单条派发与多选派发（每条技改对应一个 pipeline 实例）；派发结果返回 instanceId 列表及成功/失败信息
- [x] 3.4 技改任务与 pipeline 实例关联落库（如 task 上记录 instanceId 或单独关联表），便于从技改跳转到开发实例

## 4. 技改模块前端

- [x] 4.1 新增技改入口（导航或路由，如 /tech 或 /tech.html），页面展示技改任务列表
- [x] 4.2 列表支持按类型筛选（漏洞修复、组件升级、数据库规范、数据库性能等）
- [x] 4.3 支持多选任务与「处理」按钮；处理时调用派发 API，成功后展示结果并提供跳转到开发模块设计页（/design.html?id=xxx）的链接
- [x] 4.4 处理失败时展示错误信息并支持重试

## 5. 开发模块可调用性

- [x] 5.1 确认现有 POST /api/requirements、POST /api/instances/{id}/design/generate、POST /api/instances/{id}/openspec/run 可被后端/内部调用（无 CSRF 或仅 UI 限制），必要时为派发调用放宽或增加内部调用路径
- [x] 5.2 首页或导航中区分「开发」与「技改」入口，明确开发模块定位

## 6. 集成与文档

- [ ] 6.1 端到端验证：录入或导入技改任务 → 筛选 → 选择处理 → 自动创建 pipeline、生成设计、运行 openspec → 跳转至编码就绪
- [x] 6.2 在 README 或使用说明中补充技改模块用法与技改类型说明
