package com.example.admin.jjypznglxt.utils;

import android.text.TextUtils;

/**
 * Created by hzn on 2015/9/29.
 */
public class Util {

    private static char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};

    /**
     *
     *手机号码验证
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        return str.matches(("^1[345678]\\d{9}$"));
    }

    public static int pwsStronger(String pws){
        char[] chars = pws.toCharArray();
        int result = 0;
        for (char c : chars){
            for (int i = 0; i < numbers.length; i++){
                if(numbers[i] == c){
                    result = 1;
                }
            }
        }
        return 0;
    }
}
