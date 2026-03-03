## 1. 类型筛选改为 Tab 结构

- [x] 1.1 在 tech.html 中移除「类型」下拉框（`<select id="typeFilter">` 及 label），改为横向 Tab 容器：一行内展示「全部」+ 各类型（漏洞修复、组件版本升级、数据库规范整改、数据库性能优化、其他），每个 Tab 带 `data-type`（全部为空字符串）
- [x] 1.2 为 Tab 区域添加样式（可在 tech.html 的 `<style>` 或 common.css）：横向排列、间距、默认与选中态（如背景/下划线高亮），与现有 shell 风格一致；「刷新」按钮保留在 Tab 行右侧

## 2. 交互与状态

- [x] 2.1 页面加载时请求 `GET /api/tech-improvement/tasks/types` 动态渲染 Tab（「全部」+ 返回的 type 列表），默认选中「全部」
- [x] 2.2 Tab 点击时：设置当前 Tab 的 active 样式、根据选中 Tab 的 `data-type` 调用现有列表接口并刷新任务列表；与原有 `typeFilter` 变更逻辑一致，仅数据源从 select 改为 tabs
- [x] 2.3 「刷新」按钮仍按当前选中的 type 重新拉取列表，行为不变

## 3. 验证

- [x] 3.1 手工验证：打开技改页，确认类型以 Tab 平铺展示；点击各 Tab 筛选正确；刷新按钮有效；新增任务表单中的类型下拉保留（仅筛选区改为 Tab）
