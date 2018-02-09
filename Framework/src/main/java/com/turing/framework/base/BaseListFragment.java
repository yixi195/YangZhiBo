package com.turing.framework.base;

import android.view.View;

import com.turing.framework.view.viewdata.BaseListView;


/**
 * 基础刷新Fragment
 * Created by huang on 2017/5/2.
 */

public abstract class BaseListFragment extends BaseFragment implements BaseListView{

    protected  PullRefreshProxy refreshProxy;

    @Override
    protected void initView(View view) {
        refreshProxy = new PullRefreshProxy(getContext(), this);
        initListView(view);
    }

    /** 跟RecycleView有关的操作在这个方法重写 */
    protected abstract void initListView(View view);
}
