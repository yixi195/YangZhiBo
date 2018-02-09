package com.turing.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.framework.view.viewdata.BaseListSwipView;
import com.turing.framework.view.viewdata.BaseListView;

/**
 * Created by YSL on 2017/6/29.
 */

public abstract class BaseListSwipFragment extends BaseFragment implements BaseListSwipView {

    protected View rootView;
    protected RefreshProxy refreshProxy = null;
    protected boolean isOnResum = false; //是否调用OnResum(),参考HomeFragment

    @Override
    protected void initView(View view) {
        rootView = view;
        refreshProxy = new RefreshProxy(getContext(), this);
        initListView(view);
    }

    /** 跟RecycleView有关的操作在这个方法重写 */
    protected abstract void initListView(View view);
}
