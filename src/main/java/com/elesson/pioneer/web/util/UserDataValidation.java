package com.elesson.pioneer.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidation {

    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern NAME = Pattern.compile("^[a-z-]{2,} [a-z-]{2,}$",
            Pattern.CASE_INSENSITIVE);

    public static boolean validEmail(String emailStr) {
        Matcher matcher = EMAIL.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validName(String emailStr) {
        Matcher matcher = NAME.matcher(emailStr);
        return matcher.find();
    }

    public static boolean hasError(HttpServletRequest req, String regName, String regEmail, String regPassword, String confPassword) {
        boolean isError = false;

        if(!validName(regName)) {
            req.setAttribute("errName", true);
            isError = true;
        }
        if(!validEmail(regEmail)) {
            req.setAttribute("errEmail", true);
            isError = true;
        }
        if(!regPassword.equals(confPassword)) {
            req.setAttribute("errPassDif", true);
            isError = true;
        }
        if(regPassword.length() < 4) {
            req.setAttribute("errPassLen", true);
            isError = true;
        }
        return isError;
    }

}
