package com.turing.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 *  屏幕工具类
 */
public class DisplayUtil {

	public static final String TAG = "DisplayUtil";

	/**
	 * get screen height of this cellphone
	 * 
	 * @param context
	 * @return
	 */
	public static int getMobileHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels; // 得到高度
		return height;
	}

	/**
	 * get screen width of this cellphone
	 * 
	 * @param context
	 * @return
	 */
	public static int getMobileWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels; // 得到宽度
		return width;

	}

	/**
	 * 根据手机的分辨率dp 转成px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率px(像素) 转成dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	
	 public static float sp2px(Context context, float sp){
	        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
	        return sp * scale;
	    }


	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}

	public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
		Class<?> tabLayout = tabs.getClass();
		Field tabStrip = null;
		try {
			tabStrip = tabLayout.getDeclaredField("mTabStrip");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		tabStrip.setAccessible(true);
		LinearLayout ll_tab = null;
		try {
			ll_tab = (LinearLayout) tabStrip.get(tabs);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		int left = (int) (getDisplayMetrics(context).density * leftDip);
		int right = (int) (getDisplayMetrics(context).density * rightDip);

		for (int i = 0; i < ll_tab.getChildCount(); i++) {
			View child = ll_tab.getChildAt(i);
			child.setPadding(0, 0, 0, 0);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
			params.leftMargin = left;
			params.rightMargin = right;
			child.setLayoutParams(params);
			child.invalidate();
		}
	}

}
