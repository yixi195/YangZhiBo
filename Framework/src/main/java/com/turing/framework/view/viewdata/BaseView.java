package com.turing.framework.view.viewdata;

import android.content.Context;

/**
 * 基本View
 */
public interface BaseView {

    void showLoading(String str);
    void dismissLoading();
    void showToast(String str);
    Context getContext();
}
