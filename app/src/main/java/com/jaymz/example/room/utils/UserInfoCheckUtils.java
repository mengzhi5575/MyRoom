package com.jaymz.example.room.utils;

public class UserInfoCheckUtils {
    /**
     * 检验用户名是否有效
     *
     * @param userName
     * @return
     */
    public static boolean validateUserName(String userName) {
        if (userName == null || userName.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean validateUserAge(int userAge) {
        if (userAge < 0 || userAge > 150) {
            return false;
        }
        return true;
    }
}
