package net.pfiers.ipm_minor_ex1.util;

import org.thymeleaf.util.StringUtils;

public abstract class ThymeleafUtil {
    public static String multilineEscape(String text) {
        var escaped = StringUtils.escapeXml(text);
        return escaped.replace("\n", "<br />");
    }
}
