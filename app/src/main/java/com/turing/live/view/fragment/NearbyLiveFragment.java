package com.turing.live.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.turing.framework.base.BaseFragment;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Constants;
import com.turing.framework.utils.DisplayUtil;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.framework.view.SuperViewHolder;
import com.turing.live.R;
import com.turing.live.model.HotAnchor;
import com.turing.live.model.HotGirl;
import com.turing.live.scene.HotScene;
import com.turing.live.utils.LiveUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 附近
 * Created by YSL on 2017/6/13.
 */

public class NearbyLiveFragment extends BaseFragment {

    @InjectView(id = R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(id = R.id.lu_recyclerview)
    private LuRecyclerView luRecyclerView;
    private LuRecyclerViewAdapter luRecyclerViewAdapter;

    private NearbyLiveAdapter adapter;

    private int pageNo = 1; //当前页码
    private int currentLoadCount = 0; //当前加载数量
    private int total; //总数

    @Override
    protected int getContentView() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();

    }

    /**
     * 初始化上拉下拉加载控件
     */
    private void initRecyclerView() {
        //setLayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //防止item位置互换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        luRecyclerView.setLayoutManager(layoutManager);

        adapter = new NearbyLiveAdapter(getActivity());
        luRecyclerViewAdapter = new LuRecyclerViewAdapter(adapter);
        luRecyclerView.setAdapter(luRecyclerViewAdapter);

        luRecyclerView.setFooterViewColor(com.turing.framework.R.color.colorAccent, com.turing.framework.R.color.black, android.R.color.white);
        luRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
//        luRecyclerView.setOnLoadMoreListener(onLoadMore);

        swipeRefreshLayout.setProgressViewOffset(false, 0, DisplayUtil.dip2px(getActivity(), Constants.DEFAULT_REFRESH_HEIGHT));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(onRefresh);

//        swipeRefreshLayout.setRefreshing(true);
//        // 默认自动加载数据
//        onRefresh.onRefresh();
//        luRecyclerView.setRefreshing(true);

        luRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {  //点击事件
            @Override
            public void onItemClick(View view, int position) {
                Logger.i(TAG,"直播地址是>>" + adapter.getDataList().get(position).getStreamurl());
                LiveUtils.startLookLive(getActivity(),adapter.getDataList().get(position).getXid(),adapter.getDataList().get(position).getPhoto(),adapter.getDataList().get(position).getStreamurl());
            }
        });
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener onRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            currentLoadCount = 0;
            pageNo = 1;
            adapter.clear();
            luRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法

            initData();
        }
    };
    //上啦加载更多
//    private OnLoadMoreListener onLoadMore = new OnLoadMoreListener() {
//        @Override
//        public void onLoadMore() {
//            if (currentLoadCount < total) {
//                pageNo++;
////                fetchData();
//            } else {
//                luRecyclerView.setNoMore(true);
//            }
//        }
//    };

    @Override
    protected void initData() {
        swipeRefreshLayout.setRefreshing(true);
        luRecyclerView.setRefreshing(true);
        HotScene.getNearListTest(new BaseObserver<HotAnchor>() {
            @Override
            public void OnFail(int code, String err) {
                swipeRefreshLayout.setRefreshing(false);
                showToast(err);
            }

            @Override
            public void OnSuccess(HotAnchor hotAnchor) {
                if (hotAnchor == null){
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }

                List<HotGirl> list = new ArrayList<>();
                for (int i=0;i<hotAnchor.getListHotGirl().size();i++){
                    HotGirl item = hotAnchor.getListHotGirl().get(i);
//                    item.height = new Random().nextInt(1000);
//                    if(item.height < 100) {
//                        item.height += 400;
//                    }
                    list.add(item);
                }
                adapter.setDataList(list);

                swipeRefreshLayout.setRefreshing(false);
                luRecyclerView.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
                luRecyclerView.setNoMore(true); //设置已经全部加载
            }
        });
    }

    /**
     * 附近适配器
     */
    class NearbyLiveAdapter extends ListBaseAdapter<HotGirl> {
        public NearbyLiveAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_nearbylive;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            HotGirl hotGirl = mDataList.get(position);

            CardView cardView =  holder.getView(R.id.card_view);
            TextView tvNickName = holder.getTextView(R.id.tv_name);
            tvNickName.setText(hotGirl.getNickName());
            tvNickName.setCompoundDrawablesWithIntrinsicBounds(LiveUtils.getLevIco(hotGirl.getLevel()),0,0,0);

            ImageView ivPhoto = holder.getView(R.id.iv_photo);
            Glide.with(mContext).load(hotGirl.getPhoto()).into(ivPhoto);

            //修改高度，模拟交错效果
//            cardView.getLayoutParams().height = hotGirl.height;
        }
    }
}
