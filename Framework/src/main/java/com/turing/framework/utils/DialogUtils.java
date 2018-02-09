package com.turing.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Window;

import com.turing.framework.R;
import com.turing.framework.view.LoadingDialog;

/**
 * 等待提示框
 * Created by huang on 2017/5/23.
 */

public class DialogUtils {

    private LoadingDialog load;
    private Context mContext;

    public DialogUtils(Context context){
        this.mContext = context;
    }

    /**
     * 统一耗时操作Dialog
     */
    public void showLoading(String txt) {
        if (load == null) {
            load = new LoadingDialog(mContext, R.style.dialog);
            load.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (load.isShowing()) {
            load.dismiss();
        }
        if (!TextUtils.isEmpty(txt))
            load.setTitle(txt);
        if (mContext instanceof Activity && ((Activity)mContext).isFinishing()) {
            return;
        }
        load.show();
    }

    /**
     * 关闭Dialog
     */
    public void dismissLoading() {
        if (mContext instanceof Activity && ((Activity)mContext).isFinishing()) {
            return;
        }
        if (load != null && load.isShowing()) {
            load.dismiss();
        }
    }


}
