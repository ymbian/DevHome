## Context

DevHome 当前已有 openspec 工作流（proposal → design → specs → tasks）与 Cursor 集成能力。软件公司希望在一个统一入口输入自然语言需求，自动得到设计文档、openspec 规范与可交由 Cursor 执行的编码步骤。约束：openspec CLI 已安装；Cursor 集成方式以官方/文档支持的 API 或约定为准；首版可优先本地或单机部署。**技术栈**：Java 8、Maven 3.9、Spring Boot 2.7.4（后端）；前端可为静态页面或与 Spring Boot 同仓的简单 Web 资源。

## Goals / Non-Goals

**Goals:**

- 提供单一入口（如 Web 输入框）录入需求描述并持久化。
- 根据需求描述生成可编辑的设计文档（格式与模板可配置）。
- 在流水线中调用 openspec 创建/更新 change 并生成 proposal、design、specs、tasks 等制品。
- 在规范就绪后触发或驱动 Cursor 编码（通过 API 或生成任务清单供 Agent 执行）。
- 将需求→设计→openspec→编码编排为可顺序执行、可查看状态的流水线。

**Non-Goals:**

- 不替代 openspec 或 Cursor 的既有用法，仅作为编排与调用方。
- 不承诺实现 Cursor 官方未暴露的私有 API；若无稳定 API，则通过任务清单/文档驱动人工在 Cursor 中执行。
- 首版不要求多租户、权限体系或生产级高可用。

## Decisions

1. **前端形态**  
   - **选择**：先采用本地 Web 应用（前端可为静态页面或简单 SPA），后端采用 Spring Boot 2.7.4，部署在开发机或内网。  
   - **理由**：快速验证流水线价值，避免首版就做云上与权限；Java 8 + Spring Boot 与既有企业技术栈一致。  
   - **备选**：纯 CLI 脚本也可作为补充入口，与 Web 共享同一套后端或脚本逻辑。

2. **设计文档生成**  
   - **选择**：用 LLM（如 Cursor 内置模型或可配置 API）根据需求描述生成设计文档，输出为 Markdown，模板与章节可配置。  
   - **理由**：自然语言→结构化设计是核心价值，LLM 可复用现有环境。  
   - **备选**：若暂不接入 LLM，可先做固定模板 + 占位，由用户手填，后续再接入生成。

3. **openspec 集成方式**  
   - **选择**：后端/脚本通过子进程调用 `openspec` CLI（`new change`、`status`、以及按 schema 生成/更新 proposal、design、specs、tasks 的流程）。  
   - **理由**：与现有 openspec 工作流一致，无需改 openspec 本体。  
   - **备选**：若未来 openspec 提供 Node/程序化 API，可替换为库调用以减少 shell 依赖。

4. **Cursor 编码触发**  
   - **选择**：优先「任务清单 + 文档」驱动：平台生成 tasks.md 与上下文文档，用户或 Cursor Agent 在 Cursor 中按任务执行；若存在稳定 Cursor API（如 Composer/Agent 触发），再增加「一键触发」能力。  
   - **理由**：避免依赖未公开 API，保证可维护性。  
   - **备选**：若 Cursor 后续提供官方 API，再增加直接调用。

5. **流水线状态与数据**  
   - **选择**：每个「需求单」对应一个流水线实例，状态包括：已录入、设计已生成、openspec change 已创建、各制品（proposal/design/specs/tasks）就绪、已触发/已交付编码。  
   - **理由**：状态清晰便于重试、跳过某步和排查。  
   - **存储**：首版可用本地文件或 SQLite；若未来有多用户再考虑数据库。

6. **技术栈（固定）**  
   - **选择**：后端使用 **Java 8**、**Maven 3.9**、**Spring Boot 2.7.4**；构建与依赖管理统一用 Maven。  
   - **理由**：与公司或团队既定技术栈一致，便于维护与部署；Java 8 与 Spring Boot 2.7.x 兼容性成熟。  
   - **约束**：实现与依赖须在此版本范围内；不升级到 Java 11+ 或 Spring Boot 3.x 除非后续单独变更。

## Risks / Trade-offs

- **[依赖 openspec CLI]** → 要求环境已安装且版本兼容；文档中明确说明安装与版本要求，必要时在 UI 中做「检查 openspec」自检。
- **[Cursor 无稳定 API]** → 以任务清单与文档驱动为主，不承诺全自动编码；若后续有 API 再迭代。
- **[LLM 生成质量]** → 设计文档与 openspec 内容可编辑；提供「重新生成」与人工修正流程，避免错误直接进入代码。
- **[单机/本地部署]** → 首版不解决多机与高可用；后续若有需求再拆服务与状态存储。

## Migration Plan

- 在 DevHome 内新增独立模块或子项目（如 `platform/` 或 `requirement-to-code/`），不修改现有 openspec 配置与 Cursor 规则。技术栈：Java 8、Maven 3.9、Spring Boot 2.7.4。
- 部署：本地运行 Spring Boot 应用（+ 前端静态资源），配置 openspec 路径与（可选）LLM 端点。
- 回滚：新模块可整体移除，不影响既有 openspec changes 与 Cursor 使用。

## Open Questions

- Cursor 是否提供可被外部调用的 Composer/Agent API，以及调用约定（鉴权、参数格式）。
- 设计文档与 openspec proposal/design 的重复度与同步策略（是否由平台直接写 proposal/design 文件以保持一致性）。
