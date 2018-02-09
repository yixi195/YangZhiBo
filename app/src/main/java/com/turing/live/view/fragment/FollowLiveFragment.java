package com.turing.live.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.turing.framework.base.BaseFragment;
import com.turing.framework.base.BaseListSwipFragment;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Constants;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.ToastUtil;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.live.R;
import com.turing.live.adapter.FollowAdapter;
import com.turing.live.adapter.HotLiveAdapter;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.scene.HotScene;
import com.turing.live.scene.UserScene;
import com.turing.live.utils.LiveUtils;
import com.turing.live.view.holder.BannerHolder;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 关注
 * Created by YSL on 2017/6/13.
 */
public class FollowLiveFragment  extends BaseListSwipFragment {

    private FollowAdapter followAdapter;
    @InjectView(id = R.id.lu_recyclerview)
    private LuRecyclerView luRecyclerView;
    @InjectView(id = R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(id = R.id.empty_view)
    private View emptyView;

    @Override
    public LuRecyclerView getLuRecycleView() {
        return luRecyclerView;
    }

    @Override
    public ListBaseAdapter getAdapter() {
        if (followAdapter == null){
            followAdapter = new FollowAdapter(getActivity());
        }
        return followAdapter;
    }

    @Override
    public SwipeRefreshLayout getSwipe() {
        Logger.i(TAG,"getSwipe()>>>");
        return swipeRefreshLayout;
    }

    @Override
    public OnItemClickListener onItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.i(TAG,"直播地址是>>" + followAdapter.getDataList().get(position).getVideoinfo().getStreamurl());
                LiveUtils.startLookLive(getActivity(),followAdapter.getDataList().get(position).getXid(),followAdapter.getDataList().get(position).getRoominfo().getPhoto(),followAdapter.getDataList().get(position).getVideoinfo().getStreamurl());
            }
        };
    }

    @Override
    public void loadDataWithCustome() {
        BmobQuery<BaseRoomInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("userName",UserScene.getUserName());
        query.findObjects(new FindListener<BaseRoomInfo>() {
            @Override
            public void done(List<BaseRoomInfo> list, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                luRecyclerView.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
                refreshProxy.getLuAdapter().notifyDataSetChanged();

                if (e == null && list != null && list.size()>0){
                    emptyView.setVisibility(View.GONE);
                    followAdapter.setDataList(list);
                }else{
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onResume() {
        loadDataWithCustome();
        super.onResume();
    }

    @Override
    public void loadData(int pageNo, BaseObserver observer) {
//        HotScene.getHotRoomList(pageNo, Constants.DEFAULT_PAGE_SIZE,observer);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initListView(View view) {
        Logger.i(TAG,"initListView");
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
