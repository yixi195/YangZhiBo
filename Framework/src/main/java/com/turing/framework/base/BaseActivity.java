package com.turing.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.jaeger.library.StatusBarUtil;
import com.turing.framework.R;
import com.turing.framework.utils.ActivityManager;
import com.turing.framework.utils.AppManager;
import com.turing.framework.utils.DialogUtils;
import com.turing.framework.utils.ToastUtil;
import com.turing.framework.utils.annotate.ViewAnotationUtil;
import com.turing.framework.view.BaseViewHelper;
import com.umeng.analytics.MobclickAgent;

/**
 * 整合基础Activity
 * Created by huang on 2017/5/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;
    protected DialogUtils dialogUtils;
    private IActivityHelper mActivityHelper = BaseViewHelper.getInstance().getActivityHelper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        if (0 != getContentView()){
            setContentView(getContentView());
        }
        setStatusBar();
        initView();
        initData();
        ActivityManager.addActivity(this,TAG);
        AppManager.getInstance().pushActivity(this);

        if (mActivityHelper != null) mActivityHelper.onCreate(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        ViewAnotationUtil.autoInjectAllField(this, view);
        this.setContentView(view);
    }

    /** 初始化视图 */
    protected abstract int getContentView();
    /** 初始化布局 */
    protected abstract void initView();
    /** 初始化数据 */
    protected abstract void initData();

    public Context getContext(){
        return this;
    }

    protected void setStatusBar(){
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.main_head), 0);
    }

    public void showToast(String text) {
        ToastUtil.show(text);
    }

    public void showLoading() {
        showLoading("");
    }
    public void showLoading(String text) {
        if (dialogUtils == null){
            dialogUtils = new DialogUtils(this);
        }
        dialogUtils.showLoading(text);
    }

    public void dismissLoading() {
        dialogUtils.dismissLoading();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mActivityHelper != null) mActivityHelper.onStart(this);
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        if (mActivityHelper != null) mActivityHelper.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        if (mActivityHelper != null) mActivityHelper.onPause(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mActivityHelper != null) mActivityHelper.onStop(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.removeActivity(TAG);
        AppManager.getInstance().popActivity(this);
        if (mActivityHelper != null) mActivityHelper.onDestroy(this);
        super.onDestroy();
    }

    //处理系统软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
