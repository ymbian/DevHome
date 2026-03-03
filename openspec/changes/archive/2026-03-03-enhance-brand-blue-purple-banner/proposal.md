## Why

当前顶部主题条已经具备基础样式，但品牌蓝紫表达还不够“炫”，文字清晰度和吸引力仍有提升空间。需要进一步强化主题头图的品牌辨识度和首屏冲击力。

## What Changes

- 将顶部主题条升级为更鲜明的品牌蓝紫风格，增强视觉吸引力。
- 优化文字清晰度：提高对比度、阴影质量和可读性。
- 保持主题文案居中与主标题层级，强化首页主题感。
- 统一三个核心页面（开发、技改、工作计划）的头图视觉一致性。
- 在不引入新依赖的前提下，通过共享 CSS 完成样式升级。

## Capabilities

### New Capabilities
- `brand-blue-purple-theme-banner`: 提供品牌蓝紫主题头图样式规范，强调“更炫”和高可读性并存的首页视觉表达。

### Modified Capabilities
- `tech-dev-integration`: 扩展跨页面主题一致性要求，新增品牌蓝紫视觉质量基线。

## Impact

- Affected code:
  - `platform/src/main/resources/static/common.css`
  - `platform/src/main/resources/static/index.html`
  - `platform/src/main/resources/static/tech.html`
  - `platform/src/main/resources/static/work-plan.html`
- API/Backend:
  - 无新增接口、无数据库改动。
- Dependencies:
  - 无新增第三方依赖。
- Risks:
  - 过强视觉可能影响正文聚焦；需平衡阴影强度与对比度。
