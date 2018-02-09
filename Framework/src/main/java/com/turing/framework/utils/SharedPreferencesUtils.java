package com.turing.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.turing.framework.base.BaseApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by kifile on 16/8/16.
 */
public class SharedPreferencesUtils {

    public static final String SP_NAME = "ysl";

    public static String getString(Context context, String key, String def) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(key, def);
    }

    public static void saveString(Context context, String key, String value) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putString(key, value)
                .apply();
    }

    public static int getInt(Context context, String key, int def) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getInt(key, def);
    }

    public static void saveInt(Context context, String key, int value) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putInt(key, value)
                .apply();
    }

    public static long getLong(Context context, String key, long def) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getLong(key, def);
    }

    public static void saveLong(Context context, String key, long value) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putLong(key, value)
                .apply();
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getBoolean(key, def);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value)
                .apply();
    }

    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public static void remove(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
