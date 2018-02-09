package com.turing.framework.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.turing.framework.utils.ScreenUtils;

/**
 * 自定义状态栏的高度
 */
public class MyStatusBar extends View {
	Context context;
	public MyStatusBar(Context context) {
		super(context);
	}
	
	public MyStatusBar(Context context, AttributeSet attrs)  {
		super(context, attrs);
		this.context = context;
		
		setVisibility(View.VISIBLE);
	}
	
	public MyStatusBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	@Override
	public void setVisibility(int visibility) {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			super.setVisibility(visibility);
		} else {
			super.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LayoutParams params = this.getLayoutParams();
		params.height = ScreenUtils.getStatusHeight(context);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

}
