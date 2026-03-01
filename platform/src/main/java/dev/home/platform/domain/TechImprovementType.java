package dev.home.platform.domain;

/**
 * Tech improvement task type.
 */
public enum TechImprovementType {

    VULNERABILITY_FIX("漏洞修复"),
    COMPONENT_UPGRADE("组件版本升级"),
    DATABASE_COMPLIANCE("数据库规范整改"),
    DATABASE_PERFORMANCE("数据库性能优化"),
    OTHER("其他");

    private final String label;

    TechImprovementType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
