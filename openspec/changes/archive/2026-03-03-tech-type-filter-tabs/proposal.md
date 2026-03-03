## Why

技改任务页当前使用「类型」下拉框筛选，选项需点击展开才能看到，类型一览性差。将类型改为上方平铺的 Tab，用户无需展开即可看到全部类型并一键切换，提升筛选效率与界面清晰度。

## What Changes

- 技改任务页的**类型筛选**由下拉列表（`<select>`）改为**上方平铺的 Tab**：展示「全部」及各技改类型（漏洞修复、组件版本升级、数据库规范整改、数据库性能优化、其他），当前选中 Tab 高亮，点击切换即筛选列表。
- 筛选逻辑与 API 不变：仍按 `type` 参数请求 `GET /api/tech-improvement/tasks?type=xxx`，仅前端控件从 dropdown 改为 tabs。
- 「刷新」按钮可保留在 Tab 右侧或与 Tab 同一行，位置可随布局微调。

## Capabilities

### New Capabilities

- （无：本次仅修改现有技改目录页的筛选控件形态。）

### Modified Capabilities

- `tech-improvement-catalog`: 类型筛选的呈现方式由“下拉”改为“上方平铺 Tab”；需求层面补充：类型筛选 SHALL 以横向 Tab 形式展示，便于一眼看到所有类型并切换。

## Impact

- **代码**：`platform/src/main/resources/static/tech.html` 中替换类型筛选区域 HTML/CSS/JS（由 `<select>` + label 改为 Tab 结构，样式与高亮状态），可选在 `common.css` 中增加 `.tabs` 相关样式供复用。
- **API**：无变更；仍使用现有 `GET /api/tech-improvement/tasks` 与 `GET /api/tech-improvement/tasks/types`。
- **兼容**：仅前端展示变化，无破坏性。
