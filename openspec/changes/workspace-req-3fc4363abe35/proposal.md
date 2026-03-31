## Why

信息技术部架构评审结论PDF提取并写入MySQL数据库需求说明书

## What Changes

- 围绕需求核心诉求完成方案设计：信息技术部架构评审结论PDF提取并写入MySQL数据库需求说明书
- 工作台请求接入模块；openspec 提案执行模块；智谱设计生成模块；设计结果展示模块；需求文档解析模块；
- POST /api/workspace/send 提交需求并触发全流程；GET /api/workspace/instances/{id} 查询处理结果；

## Impact

- 影响范围以需求文档中涉及的业务模块、接口、数据对象和处理流程为准。
- 待确认项与风险：- openspec CLI 需已安装并可从 PATH 调用。
- 智谱 API Key 未配置时将回退为模板生成。
- 文档内容过长时需要控制 prompt 大小。
