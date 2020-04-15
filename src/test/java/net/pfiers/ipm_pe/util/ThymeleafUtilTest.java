package net.pfiers.ipm_pe.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThymeleafUtilTest {

    @Test
    void multilineEscapeScript() {
        String str = "<script>alert(1)</script>";
        String expected = "&lt;script&gt;alert(1)&lt;/script&gt;";
        assertEquals(expected, ThymeleafUtil.multilineEscape(str));
    }

    @Test
    void multilineEscapeLb() {
        String str = "line 1\nline 2";
        String expected = "line 1<br />line 2";
        assertEquals(expected, ThymeleafUtil.multilineEscape(str));
    }
}