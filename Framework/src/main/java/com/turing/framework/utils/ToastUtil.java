package com.turing.framework.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.turing.framework.view.BaseViewHelper;

import java.lang.ref.WeakReference;


public class ToastUtil {

    private static Toast toast = null;
    private static WeakReference<Toast> mShowingToastRef;

    private ToastUtil() {
    }

    public static void show(CharSequence text) {
        if (mShowingToastRef != null) {
            toast = mShowingToastRef.get();
        }
        if (toast == null) {  //保证不重复弹出
            if (text.length() < 10) {
                toast = Toast.makeText(BaseViewHelper.getInstance().getApplicationContext(),text, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(BaseViewHelper.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG);
            }
            mShowingToastRef = new WeakReference<>(toast);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show(@StringRes int resId) {
        show(BaseViewHelper.getInstance().getApplicationContext().getString(resId));
    }

}