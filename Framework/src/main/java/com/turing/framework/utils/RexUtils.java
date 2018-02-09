package com.turing.framework.utils;

import android.text.TextUtils;

/**
 * 正则判断
 * Created by YSL on 2017/5/20.
 */

public class RexUtils {
    /**
     * 是否为合法手机号
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        String telRegex = "[1][345789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number))
            return false;
        else
            return number.matches(telRegex);
    }
}
