package com.turing.framework.view.viewdata;

import android.support.v7.widget.RecyclerView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.turing.framework.model.PageList;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.view.ListBaseAdapter;

/**
 *
 * Created by huang on 2017/5/2.
 */

public interface BaseListView<T> extends BaseView {

    RecyclerView getRecycleView();

    ListBaseAdapter getAdapter();

//    SwipeRefreshLayout getSwipe();

    OnItemClickListener onItemClickListener();

//    View emptyView();

    void loadData(int pageNo, BaseObserver<PageList<T>> observer);

    void loadDataWithCustome();

}
