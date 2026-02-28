# 需求到编码平台 (Requirement-to-Code Platform)

为软件公司提供从**需求描述**到**设计文档**、**openspec 规范**、再到 **Cursor 编码**的流水线平台。

- **技术栈**: Java 8、Maven 3.9、Spring Boot 2.7.4
- **运行**: 本地 Web 应用，前端静态页 + 后端 API，数据持久化到 H2 文件库

## 功能概览

1. **需求输入**: 在首页输入框提交自然语言需求，自动创建流水线实例并持久化。
2. **设计生成**: 根据需求生成 Markdown 设计文档（可配置模板，可选 LLM），支持查看与编辑。
3. **openspec 集成**: 在配置的工作目录下调用 `openspec new change <name>`，写入 proposal、design、specs、tasks，可复用已有 change。
4. **编码就绪**: 制品就绪后展示「运行 /opsx:apply」及 change 名称、tasks 路径；提供任务与上下文 API 供 Cursor Agent 使用。
5. **流水线**: 状态机（已录入 → 设计已生成 → 制品就绪 → 编码就绪），支持从某步重跑与失败重试。

## 配置

在 `src/main/resources/application.properties` 或环境变量中配置：

| 配置项 | 说明 | 默认 |
|--------|------|------|
| `platform.openspec-work-dir` | openspec 工作目录（仓库根路径，如 DevHome） | `.` |
| `platform.llm-endpoint` | 可选，设计生成用 LLM API 地址 | 未配置则用模板 |
| `platform.cursor-api-endpoint` | 可选，Cursor API 地址 | 未配置则仅提供任务清单 |

环境变量示例（Spring Boot 宽松绑定）:

- `PLATFORM_OPENSPEC_WORK_DIR=/path/to/DevHome`
- `PLATFORM_LLM_ENDPOINT=http://localhost:11434/v1`

## 本地运行

1. 安装 [openspec CLI](https://openspec.dev) 并确保 `openspec` 在 PATH 中（启动时会在日志中检查）。
2. 进入本模块目录，使用 **Maven 3.9** 编译并启动：

   ```bash
   mvn spring-boot:run
   ```

3. 浏览器访问: http://localhost:8080

流程建议：

1. 在首页输入需求并提交 → 得到实例 ID。
2. 点击该实例的「设计」→ 在设计页点击「生成设计」→ 可编辑后「保存」。
3. 点击「运行 openspec」→ 在配置的 `platform.openspec-work-dir` 下会创建 `openspec/changes/rtc-<instanceId>/` 并写入 proposal、design、specs、tasks。
4. 页面会显示「编码就绪」及指引；在 Cursor 中对该仓库执行 **`/opsx:apply`** 或指定变更名进行实现。

## 与 Cursor 配合使用

- 制品就绪后，平台会显示变更名与 `tasks.md` 路径。
- 在 Cursor 中打开本仓库，执行 **`/opsx:apply`**（或 `/opsx:apply <change-name>`）即可按 tasks 实施。
- 如需给 Agent 提供完整上下文，可调用 `GET /api/instances/{id}/context` 获取 requirement、design、tasksMd、proposalMd、designMd 等字段。

## API 摘要

- `GET /api/status` — openspec 是否可用、工作目录、LLM/Cursor 是否配置
- `POST /api/requirements` — 提交需求（body: `{ "text": "..." }`）
- `GET /api/requirements` — 需求列表
- `GET /api/requirements/{id}` — 需求详情
- `GET/PUT /api/instances/{id}/design` — 获取/保存设计内容
- `POST /api/instances/{id}/design/generate` — 生成设计
- `POST /api/instances/{id}/openspec/run` — 创建/更新 openspec change 并写入制品
- `GET /api/instances/{id}/coding-guide` — 编码就绪状态与指引
- `GET /api/instances/{id}/context` — 任务与上下文（供 Cursor Agent）

## 许可证

与 DevHome 仓库一致。
