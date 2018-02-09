package com.turing.live.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.turing.framework.base.BaseListSwipFragment;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Constants;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.live.R;
import com.turing.live.adapter.HotLiveAdapter;
import com.turing.live.model.HotAnchor;
import com.turing.live.scene.HotScene;
import com.turing.live.utils.LiveUtils;
import com.turing.live.view.holder.BannerHolder;

/**
 * 热门
 * Created by YSL on 2017/6/13.
 */

public class HotLiveFragment extends BaseListSwipFragment{

    private BannerHolder bannerHolder;
    private HotLiveAdapter hotLiveAdapter;
    @InjectView(id = R.id.lu_recyclerview)
    private LuRecyclerView luRecyclerView;
    @InjectView(id = R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public LuRecyclerView getLuRecycleView() {
        return luRecyclerView;
    }

    @Override
    public ListBaseAdapter getAdapter() {
        if (hotLiveAdapter == null){
            hotLiveAdapter = new HotLiveAdapter(getActivity());
        }
        return hotLiveAdapter;
    }

    @Override
    public SwipeRefreshLayout getSwipe() {
        Logger.i(TAG,"getSwipe()>>>");
        return swipeRefreshLayout;
    }

    @Override
    public void onStart() {
        Logger.i(TAG,"onStart()>>>");
        bannerHolder.startBanner();
        super.onStart();
    }

    @Override
    public OnItemClickListener onItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.i(TAG,"直播地址是>>" + hotLiveAdapter.getDataList().get(position).getStreamurl());
                LiveUtils.startLookLive(getActivity(),hotLiveAdapter.getDataList().get(position).getXid(),hotLiveAdapter.getDataList().get(position).getPhoto(),hotLiveAdapter.getDataList().get(position).getStreamurl());
            }
        };
    }

    @Override
    public void loadDataWithCustome() {

    }

    @Override
    public void loadData(int pageNo, BaseObserver observer) {
        if (refreshProxy != null && refreshProxy.isDownRefresh){
            bannerHolder.showBanner(); //刷新Banner
        }
        HotScene.getHotRoomList(pageNo,Constants.DEFAULT_PAGE_SIZE,observer);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initListView(View view) {
        Logger.i(TAG,"initListView");
        bannerHolder = new BannerHolder(getActivity());
        refreshProxy.getLuAdapter().addHeaderView(bannerHolder.getContentView());
        bannerHolder.showBanner();
    }

    @Override
    protected void initData() {
        Logger.i(TAG,"initData()>>>");
    }

    /**
     * 回到头部
     */
    public void backTop(){
        luRecyclerView.scrollToPosition(0);
    }
}
