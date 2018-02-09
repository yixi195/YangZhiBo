package com.turing.framework.base;

import com.turing.framework.view.viewdata.BaseListView;


/**
 * 基础刷新Activity
 * Created by huang on 2017/5/2.
 */

public abstract class BaseListActivity extends BaseActivity implements BaseListView{

    protected PullRefreshProxy pullRefreshProxy;

    @Override
    protected void initView() {
        pullRefreshProxy = new PullRefreshProxy(getContext(),this);
        initListView();
    }

    /** 跟RecycleView有关的操作在这个方法重写 */
    protected abstract void initListView();
}
