package com.turing.live.view.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.turing.framework.base.BaseListActivity;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Constants;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.framework.view.SuperViewHolder;
import com.turing.live.R;
import com.turing.live.model.SystemMessage;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 系统消息
 * Created by YSL on 2017/7/3.
 */

public class SystemMessageActivity extends BaseListActivity{
    @InjectView(id = R.id.l_recyclerview)
    private LRecyclerView lRecyclerView;
    private SysMessageAdapter sysMessageAdapter;

    @Override
    protected void initListView() {

    }

    @Override
    public RecyclerView getRecycleView() {
        return lRecyclerView;
    }

    @Override
    public ListBaseAdapter getAdapter() {
        if (sysMessageAdapter == null){
            sysMessageAdapter = new SysMessageAdapter(this);
        }
        return sysMessageAdapter;
    }

    @Override
    public OnItemClickListener onItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        };
    }

    @Override
    public void loadDataWithCustome() {
        BmobQuery<SystemMessage> query = new BmobQuery<>();
        query.setLimit(50);
        query.findObjects(new FindListener<SystemMessage>() {
            @Override
            public void done(List<SystemMessage> list, BmobException e) {
                if(e==null){
                    getAdapter().setDataList(list);
                    lRecyclerView.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
//                    getAdapter().notifyDataSetChanged();
                    pullRefreshProxy.getAdapter().notifyDataSetChanged();
                }else{
                    showToast("暂无数据");
                    lRecyclerView.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
//                mListView.getAdapter().notifyDataSetChanged();
                    pullRefreshProxy.getAdapter().notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void loadData(int pageNo, BaseObserver observer) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sys_message;
    }

    @Override
    protected void initData() {

    }


    /**
     * 消息适配器
     */
    class SysMessageAdapter extends ListBaseAdapter<SystemMessage>{
        public SysMessageAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_sysmessage;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            SystemMessage systemMessage = mDataList.get(position);
            holder.getTextView(R.id.tv_title).setText(systemMessage.getTitle());
            holder.getTextView(R.id.tv_content).setText(systemMessage.getContent());
        }
    }
}
