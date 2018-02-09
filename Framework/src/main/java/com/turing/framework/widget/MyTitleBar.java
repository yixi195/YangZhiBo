package com.turing.framework.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.turing.framework.R;


/**
 * headView format
 * Created by huang on 2015/11/12.
 */
public class MyTitleBar extends RelativeLayout {
    private LinearLayout rl_back_layer;
    private LinearLayout right_layer;
    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_middle;
    private ImageView iv_right_icon;

    private OnClickListener mLeftClickListener = null;
    private OnClickListener mRightClickListener = null;


    public MyTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public MyTitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    private void init(final Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_title_bar, this, true);
        RelativeLayout mParentView = (RelativeLayout) findViewById(R.id.head);
        rl_back_layer = (LinearLayout)findViewById(R.id.rl_back_layer);
        rl_back_layer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
//                ((Activity)context).overridePendingTransition(R.anim.left_in,R.anim.right_out);

            }
        });

        MyStatusBar status_bar = (MyStatusBar) findViewById(R.id.status_bar);
        ImageView iv_left_icon = (ImageView) findViewById(R.id.iv_left_icon);
        iv_right_icon = (ImageView) findViewById(R.id.right_iv_icon);
        tv_left = (TextView)findViewById(R.id.tv_left);
        tv_middle = (TextView)findViewById(R.id.tv_middle);
        tv_right = (TextView)findViewById(R.id.tv_right);
        right_layer = (LinearLayout) findViewById(R.id.right_layer);
        View divider_line = findViewById(R.id.divider_line);

        //默认不显示分割线
        divider_line.setVisibility(View.GONE);
        status_bar.setVisibility(View.GONE);
        if (attrs == null) {
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        int count = array.getIndexCount();
        for (int i = 0 ; i < count; i++) {
            int attr = array.getIndex(i);

            if (attr == R.styleable.MyTitleBar_leftText) {
                String resId = array.getString(attr);
                tv_left.setText(resId);
            } else if (attr == R.styleable.MyTitleBar_middleText) {
                String resId = array.getString(attr);
                tv_middle.setText(resId);
            } else if (attr == R.styleable.MyTitleBar_rightText) {
                String resId = array.getString(attr);
                tv_right.setText(resId);
            } else if (attr == R.styleable.MyTitleBar_leftIcon) {
                Drawable drawable = array.getDrawable(attr);
                iv_left_icon.setImageDrawable(drawable);
            } else if (attr == R.styleable.MyTitleBar_rightIcon){
                Drawable drawable = array.getDrawable(attr);
                iv_right_icon.setImageDrawable(drawable);
            } else if (attr == R.styleable.MyTitleBar_middleTextColor){
                int color=array.getColor(attr, 0);
                tv_middle.setTextColor(color);
            } else if (attr == R.styleable.MyTitleBar_rightTextColor){
                int color=array.getColor(attr, 0);
                tv_right.setTextColor(color);
            } else if (attr == R.styleable.MyTitleBar_backgroundColor){
                int color=array.getColor(attr, 0);
                mParentView.setBackgroundColor(color);
            } else if (attr == R.styleable.MyTitleBar_statusBarBackgroundColor) {
                int color = array.getColor(attr, 0);
                status_bar.setBackgroundColor(color);
            } else if (attr == R.styleable.MyTitleBar_rightTextSize){
                float size=array.getDimension(attr, 0);
                tv_right.setTextSize(size);
            } else if (attr == R.styleable.MyTitleBar_rightBackground){
                Drawable drawable = array.getDrawable(attr);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    tv_right.setBackgroundDrawable(drawable);
                } else {
                    tv_right.setBackground(drawable);
                }
            } else if (attr == R.styleable.MyTitleBar_leftVisible) {
                boolean show = array.getBoolean(attr, true);
                int visible = show ? View.VISIBLE : View.INVISIBLE;
                rl_back_layer.setVisibility(visible);
            } else if (attr == R.styleable.MyTitleBar_rightVisible) {
                boolean show = array.getBoolean(attr, true);
                int visible = show ? View.VISIBLE : View.INVISIBLE;
                tv_right.setVisibility(visible);
            } else if (attr == R.styleable.MyTitleBar_showStatusBar) {
                boolean show = array.getBoolean(attr, false);
                int visible = show ? View.VISIBLE : View.GONE;
                status_bar.setVisibility(visible);


            } else if (attr == R.styleable.MyTitleBar_showDividerLine) {
                boolean show = array.getBoolean(attr, true);
                int visible = show ? View.VISIBLE : View.GONE;
                divider_line.setVisibility(visible);
            }
        }
        array.recycle();
    }

    public void setLeftText(String title) {tv_left.setText(title);}

    public void setMiddleText(String title){
        tv_middle.setText(title);
    }

    public void setRightText(String title){
        tv_right.setText(title);
    }

    public void setRightImage(int resId){
        iv_right_icon.setBackgroundResource(resId);
    }

    public void setLeftVisible(int visible) {
        rl_back_layer.setVisibility(visible);
    }

    public void setRightVisible(int visible) {
        right_layer.setVisibility(visible);
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        mLeftClickListener = listener;
        rl_back_layer.setOnClickListener(listener);
    }

    public void setOnRightClickListener(OnClickListener listener) {
        mRightClickListener = listener;
        right_layer.setOnClickListener(listener);
    }

    public OnClickListener getRightClickListener() {
        return mRightClickListener;
    }

}
