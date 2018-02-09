package com.turing.framework.view;

import android.content.Context;

import com.turing.framework.base.IActivityHelper;
import com.turing.framework.base.IBaseRequirement;
import com.turing.framework.base.IFragmentHelper;

/**
 * 给这个框架提供应用环境,
 * Created by huang on 2017/5/15.
 */

public class BaseViewHelper {

    private static BaseViewHelper mInstance;

    private IBaseRequirement mBaseRequirement;

    private Context mContext;

    private BaseViewHelper() {
    }

    public static BaseViewHelper getInstance() {
        if (mInstance == null) {
            mInstance = new BaseViewHelper();
        }
        return mInstance;
    }

    public void setBaseRequirement(IBaseRequirement requirement) {
        mBaseRequirement = requirement;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public void setApplicationContext(Context context) {
        mContext = context;
    }

    public IActivityHelper getActivityHelper() {
        if (mBaseRequirement != null) {
            return mBaseRequirement.getActivityHelper();
        }

        return null;
    }

    public IFragmentHelper getFragmentHelper() {

        if (mBaseRequirement != null) {
            return mBaseRequirement.getFragmentHelper();
        }

        return null;
    }

}
