package com.turing.live.utils;

/**
 * 文字处理工具类
 * Created by YSL on 2017/6/30.
 */

public class TextUtils {

    public static String getHtmlLookCount(String count){
        return "<big><big><big><font color='#239fdc'>" + count + " </font></big></big></big>"
                + "<font color='#8f8f8f'>" + "人在看" + "</font>";
    }
}
