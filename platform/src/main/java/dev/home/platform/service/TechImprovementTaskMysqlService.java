package dev.home.platform.service;

import dev.home.platform.domain.TechImprovementType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads tech improvement tasks from MySQL per-type tables.
 * Each type maps to one table: tech_task_vulnerability_fix, tech_task_component_upgrade, etc.
 */
@Service
@ConditionalOnBean(name = "techMysqlJdbcTemplate")
public class TechImprovementTaskMysqlService {

    private static final Map<TechImprovementType, String> TABLE_NAMES = new HashMap<>();

    static {
        TABLE_NAMES.put(TechImprovementType.VULNERABILITY_FIX, "tech_task_vulnerability_fix");
        TABLE_NAMES.put(TechImprovementType.COMPONENT_UPGRADE, "tech_task_component_upgrade");
        TABLE_NAMES.put(TechImprovementType.DATABASE_COMPLIANCE, "tech_task_database_compliance");
        TABLE_NAMES.put(TechImprovementType.DATABASE_PERFORMANCE, "tech_task_database_performance");
        TABLE_NAMES.put(TechImprovementType.OTHER, "tech_task_other");
    }

    private final JdbcTemplate jdbcTemplate;

    public TechImprovementTaskMysqlService(@Qualifier("techMysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TechTaskRowDto> listByType(TechImprovementType type) {
        String table = TABLE_NAMES.get(type);
        if (table == null) return new ArrayList<>();
        String sql = "SELECT id, application_name, issue_description, deadline FROM " + table + " ORDER BY id DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs, type));
    }

    public List<TechTaskRowDto> listAll() {
        List<TechTaskRowDto> out = new ArrayList<>();
        for (TechImprovementType type : TechImprovementType.values()) {
            List<TechTaskRowDto> list = listByType(type);
            for (TechTaskRowDto dto : list) {
                dto.setType(type.name());
                dto.setTypeLabel(type.getLabel());
            }
            out.addAll(list);
        }
        out.sort((a, b) -> Long.compare(b.getId() != null ? b.getId() : 0L, a.getId() != null ? a.getId() : 0L));
        return out;
    }

    private static TechTaskRowDto mapRow(ResultSet rs, TechImprovementType type) throws SQLException {
        TechTaskRowDto d = new TechTaskRowDto();
        d.setId(rs.getLong("id"));
        d.setApplicationName(rs.getString("application_name"));
        d.setIssueDescription(rs.getString("issue_description"));
        if (rs.getObject("deadline") != null) {
            Object deadline = rs.getObject("deadline");
            if (deadline instanceof java.sql.Date) {
                d.setDeadline(((java.sql.Date) deadline).toLocalDate().toString());
            } else {
                d.setDeadline(deadline.toString());
            }
        }
        d.setType(type.name());
        d.setTypeLabel(type.getLabel());
        return d;
    }

    public static class TechTaskRowDto {
        private Long id;
        private String applicationName;
        private String issueDescription;
        private String deadline;
        private String type;
        private String typeLabel;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getApplicationName() { return applicationName; }
        public void setApplicationName(String applicationName) { this.applicationName = applicationName; }
        public String getIssueDescription() { return issueDescription; }
        public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
        public String getDeadline() { return deadline; }
        public void setDeadline(String deadline) { this.deadline = deadline; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getTypeLabel() { return typeLabel; }
        public void setTypeLabel(String typeLabel) { this.typeLabel = typeLabel; }
    }
}
