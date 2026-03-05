## Why

DevHome 当前前端为朴素白底蓝钮风格，缺乏科技感与高端产品气质，不利于建立「专业开发平台」的品牌认知。优化视觉与交互可提升可信度与使用意愿，且不改变现有功能行为。

## What Changes

- **全局视觉升级**：在保持现有布局与功能的前提下，引入更科技、更高端的视觉语言（配色、字体、圆角、阴影、渐变、微动效）。
- **公共样式**：在 `common.css` 中扩展/新增 CSS 变量与组件样式（按钮、卡片、侧栏、表单、主内容区），统一「科技感」风格。
- **页面级一致性**：首页、设计页、技改页、工作计划等页面复用公共样式，局部可做页面专属微调（如设计页的 spec 开关、Git 操作区）。
- **无破坏性**：不删除或重命名现有 class/结构，仅增强样式与可选动效；现有 API 与 HTML 结构保持不变。

## Capabilities

### New Capabilities

- `frontend-tech-premium-ui`: 定义并实现「科技感、高端感」的全局前端视觉规范与公共样式（配色、排版、组件、动效），并应用到各静态页。

### Modified Capabilities

- （无。本次仅视觉与样式增强，不改变现有 spec 的功能需求。）

## Impact

- **受影响代码**：`platform/src/main/resources/static/common.css`；各 HTML 页（如 `index.html`、`design.html`、`tech.html`、`work-plan.html` 等）可能增加 class 或小幅结构调整以挂载新样式。
- **依赖**：无新增运行时依赖；仅 CSS（及可选少量内联或现有 JS 驱动的 class 切换）。
- **系统**：仅前端静态资源与样式，后端 API 与路由不变。
