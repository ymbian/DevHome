## Why

当前页面顶部主题文案视觉层级不足，缺少首页应有的品牌感和吸引力。需要将“欢迎来到开发者之家DevHome”升级为更强识别度的主题头图，以提升第一屏体验和整体质感。

## What Changes

- 优化顶部主题区域为首页级视觉模块，突出“欢迎来到开发者之家DevHome”。
- 主题文案改为居中显示，提升主视觉聚焦。
- 明显放大字体并增强字重、字距，使其具备主标题感。
- 使用蓝紫渐变背景、阴影卡片、圆角和更大留白，强化品牌氛围与吸引力。
- 保持三大页面（开发、技改、工作计划）主题样式一致，避免视觉割裂。

## Capabilities

### New Capabilities
- `homepage-theme-banner`: 定义首页主题头图的统一视觉规范（居中、放大、渐变、阴影、圆角、留白）并在核心页面一致呈现。

### Modified Capabilities
- `tech-dev-integration`: 扩展页面一致性要求，新增跨页面统一主题头图样式约束。

## Impact

- Affected code:
  - `platform/src/main/resources/static/common.css`
  - `platform/src/main/resources/static/index.html`
  - `platform/src/main/resources/static/tech.html`
  - `platform/src/main/resources/static/work-plan.html`
- API/Backend:
  - 无新增后端接口或数据库变更。
- Dependencies:
  - 无新增第三方依赖。
- Risks:
  - 过强视觉可能影响可读性；需控制字号与对比度并保持移动端可读。
