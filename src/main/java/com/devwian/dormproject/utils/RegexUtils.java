package com.devwian.dormproject.utils;

public class RegexUtils {
    private static String reason;

    public static String getReason() {
        return reason;
    }

    /**
     * id判断
     *
     * @param id id
     * @return {@link Boolean}
     */
    public static Boolean idJudge(String id) {
        if (id.isEmpty()) {
            reason = "用户名不能为空！";
            return false;
        }
        if (id.length() > 20) {
            reason = "用户名不能超过20位！";
            return false;
        }
        if (!id.matches("^[\\w]{3,20}$")) {
            reason = "用户名请使用英文和数字组合！";
            return false;
        }
        return true;
    }

    /**
     * pwd法官
     * 密码判断
     *
     * @param pwd 密码
     * @return {@link Boolean}
     */
    public static Boolean pwdJudge(String pwd) {
        if (pwd.isEmpty()) {
            reason = "密码不能为空！";
            return false;
        }
        if (pwd.length() > 20 || pwd.length() < 6) {
            reason = "密码应为6-20位！";
            return false;
        }
        if (!pwd.matches("^[\\w_]{6,20}$")) {
            reason = "密码请使用字母数字下划线组合！";
            return false;
        }
        return true;
    }

    /**
     * 电话判断
     *
     * @param tel 电话
     * @return boolean
     */
    public static boolean telJudge(String tel) {
        if (tel.isEmpty()) {
            reason = "电话号码不能为空！";
            return false;
        }
        if (tel.length() != 11) {
            reason = "电话长度应为11位！";
            return false;
        }

        return true;
    }
}
