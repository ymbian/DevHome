## Why

工作台于 2026-03-19T00:00:41.384Z 提交需求，需要先基于需求正文创建 openspec proposal。

## Requirement

信息技术部架构评审结论PDF提取并写入MySQL数据库需求说明书

1 特性概述[必需]

作为平台用户，我想要自动读取并提取指定PDF文件中的评审信息，以便于将这些信息存储到MySQL数据库中。

2 关联专题衡量指标[按需]

本期暂无明确补充指标，可在后续评审中结合处理成功率、字段提取准确率、入库成功率等指标进一步量化。

3 特性全景

3.1 特性全景描述[必需]

正常流程

	1	用户（系统管理员）触发系统执行文件读取任务。

	2	系统扫描 resources/pdf 文件夹，识别以“信息技术部架构评审结论”开头的PDF文件。

	3	系统使用PDF解析库读取每个符合要求的PDF文件内容。

	4	系统根据预定义的映射关系，提取PDF文件中的字段。

	5	系统将提取的字段数据写入MySQL数据库的相应表中。

分支流程

	1	系统扫描 resources/pdf 文件夹，识别以“信息技术部架构评审结论”开头的PDF文件。

	2	系统使用PDF解析库读取每个符合要求的PDF文件内容。

	3	如PDF内容存在多行值、换行、表格或文本格式不规整情况，系统仍需尽可能按映射规则提取字段。

	4	系统根据预定义的映射关系，提取PDF文件中的字段。

	5	系统将提取的字段数据写入MySQL数据库的相应表中。

系统需要新增的功能分析

	1	文件夹扫描功能

	◦	描述：系统需要能够扫描指定文件夹 resources/pdf，识别并列出所有以“信息技术部架构评审结论”开头的PDF文件。

	◦	目的：确保系统能够找到需要处理的文件。

	2	PDF文件读取功能

	◦	描述：系统需要能够读取扫描到的PDF文件内容，支持常见的PDF格式。

	◦	目的：提取PDF文件中的文本内容，以便进一步处理。

	3	字段提取功能

	◦	描述：系统需要根据预定义的映射关系，从PDF文件中提取所需字段，包括评审编号、评审名称、申请人、申请科室、评审类型、评审专家、评审组长、评审日期、评审简介、评审纪要、待跟踪事项、评审结论、专家建议。

	◦	目的：准确提取PDF文件中的关键信息，确保数据的完整性和准确性。

	4	数据库写入功能

	◦	描述：系统需要能够将提取的字段数据写入MySQL数据库的指定表中，确保数据的持久化存储。

	◦	目的：将提取的数据存入数据库，便于后续查询和分析。

3.2 原型图[按需]

[如涉及体验设计则该项必填，原型图用于展示页面的布局和各个功能的位置及交互]

4 特性说明

4.1 功能说明[必需]

4.1.1 功能名称：文件夹扫描功能

功能描述：

系统需要能够扫描指定文件夹 resources/pdf，识别并列出所有以“信息技术部架构评审结论”开头的PDF文件。该功能确保系统能够找到并定位需要处理的文件。

业务规则：

	1	文件夹路径固定：系统扫描的文件夹路径为 resources/pdf。

	2	文件名前缀匹配：系统需要识别文件名以“信息技术部架构评审结论”开头的PDF文件。

	3	文件类型限制：系统仅识别PDF文件，其他类型文件需要忽略。

4.1.2 功能名称：PDF文件读取和写入MySQL数据库功能

功能描述：

系统需要能够读取扫描到的PDF文件内容，支持常见的PDF格式。该功能用于将PDF文件中的文本内容提取出来，以便进一步进行字段提取和数据处理。

PDF解析逻辑如下：每行有两列，第一列为“评审编号”“评审名称”等键，第二列为对应值；值可能为多行文本，且可能包含表格内容。系统需要基于键值映射关系完成字段提取与数据库落表。

