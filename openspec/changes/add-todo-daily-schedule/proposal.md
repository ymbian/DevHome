## Why

当前平台只有“开发”和“技改”两个入口，缺少对每日工作计划的可视化总览。需要一个一眼可读的待办事项模块，按 0-23 点展示日程，并用颜色和图标直接反映完成与超时状态，先用 mock 数据验证交互与信息架构。

## What Changes

- 在左侧导航新增“待办事项”入口，位于“开发”“技改”之后。
- 新增待办事项页面，按小时（00:00-23:00）展示当日计划。
- 页面展示三种状态：未完成、已完成（绿色对勾）、超时未完成（红色高亮）。
- 使用前端 mock 数据渲染完整日程视图，不依赖后端接口和数据库。
- 统一导航和页面样式，保证与现有静态页面体验一致。

## Capabilities

### New Capabilities
- `todo-daily-schedule`: 提供每日 24 小时工作计划可视化视图，支持基于当前时间展示“已完成/超时未完成”状态，便于快速掌握当天执行情况。

### Modified Capabilities
- `tech-dev-integration`: 扩展全站导航能力，新增“待办事项”入口并保持与现有模块一致的导航行为。

## Impact

- Affected code:
  - `platform/src/main/resources/static/` 下新增待办事项页面与脚本逻辑。
  - 现有 `index.html`、`tech.html`、`design.html`（以及其他含侧边导航的静态页面）需要补充导航链接。
  - `platform/src/main/resources/static/shell.js` 需要扩展导航高亮规则。
- API/Backend:
  - 无新增后端 API（本期使用前端 mock 数据）。
- Dependencies:
  - 无新增第三方依赖。
- Risk:
  - 主要风险是多页面导航一致性遗漏；通过统一检查所有静态页面规避。
