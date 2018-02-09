package com.turing.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turing.framework.utils.DialogUtils;
import com.turing.framework.utils.ToastUtil;
import com.turing.framework.utils.annotate.ViewAnotationUtil;
import com.turing.framework.view.BaseViewHelper;

/**
 * 整合基础Fragment
 * Created by huang on 2017/5/17.
 */

public abstract class BaseFragment extends Fragment {

    protected String TAG;
    protected View mContentView;
    protected DialogUtils dialogUtils;
    private IFragmentHelper mFragmentHelper = null;

    /** 初始化视图 */
    protected abstract int getContentView();
    /** 初始化布局 */
    protected abstract void initView(View view);
    /** 初始化数据 */
    protected abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mFragmentHelper = BaseViewHelper.getInstance().getFragmentHelper();
        if (mFragmentHelper != null) {
            mFragmentHelper.onCreate(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = createContentView(getContentView());
            initView(mContentView);
            initData();
        }else{
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }

        return mContentView;
    }

    @NonNull
    public View getView(){
        return mContentView;
    }

    public View createContentView(int layoutResID) {
        View view = getActivity().getLayoutInflater().inflate(layoutResID, null);
        ViewAnotationUtil.autoInjectAllField(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mFragmentHelper != null) {
            mFragmentHelper.onViewCreated(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentHelper != null) mFragmentHelper.onStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFragmentHelper != null) mFragmentHelper.onResume(this);
    }

    @Override
    public void onPause() {
        if (mFragmentHelper != null) mFragmentHelper.onPause(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFragmentHelper != null) mFragmentHelper.onStop(this);
    }

    @Override
    public void onDestroyView() {
        if (mFragmentHelper != null) mFragmentHelper.onDestroyView(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mFragmentHelper != null) mFragmentHelper.onDestroy(this);
        super.onDestroy();
    }

    public void showToast(String text) {
        ToastUtil.show(text);
    }

    public void showLoading(String title) {
        dialogUtils = new DialogUtils(getContext());
        dialogUtils.showLoading(title);
    }

    public void dismissLoading() {
        dialogUtils.dismissLoading();
    }

    public Context getContext(){
        return getActivity();
    }
}
