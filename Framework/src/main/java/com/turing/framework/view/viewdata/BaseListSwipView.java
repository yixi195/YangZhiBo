package com.turing.framework.view.viewdata;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.turing.framework.model.PageList;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.view.ListBaseAdapter;

/**
 *
 */
public interface BaseListSwipView<T> extends BaseView {

    LuRecyclerView getLuRecycleView();

    ListBaseAdapter getAdapter();

    SwipeRefreshLayout getSwipe();

    OnItemClickListener onItemClickListener();

//    View emptyView();

    void loadData(int pageNo, BaseObserver<PageList<T>> observer);

    void loadDataWithCustome();
}
