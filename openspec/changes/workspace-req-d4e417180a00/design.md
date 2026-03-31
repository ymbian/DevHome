# 架构设计

## 背景

工作台发送按钮触发后端编排流程，先执行 openspec 生成 proposal，再基于 proposal 和需求调用智谱或本地兜底逻辑生成 design。

## 目标

- 围绕需求核心诉求完成方案设计：信息技术部架构评审结论PDF提取并写入MySQL数据库需求说明书
- 可维护、可扩展。

## 总体方案

1. 前端工作台提交需求文本到后端。
2. 后端创建实例并执行 openspec change/proposal 初始化。
3. 后端将需求与 proposal 发送给智谱生成 design.md。
4. 后端将生成的 design.md 落盘并返回给前端展示。

## 模块设计

- 工作台请求接入模块；openspec 提案执行模块；智谱设计生成模块；设计结果展示模块；需求文档解析模块；

## 接口设计

- POST /api/workspace/send 提交需求并触发全流程；GET /api/workspace/instances/{id} 查询处理结果；

## 数据与状态

- 实例状态建议包含：SUBMITTED、PROPOSAL_GENERATED、DESIGN_GENERATED、FAILED。
- 关键字段包含：instanceId、requirementText、changeName、changePath、proposalContent、designContent。

## 风险与待确认项

- openspec CLI 需已安装并可从 PATH 调用。
- 智谱 API Key 未配置时将回退为模板生成。
- 文档内容过长时需要控制 prompt 大小。
