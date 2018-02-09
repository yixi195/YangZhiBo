package com.turing.framework.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装adapter
 */
public abstract class ListBaseAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {
    protected Context mContext;
    private LayoutInflater mInflater;

    protected List<T> mDataList = new ArrayList<>();

    public ListBaseAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getLayoutId(), parent, false);
        return new SuperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        onBindItemHolder(holder, position);
    }

    //局部刷新关键：带payload的这个onBindViewHolder方法必须实现
    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindItemHolder(holder, position, payloads);
        }

    }

    public abstract int getLayoutId();

    public abstract void onBindItemHolder(SuperViewHolder holder, int position);

    public void onBindItemHolder(SuperViewHolder holder, int position, List<Object> payloads){

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 获取List列表数据
     * @return
     */
    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 设置数据
     * @param list
     */
    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param list
     */
    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    /**
     * 移除某条数据
     * @param position
     */
    public void remove(int position) {
        //保证列表有数据，并且最少有一条
        if(mDataList.size()<2&&mDataList.size()!=0){
            mDataList.remove(0);
            notifyDataSetChanged();
        }else if(mDataList.size()==0){ //当列表没有数据提示用户，免得造成系统崩溃

        }else{//更新列表
            mDataList.remove(position);
            notifyDataSetChanged();
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mDataList.size());
        }
    }
    /**
     * 清空列表
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

}
