## 1. 项目与配置

- [x] 1.1 在 DevHome 下创建平台模块目录（如 `platform/` 或 `requirement-to-code/`），使用 Maven 3.9 初始化 Spring Boot 2.7.4 项目（Java 8）
- [x] 1.2 添加配置文件（或 env）支持：openspec 工作目录、可选 LLM 端点、可选 Cursor API 等
- [x] 1.3 实现 openspec CLI 可用性检查（如 `openspec --version` 或 which），并在 UI 或启动时暴露结果

## 2. 需求输入 (requirement-input)

- [x] 2.1 实现需求描述输入控件（如多行输入框 + 提交按钮）并接入前端
- [x] 2.2 实现需求持久化：提交后将文本与唯一 pipeline 实例 id 存入本地存储或 SQLite
- [x] 2.3 实现需求列表/历史：可列出过往提交（id、摘要、时间），并支持选择查看全文或基于其重新运行流水线

## 3. 设计文档生成 (design-generation)

- [x] 3.1 定义设计文档模板（Markdown 章节结构）并支持配置或替换
- [x] 3.2 实现设计生成逻辑：根据需求文本（+ 可选 LLM）生成设计内容并写入文件或记录，与 pipeline 实例关联
- [x] 3.3 实现设计文档的查看与编辑界面（或链接到外部编辑器），保存后更新关联的设计 artifact

## 4. openspec 集成 (openspec-integration)

- [x] 4.1 实现根据 pipeline 实例生成 change 名称（kebab-case），并在配置的工作目录下调用 `openspec new change <name>` 创建变更
- [x] 4.2 实现将 proposal 与 design 内容写入 change 目录下 proposal.md、design.md，并保证 `openspec status` 能正确识别
- [x] 4.3 实现按 proposal 中 Capabilities 创建 specs/<capability>/spec.md（或调用 openspec 流程生成），并生成或更新 tasks.md 使 change 达到 apply-ready
- [x] 4.4 若 change 已存在则复用：根据实例 id/name 使用 `openspec status --change <name>` 等命令更新制品而非重复创建

## 5. Cursor 编码衔接 (cursor-integration)

- [x] 5.1 在 openspec 制品就绪后，生成并暴露「编码就绪」状态与指引（如「运行 /opsx:apply」、change 名称、tasks 路径）
- [x] 5.2 实现将 tasks.md 与相关上下文（design、proposal）汇总为可被用户或 Cursor Agent 使用的任务清单与文档入口
- [x] 5.3 （可选）若配置了 Cursor API：实现调用该 API 触发编码步骤；未配置时仅依赖任务清单与文档，不依赖未公开 API

## 6. 流水线编排 (pipeline-orchestration)

- [x] 6.1 实现流水线状态机：已录入 → 设计已生成 → change 已创建 → 各制品就绪 → 编码已就绪；支持失败暂停与重试
- [x] 6.2 实现流水线状态持久化与展示：当前/最后完成步骤、成功/失败、设计与 change 路径、任务链接
- [x] 6.3 实现「从某步重新运行」：用户可选择从设计生成、openspec 或后续步骤重跑，使用当前实例数据
- [x] 6.4 保证每次新提交需求创建新 pipeline 实例，且每个实例对应唯一 change 名称或 id

## 7. 集成与文档

- [x] 7.1 端到端联调：从输入需求到生成设计、openspec 制品、再到「编码就绪」提示可一气跑通
- [x] 7.2 编写 README 或使用说明：如何配置 openspec 路径与可选 LLM/Cursor API、如何本地运行、如何与 Cursor 配合使用（如 /opsx:apply）
