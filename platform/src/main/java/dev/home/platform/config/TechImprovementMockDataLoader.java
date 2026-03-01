package dev.home.platform.config;

import dev.home.platform.domain.TechImprovementTask;
import dev.home.platform.domain.TechImprovementTaskRepository;
import dev.home.platform.domain.TechImprovementType;
import dev.home.platform.service.TechImprovementTaskMysqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Seeds mock tech improvement tasks when using H2 and the task table is empty.
 * Skipped when MySQL tech list is configured ({@link TechImprovementTaskMysqlService} is present).
 */
@Component
@Order(100)
@ConditionalOnMissingBean(TechImprovementTaskMysqlService.class)
public class TechImprovementMockDataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TechImprovementMockDataLoader.class);

    private final TechImprovementTaskRepository repository;

    public TechImprovementMockDataLoader(TechImprovementTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (repository.count() > 0) {
            return;
        }
        log.info("Seeding mock tech improvement tasks (H2, empty table).");
        Instant now = Instant.now();
        save(TechImprovementType.VULNERABILITY_FIX, "订单中心", "修复 Log4j2 远程代码执行漏洞，升级至 2.17.0+。", now);
        save(TechImprovementType.VULNERABILITY_FIX, "用户服务", "Spring Framework 反序列化漏洞 CVE-2022-22965，升级依赖并验证。", now);
        save(TechImprovementType.COMPONENT_UPGRADE, "网关", "将 Spring Cloud Gateway 从 3.0.x 升级至 3.1.x，兼容现有路由配置。", now);
        save(TechImprovementType.COMPONENT_UPGRADE, "消息队列客户端", "RocketMQ 客户端升级至 5.x，评估 API 变更与重试策略。", now);
        save(TechImprovementType.DATABASE_COMPLIANCE, "支付库", "表结构与索引符合 DBA 命名与冗余规范，补充缺失注释与分区策略。", now);
        save(TechImprovementType.DATABASE_COMPLIANCE, "日志归档库", "大表增加分区与归档策略，满足数据保留与审计规范。", now);
        save(TechImprovementType.DATABASE_PERFORMANCE, "报表查询", "慢 SQL 优化：订单汇总报表添加合适索引并改写子查询。", now);
        save(TechImprovementType.DATABASE_PERFORMANCE, "搜索服务", "全文检索表碎片整理与统计信息更新，改善查询延迟。", now);
        save(TechImprovementType.OTHER, "监控告警", "接入统一告警平台，配置技改相关指标与通知策略。", now);
    }

    private void save(TechImprovementType type, String title, String description, Instant createdAt) {
        TechImprovementTask t = new TechImprovementTask();
        t.setType(type);
        t.setTitle(title);
        t.setDescription(description);
        t.setPriority("NORMAL");
        t.setStatus("PENDING");
        t.setSource("mock");
        t.setCreatedAt(createdAt);
        repository.save(t);
    }
}
