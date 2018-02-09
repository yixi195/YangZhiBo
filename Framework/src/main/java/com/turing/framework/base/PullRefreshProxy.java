package com.turing.framework.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.turing.framework.R;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.rx.ServerResult;
import com.turing.framework.utils.Constants;
import com.turing.framework.utils.Logger;
import com.turing.framework.view.viewdata.BaseListView;

import java.util.Collection;

/**
 * 下拉风格刷新代理
 */
public class PullRefreshProxy implements OnRefreshListener, OnLoadMoreListener, OnNetWorkErrorListener {

    private int currentPage = 1;
    private int totalPage;
    private Context mContext;
    private BaseListView mListView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private LRecyclerView recyclerView;

    public PullRefreshProxy(Context context, BaseListView view) {
        this.mContext = context;
        this.mListView = view;
        initView();
    }

    private void initView() {
        if (mListView.getAdapter() == null)
            throw new IllegalArgumentException("Constructor parameters : Adapter cannot be null !!!");

        if (mListView.getRecycleView() == null)
            throw new IllegalArgumentException("Constructor parameters : RecycleView cannot be null !!!");

        if (!(mListView.getRecycleView() instanceof LRecyclerView))
            throw new IllegalArgumentException("Constructor parameters : RecycleView should instanceof LRecyclerView !!!");

        recyclerView = (LRecyclerView) mListView.getRecycleView();

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListView.getAdapter());
        recyclerView.setAdapter(mLRecyclerViewAdapter);

        if (mListView.onItemClickListener() != null)
            mLRecyclerViewAdapter.setOnItemClickListener(mListView.onItemClickListener());

        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setOnNetWorkErrorListener(this);
        recyclerView.forceToRefresh();
    }

    public LRecyclerViewAdapter getAdapter(){
        return mLRecyclerViewAdapter;
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        fetchData();
    }

    @Override
    public void onLoadMore() {
        if (currentPage < totalPage) {
            currentPage ++;
            fetchData();
        } else {
            recyclerView.setNoMore(true);
        }
    }

    @Override
    public void reload() {
        onRefresh();
    }


    /**
     * 加载数据
     */
    @SuppressWarnings("unchecked")
    private void fetchData() {
        mListView.loadDataWithCustome();
//        mListView.loadData(currentPage, new BaseObserver<ServerResult<Collection>>() {
//            @Override
//            public void OnSuccess(ServerResult<Collection> pageList) {
//                Logger.i("ysl","OnSuccess>>>>>>");
//                if (currentPage == 1) {
//                    mListView.getAdapter().setDataList(pageList.getData());
//                } else {
//                    mListView.getAdapter().addAll(pageList.getData());
//                }
//                recyclerView.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
//                mListView.getAdapter().notifyDataSetChanged();
//                mLRecyclerViewAdapter.notifyDataSetChanged();
//                totalPage = pageList.getData().size();
//            }
//
//            @Override
//            public void OnFail(int code, String err) {
//                Logger.i("ysl","OnFail>>>>>>");
//                mListView.showToast(err);
//                recyclerView.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
//                mListView.getAdapter().notifyDataSetChanged();
//                mLRecyclerViewAdapter.notifyDataSetChanged();
//            }
//        });
    }

}
