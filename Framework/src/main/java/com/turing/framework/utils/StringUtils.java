package com.turing.framework.utils;

import android.text.TextUtils;

/**
 * 字符工具
 * Created by YSL on 2017/4/11.
 */

public class StringUtils {

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (TextUtils.isEmpty(str) || "null".equals(str)){
            return true;
        }
        return false;
    }

    /**
     * 去除String的.0
     * @return
     */
    public static String strRemoveDot(Object object){
        String str = String.valueOf(object);
        if (isEmpty(str)) return "";
        if (str.endsWith(".0")){
            str = str.substring(0,str.length()-2);
        }
        return str;
    }

}
