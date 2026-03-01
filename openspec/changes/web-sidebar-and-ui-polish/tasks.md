## 1. 共享资源与 Shell 结构

- [x] 1.1 新增 `common.css`（或 `shell.css` + `components.css`），定义侧栏宽度、主内容区 margin、配色变量（可选）、基础字体与间距
- [x] 1.2 在 `common.css` 中定义侧栏样式：背景、边框或阴影、导航链接与 active 高亮样式
- [x] 1.3 新增共享 JS 片段（内联或独立文件）：根据 `location.pathname` 为当前页对应的侧栏项添加 active class

## 2. 各页接入 Shell

- [x] 2.1 修改 `index.html`：插入左侧边栏 HTML（开发 / 技改链接），主内容包在 main 容器内，引入 `common.css` 与高亮脚本
- [x] 2.2 修改 `tech.html`：插入相同侧栏与主内容结构，引入 `common.css` 与高亮脚本
- [x] 2.3 修改 `design.html`：插入相同侧栏，保留现有设计页内容在 main 区，引入 `common.css` 与高亮脚本（设计页高亮规则：可按 pathname 为「开发」或统一「设计」项高亮）

## 3. 视觉与组件统一

- [x] 3.1 在 `common.css` 中统一按钮样式（padding、圆角、背景、hover），并让三页的按钮使用统一类或继承
- [x] 3.2 统一表单控件（input、select、textarea）样式：边框、圆角、内边距
- [x] 3.3 统一列表/卡片样式（如技改任务项、历史记录项）：间距、边框或卡片阴影、圆角
- [x] 3.4 移除或替换各页与统一风格冲突的局部样式，确保三页观感一致

## 4. 验证

- [x] 4.1 手工验证：从首页经侧栏进入技改、进入设计页，再返回，确认侧栏高亮正确且链接有效
- [x] 4.2 确认现有 URL（`/`、`/tech.html`、`/design.html?id=xxx`）与 API 行为未变
