## Context

技改任务页（tech.html）当前使用「类型」下拉框（`<select>`）调用 `GET /api/tech-improvement/tasks?type=xxx` 筛选列表；类型选项来自 `GET /api/tech-improvement/tasks/types`。前端为静态 HTML/CSS/JS，已接入 common.css 与左侧边栏 shell。

## Goals / Non-Goals

**Goals:**

- 将类型筛选从下拉改为**上方平铺的 Tab**：一行内展示「全部」+ 各类型文案，当前项高亮，点击切换即请求对应 type 并刷新列表。
- 保留现有 API 与筛选语义；仅替换控件形态与 DOM 结构。
- 样式与现有 shell/按钮一致（可复用 common 或局部样式）。

**Non-Goals:**

- 不增加新 API、不改变 type 枚举或后端逻辑。
- 不做多选类型筛选（仍为单选：当前 Tab 对应一个 type 或“全部”）。

## Decisions

1. **Tab 结构**  
   使用语义化容器（如 `.type-tabs`）内一组可点击元素（`<a>` 或 `<button>`），`data-type` 或 `data-value` 存 type 值（空表示全部）；点击时设置当前高亮并带当前 type 调用现有 `loadTasks()` 逻辑。  
   **备选**：用 `<select>` 仅改样式——难以做到“平铺”且可访问性不如显式 Tab。

2. **样式**  
   在 tech.html 内增加 `.type-tabs` 样式，或于 common.css 增加通用 `.tabs` 类供技改页使用：横向 flex、间距、默认/active 态（背景、边框或下划线）。与现有 `.btn`、侧栏风格统一。  
   **备选**：完全内联 style——不利于维护，故用 class。

3. **刷新按钮**  
   保留「刷新」按钮，放在 Tab 行右侧或紧挨 Tab 末尾；点击仍执行当前 type 下的列表刷新，逻辑不变。

## Risks / Trade-offs

- **类型数量**：若未来类型增多，Tab 一行放不下可换行或横向滚动；首版类型固定，一行足够。  
- **无迁移**：仅前端替换，无数据与 URL 变更，直接发布即可。

## Open Questions

- 无。
