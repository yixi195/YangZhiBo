package com.turing.framework.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.turing.framework.R;
import com.turing.framework.model.PageList;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Constants;
import com.turing.framework.utils.DisplayUtil;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.ToastUtil;
import com.turing.framework.view.viewdata.BaseListSwipView;

/**
 * Swipe下拉风格刷新代理
 * Created by huang on 2017/5/2.
 */
public class RefreshProxy {

    private int pageNo = 1; //当前页码
    private int currentLoadCount = 0; //当前加载数量
    private int total; //总数

    private Context mContext;
    private BaseListSwipView mListView;
    private LuRecyclerViewAdapter mLuAdapter;
    public boolean isDownRefresh; //是否属于下拉刷新
//    private View no_data_view;

    public RefreshProxy(Context context, BaseListSwipView view) {
        this.mContext = context;
        this.mListView = view;
        initView();
    }

    private void initView(){
        if (mListView.getAdapter() == null)
            throw new IllegalArgumentException("Constructor parameters :Adapter cannot be null!!!");

        if (mListView.getLuRecycleView() == null)
            throw new IllegalArgumentException("Constructor parameters :LuRecycleView cannot be null!!!");

        if (mListView.getSwipe() == null)
            throw new IllegalArgumentException("Constructor parameters :Swipe cannot be null!!!");

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mLuAdapter = new LuRecyclerViewAdapter(mListView.getAdapter());
        if (mListView.onItemClickListener() != null)
            mLuAdapter.setOnItemClickListener(mListView.onItemClickListener());

//        if (mListView.emptyView() == null) {
//            no_data_view = new DefaultNoDataView(mContext);
//        } else {
//            no_data_view = mListView.emptyView();
//        }

        mListView.getLuRecycleView().setLayoutManager(layoutManager);
        mListView.getLuRecycleView().setAdapter(mLuAdapter);
        mListView.getLuRecycleView().setLoadMoreEnabled(true);
        mListView.getLuRecycleView().setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mListView.getLuRecycleView().setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        mListView.getLuRecycleView().setOnLoadMoreListener(onLoadMore);

        mListView.getSwipe().setProgressViewOffset(false, 0, DisplayUtil.dip2px(mContext,Constants.DEFAULT_REFRESH_HEIGHT));
        mListView.getSwipe().setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mListView.getSwipe().setOnRefreshListener(onRefresh);
        mListView.getSwipe().setRefreshing(true);
        // 默认自动加载数据
        onRefresh.onRefresh();
        mListView.getLuRecycleView().setRefreshing(true);
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener onRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            currentLoadCount = 0;
            pageNo = 1;
            mListView.getAdapter().clear();
            mListView.getSwipe().setRefreshing(true);
            mListView.getLuRecycleView().setRefreshing(true);
            isDownRefresh = true;
            fetchData();
        }
    };
    //上啦加载更多
    private OnLoadMoreListener onLoadMore = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            Logger.i("ysl","上啦加载更多>>>" + currentLoadCount + "|" + total + "|" + pageNo);
            if (currentLoadCount < total) {
                pageNo++;
                fetchData();
            } else {
                mListView.getLuRecycleView().setNoMore(true);
            }
        }
    };

    /**
     * 获取LuAdapter
     * @return
     */
    public LuRecyclerViewAdapter getLuAdapter(){
        return mLuAdapter;
    }

    /**
     * 加载数据
     */
    public void fetchData(){
        mListView.loadDataWithCustome();
        mListView.loadData(pageNo,new BaseObserver<PageList>() {
            @Override
            public void OnFail(int code, String err) {
                //TODO 请求失败的处理
                ToastUtil.show(err);
                //如果请求有错误，则会调用缓存的，所以这里也要判断下是否 有数据
//                if (mListView.getAdapter().getDataList() != null && mListView.getAdapter().getDataList().size() > 0) {
//                    no_data_view.setVisibility(View.GONE);
//                } else no_data_view.setVisibility(View.VISIBLE);
                mListView.getSwipe().setRefreshing(false);

                isDownRefresh = false;
            }

            @Override
            public void OnSuccess(PageList pageList) {
                mListView.getSwipe().setRefreshing(false);
                mListView.getLuRecycleView().refreshComplete(Constants.DEFAULT_PAGE_SIZE);
                mListView.getAdapter().notifyDataSetChanged();
                total = pageList.getTotal();
                if (pageList.getRows() == null || pageList.getRows().size() == 0) {
//                    no_data_view.setVisibility(View.VISIBLE);
                } else {
                    currentLoadCount = currentLoadCount + pageList.getRows().size();
                    mListView.getAdapter().addAll(pageList.getRows());
//                    no_data_view.setVisibility(View.GONE);
                }
                isDownRefresh = false;
            }
        });
    }

}
