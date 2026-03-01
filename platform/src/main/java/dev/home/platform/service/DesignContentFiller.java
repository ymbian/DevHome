package dev.home.platform.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Fills design document sections (目标、设计要点、风险与开放问题) from requirement text.
 * Uses keyword-based heuristics when LLM is not available.
 */
public final class DesignContentFiller {

    private static final Pattern SENTENCE_END = Pattern.compile("[。！？；\\n]");

    /**
     * Generate content for each template placeholder based on requirement.
     */
    public static Map<String, String> fillFromRequirement(String requirement) {
        Map<String, String> out = new HashMap<String, String>();
        if (requirement == null) requirement = "";
        String r = requirement.trim();

        out.put("功能目标", buildFunctionalGoal(r));
        out.put("非功能目标", buildNonFunctionalGoal(r));
        out.put("模块划分", buildModuleDivision(r));
        out.put("关键接口", buildKeyInterfaces(r));
        out.put("技术选型", buildTechSelection(r));
        out.put("风险与开放问题", buildRisks(r));
        return out;
    }

    private static String buildFunctionalGoal(String r) {
        if (r.isEmpty()) return "（请根据需求补充）";
        String first = firstSentence(r);
        if (first.length() > 80) first = first.substring(0, 77) + "...";
        return "基于上述需求，实现：" + first;
    }

    private static String buildNonFunctionalGoal(String r) {
        if (r.isEmpty()) return "无";
        StringBuilder sb = new StringBuilder();
        if (containsAny(r, "发送", "消息", "通知")) sb.append("通知及时、可追溯；");
        if (containsAny(r, "解析", "读取", "邮件")) sb.append("数据解析可靠；");
        if (sb.length() == 0) return "可维护性、可扩展性";
        return sb.toString();
    }

    private static String buildModuleDivision(String r) {
        if (r.isEmpty()) return "（请根据需求补充）";
        StringBuilder sb = new StringBuilder();
        if (containsAny(r, "解析", "饼图", "读取", "邮件")) sb.append("数据解析/读取模块；");
        if (containsAny(r, "通讯录", "负责人", "联系")) sb.append("通讯录/负责人查询模块；");
        if (containsAny(r, "发送", "消息", "通知")) sb.append("消息/通知发送模块；");
        if (containsAny(r, "应用", "异常")) sb.append("应用与异常数据模块；");
        if (sb.length() == 0) sb.append("需求解析模块；业务处理模块；");
        return sb.toString();
    }

    private static String buildKeyInterfaces(String r) {
        if (r.isEmpty()) return "（请根据需求补充）";
        StringBuilder sb = new StringBuilder();
        if (containsAny(r, "解析", "饼图", "读取")) sb.append("解析/读取接口；");
        if (containsAny(r, "通讯录", "负责人")) sb.append("负责人查询接口；");
        if (containsAny(r, "发送", "消息")) sb.append("消息发送接口；");
        if (sb.length() == 0) sb.append("输入解析接口；业务接口；输出接口；");
        return sb.toString();
    }

    private static String buildTechSelection(String r) {
        return "依现有项目技术栈（如 Java 8、Spring Boot）；外部依赖（邮件、短信等）需与现有基础设施一致。";
    }

    private static String buildRisks(String r) {
        StringBuilder sb = new StringBuilder();
        if (containsAny(r, "邮件", "饼图", "格式")) sb.append("- 邮件与图表/饼图格式依赖约定，需与上游一致。\n");
        if (containsAny(r, "通讯录")) sb.append("- 通讯录格式需与现有一致。\n");
        if (containsAny(r, "发送", "消息")) sb.append("- 通知渠道与鉴权需确认。\n");
        if (sb.length() == 0) sb.append("- 待定项与依赖在实现前确认。");
        return sb.toString().trim();
    }

    private static String firstSentence(String s) {
        String[] parts = SENTENCE_END.split(s, 2);
        return parts[0].trim();
    }

    private static boolean containsAny(String text, String... keywords) {
        for (String k : keywords) {
            if (text.contains(k)) return true;
        }
        return false;
    }
}