字段映射关系：

	•	评审编号 -> reviewNumber

	•	评审名称 -> reviewName

	•	申请人 -> applicant

	•	申请科室 -> applicantOrg

	•	评审类型 -> reviewType

	•	评审专家 -> reviewExpertList

	•	评审组长 -> groupLeader

	•	评审日期 -> reviewDate

	•	待跟踪项 -> followUpItem

	•	评审结论 -> reviewResult

	•	评审简介 -> reviewSummary

	•	评审纪要 -> reviewMinutes

	•	专家建议 -> expertAdvice

建议MySQL建表语句：

CREATE TABLE arch_review_result (

  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',

  review_number VARCHAR(64) NOT NULL COMMENT '评审编号',

  review_name VARCHAR(255) NOT NULL COMMENT '评审名称',

  applicant VARCHAR(100) DEFAULT NULL COMMENT '申请人',

  applicant_org VARCHAR(255) DEFAULT NULL COMMENT '申请科室',

  review_type VARCHAR(100) DEFAULT NULL COMMENT '评审类型',

  review_expert_list TEXT COMMENT '评审专家',

  group_leader VARCHAR(100) DEFAULT NULL COMMENT '评审组长',

  review_date DATE DEFAULT NULL COMMENT '评审日期',

  follow_up_item LONGTEXT COMMENT '待跟踪项',

  review_result LONGTEXT COMMENT '评审结论',

  review_summary LONGTEXT COMMENT '评审简介',

  review_minutes LONGTEXT COMMENT '评审纪要',

  expert_advice LONGTEXT COMMENT '专家建议',

  source_file_name VARCHAR(255) DEFAULT NULL COMMENT '源PDF文件名',

  source_file_path VARCHAR(500) DEFAULT NULL COMMENT '源PDF文件路径',

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

  PRIMARY KEY (id),

  UNIQUE KEY uk_review_number (review_number)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='架构评审结论提取结果表';

4.2 安全性评估[按需]

请评估是否涉及以下特性功能，如涉及，请勾选。

1. 用户认证与授权

	•	□ 密码认证

	•	□ 短信验证码

	•	□ 人脸识别

	•	□ 用户授权

2. 互联网文件与数据输入输出

	•	□ 文件上传

	•	□ 文件下载

	•	□ 数据输入

3. 敏感数据处理

[3级数据：个人客户数据（姓名、性别、国籍、民族、证件号码、住址、手机、电子邮箱、地理位置、信贷信息等）、单位客户数据（企业信贷信息、管理层或实际控制人员的姓名、证件号码、联系方式等）、业务数据（账户金额、交易金额信息、交易对手信息等）、经营管理数据（源代码、薪资信息等）4级数据：身份鉴别信息（银行卡磁道信息、CVN码、卡片有效期、支付密码、交易密码等）、生物特征信息（人脸、指纹等）、健康生理信息（检验报告、传染病史等）]

	•	□ 采集个人数据

	•	□ 采集3级数据

	•	□ 采集4级数据

	•	□ 会话管理

	•	□ 互联网传输3级数据中的个人信息

	•	□ 传输4级数据

	•	□ 存储4级数据

	•	□ 3级及以上数据展示/打印/下载

结论：以上均不涉及。

注：如涉及以上特性功能，则应根据我行《应用系统安全需求规范》要求，落实特性功能相关联的安全需求。

4.3 非功能需求[按需]

	•	系统应支持批量处理指定目录中的多个PDF文件。

	•	系统应具备基本的异常处理能力，对无法解析的PDF文件进行日志记录。

	•	系统应保证重复执行时不产生重复入库数据，可基于评审编号做唯一性控制。

	•	系统应支持UTF-8/UTF8MB4字符集，保证中文内容完整存储。

	•	系统应在字段缺失或格式异常时保留原始文件信息，便于后续人工核对。

## What Changes

- 新建 change 并写入 proposal.md
- 在后续步骤中使用智谱生成 design.md
- 将 design 内容回显到工作台架构设计阶段
