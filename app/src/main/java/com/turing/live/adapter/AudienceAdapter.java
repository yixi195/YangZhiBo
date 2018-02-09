package com.turing.live.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.turing.live.R;
import com.turing.live.model.RoomUser;
import com.turing.live.widgets.CustomRoundView;

import java.util.ArrayList;
import java.util.List;


/**
 * 横向listview的数据适配器，也就是观众列表，后居者可以直接根据自己的需求在这里改功能以及布局文件就ok
 */
public class AudienceAdapter extends BaseAdapter {

    private Context mContext;
    private List<RoomUser> list;

    public AudienceAdapter(Context context) {
        this.mContext = context;
        list = new ArrayList<>();
    }

    /**
     * 设置数据
     * @param list
     */
    public void setDatas(List<RoomUser> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_audienceadapter, null);
            viewHolder = new ViewHolder();
            viewHolder.ivHead = (CustomRoundView)convertView.findViewById(R.id.crvheadimage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.size() > 0 && list.size() > position){
            Glide.with(mContext).load(list.get(position).getHead()).into(viewHolder.ivHead);
        }
        return convertView;
    }

    /*存放控件 的ViewHolder*/
    public final class ViewHolder {
        public CustomRoundView ivHead;
    }
}