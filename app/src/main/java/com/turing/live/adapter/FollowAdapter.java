package com.turing.live.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.turing.framework.utils.GlideCircleTransform;
import com.turing.framework.utils.StringUtils;
import com.turing.framework.view.ListBaseAdapter;
import com.turing.framework.view.SuperViewHolder;
import com.turing.live.R;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.model.HotGirl;
import com.turing.live.utils.TextUtils;

/**
 *  关注直播适配器
 * Created by YSL on 2017/6/14.
 */
public class FollowAdapter extends ListBaseAdapter<BaseRoomInfo> {

    public FollowAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hot;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BaseRoomInfo item = mDataList.get(position);

        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvAddress = holder.getView(R.id.tv_address);
        TextView tvLookCount = holder.getView(R.id.tv_count);
        ImageView ivHead = holder.getView(R.id.iv_head);
        ImageView ivPhoto = holder.getView(R.id.iv_photo);

        tvName.setText(item.getHostinfo().getNickName());
//        if (StringUtils.isEmpty(item.getProvince())){
            tvAddress.setText("火星");
//        }else{
//            tvAddress.setText(item.getProvince() + " " + item.getCity());
//        }
        tvLookCount.setText(Html.fromHtml(TextUtils.getHtmlLookCount(item.getRoominfo().getPersonnum())));
        Glide.with(mContext).load(item.getHostinfo().getAvatar()).transform(new GlideCircleTransform(mContext)).into(ivHead);
        Glide.with(mContext).load(item.getRoominfo().getPhoto()).into(ivPhoto);


    }
}
