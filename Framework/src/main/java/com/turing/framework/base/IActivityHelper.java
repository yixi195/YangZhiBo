package com.turing.framework.base;

import android.app.Activity;

/**
 * 业务实现此接口，相当于重写BaseActivity
 * Created by huang on 2017/5/16.
 */
public interface IActivityHelper {

    void onCreate(Activity activity);

    void onStart(Activity activity);

    void onResume(Activity activity);

    void onPause(Activity activity);

    void onStop(Activity activity);

    void onDestroy(Activity activity);
}
