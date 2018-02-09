package com.turing.live.view.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.turing.framework.base.BaseActivity;
import com.turing.framework.model.PageList;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.framework.view.SuperViewHolder;
import com.turing.live.R;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.model.HotListModel;
import com.turing.live.model.SearchRoom;
import com.turing.live.scene.HotScene;
import com.turing.live.utils.LiveUtils;
import com.turing.live.widgets.CustomRoundView;

/**
 * 搜索
 * Created by YSL on 2017/6/13.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(id = R.id.iv_back, onClick = true)
    private ImageView ivBack;
    @InjectView(id = R.id.et_keyword)
    private EditText etKeyword;
    @InjectView(id = R.id.tv_search, onClick = true)
    private TextView tvSearch;
    @InjectView(id = R.id.recycleview_search)
    private LRecyclerView recyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private SearchAdapter mSearchAdapter;
    private long page = 1,pageSize = 20;
    private int currentCount,totalCount; //当前已加载数量

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        etKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //搜索操作
                    searchRoom(etKeyword.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchAdapter = new SearchAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mSearchAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setPullRefreshEnabled(false); //禁用刷新

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() { //点击
            @Override
            public void onItemClick(View view, int position) {
                showLoading("加载中...");
                if ("1".equals(mSearchAdapter.getDataList().get(position).getPlaystatus())){
                    String xid = mSearchAdapter.getDataList().get(position).getXid();
                    HotScene.getBaseRoomInfo(xid, new BaseObserver<BaseRoomInfo>() {
                        @Override
                        public void OnFail(int code, String err) {
                            dismissLoading();
                            showToast(err);
                        }
                        @Override
                        public void OnSuccess(BaseRoomInfo baseRoomInfo) {
                            dismissLoading();
                            if (baseRoomInfo == null || baseRoomInfo.getVideoinfo() == null) return;
                            Logger.i("ysl","直播地址是>>" + baseRoomInfo.getVideoinfo().getStreamurl());
                            LiveUtils.startLookLive(SearchActivity.this,baseRoomInfo.getRoominfo().getXid(),baseRoomInfo.getRoominfo().getPhoto(),baseRoomInfo.getVideoinfo().getStreamurl());
                        }
                    });
                }else{
                    dismissLoading();
                    showToast("主播还未开播哦~ ~");
                }
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() { //加载更多
            @Override
            public void onLoadMore() {
                if (totalCount > currentCount) {
                    page++;
                    searchRoom(etKeyword.getText().toString().trim());
                } else {
                    recyclerView.setNoMore(true);
                }
            }
        });

    }

    @Override
    protected void initData() {
    }

    /**
     * 搜索
     * @param keyword
     */
    private void searchRoom(String keyword){
        if (TextUtils.isEmpty(keyword)){
            showToast("搜索内容不能为空!");
            return;
        }

        showLoading("搜索中...");
        HotScene.searchRoom(keyword, page, pageSize, new BaseObserver<PageList<SearchRoom>>() {
            @Override
            public void OnFail(int code, String err) {
                dismissLoading();
                showToast(err);
            }

            @Override
            public void OnSuccess(PageList<SearchRoom> searchRoomPageList) {
                dismissLoading();
                if (searchRoomPageList == null || searchRoomPageList.getRows() == null){
                    showToast("服务器错误");
                    return;
                }
                if (searchRoomPageList.getRows().size() == 0){
                    showToast("暂无相关内容");
                }

                currentCount = searchRoomPageList.getTotal();
                if (page == 1){
                    mSearchAdapter.setDataList(searchRoomPageList.getRows());
                }else{
                    mSearchAdapter.addAll(searchRoomPageList.getRows());
                }
            }
        });
    }


    /**
     * 搜索结果适配器
     */
    class SearchAdapter extends ListBaseAdapter<SearchRoom>{
        public SearchAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_search;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            SearchRoom searchRoom = mDataList.get(position);
            holder.getTextView(R.id.tv_nickname).setText(searchRoom.getNickName());
            holder.getTextView(R.id.tv_roomname).setText("房间名: " + searchRoom.getName());
            holder.getTextView(R.id.tv_fanscount).setText("粉丝数: " + searchRoom.getFansnum());

            CustomRoundView customRoundView = holder.getView(R.id.iv_head);
            Glide.with(mContext).load(searchRoom.getAvatar()).into(customRoundView);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search: //搜索
                searchRoom(etKeyword.getText().toString().trim());
                break;
        }
    }
}
