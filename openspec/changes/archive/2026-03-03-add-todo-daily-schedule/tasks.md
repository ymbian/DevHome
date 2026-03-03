## 1. Navigation integration

- [x] 1.1 在所有包含侧边栏的静态页面中新增“待办事项”导航链接（位于开发、技改之后）
- [x] 1.2 扩展 `shell.js` 导航高亮逻辑，支持 `todo` 路由激活态
- [x] 1.3 手工验证各页面导航一致性与跳转可达性

## 2. Todo page and mock data model

- [x] 2.1 新建 `todo.html` 页面骨架并复用现有样式体系
- [x] 2.2 定义前端 mock 数据结构（至少包含 id、title、startHour、endHour、done）
- [x] 2.3 实现 00-23 全时段渲染（无任务时段也显示）

## 3. Status rendering rules

- [x] 3.1 实现“已完成=绿色对勾”的视觉样式与图标
- [x] 3.2 基于当前时间与结束时间计算“超时未完成=红色”状态
- [x] 3.3 保持未完成且未超时任务为中性待办样式

## 4. UX polish and verification

- [x] 4.1 增加页面说明与图例，确保状态语义一目了然
- [x] 4.2 用 mock 数据覆盖完成、进行中、超时三类场景
- [x] 4.3 完成端到端手测（页面打开、导航跳转、状态显示、全天时间轴完整性）
