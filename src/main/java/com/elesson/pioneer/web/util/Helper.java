package com.elesson.pioneer.web.util;

import javax.servlet.http.HttpServletRequest;

public class Helper {

    public static String getBackReference(HttpServletRequest req) {
        String referer = req.getHeader("referer");
        return referer.lastIndexOf("?") != -1 ? referer.substring(referer.lastIndexOf("?")) : "";
    }
}
