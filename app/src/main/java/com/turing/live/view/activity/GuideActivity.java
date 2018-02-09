package com.turing.live.view.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.dou361.baseutils.utils.UIUtils;
import com.dou361.customui.ui.IndicatorView;
import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.ActivityManager;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.MainActivity;
import com.turing.live.R;
import com.turing.live.adapter.GuideAdapter;
import com.turing.live.scene.UserScene;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 介绍引导页
 */
public class GuideActivity extends BaseActivity implements
        OnPageChangeListener,View.OnClickListener {

    /**
     * 装载向导图片的View
     */
    @InjectView(id = R.id.view_pager)
    private ViewPager view_pager;
    @InjectView(id = R.id.ll_indicator,onClick = true)
    private LinearLayout ll_indicator;
    /**
     * 进入主页面按钮
     */
    @InjectView(id = R.id.btn_guide,onClick = true)
    private Button btn_guide;
    /**
     * 图片列表
     */
    private List<View> imageLists;
    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;
    private IndicatorView mIndicator;

    @Override
    protected int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        view_pager.setOnPageChangeListener(this);
        mIndicator = new IndicatorView(UIUtils.getContext());
        // 设置点和点之间的间隙
        mIndicator.setInterval(UIUtils.dip2px(40));
        // 设置点的图片
        mIndicator.setIndicatorDrawable(UIUtils.getDrawable(R.drawable.live_indicator_selector));
        mIndicator.setSelection(0);
        ll_indicator.addView(mIndicator);
        TypedArray icons = UIUtils.getTypedArray(R.array.guide_picture);
        imageLists = new ArrayList<View>();
        for (int i = 0; i < icons.length(); i++) {
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ScaleType.FIT_XY);
            iv.setImageDrawable(icons.getDrawable(i));
            imageLists.add(iv);
        }
        mIndicator.setCount(imageLists.size());
        /** 如果导航页面只有一页，显示体验按钮 */
        if (imageLists.size() == 1) {
            btn_guide.setVisibility(View.VISIBLE);
        }
        GuideAdapter adapter = new GuideAdapter(imageLists);
        view_pager.setAdapter(adapter);

        /** viewpaer左滑最后一页时进入应用 */
        try {
            Field leftEdgeField = view_pager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = view_pager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(view_pager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(view_pager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guide:
                Intent intent;
                if (TextUtils.isEmpty(UserScene.getUserName())){
                    intent = new Intent(GuideActivity.this,LoginMainActivity.class);
                }else{
                    intent = new Intent(GuideActivity.this,MainActivity.class);
                }
                startActivity(intent);
                this.finish();
                break;
        }
    }

    /**
     * 进入应用
     */
    private void enterApp() {
        startActivity(new Intent(this,MainActivity.class));
        ActivityManager.removeAllActivity();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        /** 到了最后一张并且还继续拖动，出现蓝色限制边条了 */
        if (rightEdge != null && !rightEdge.isFinished()) {
            /** 最后一张往右滑进入应用 */
            enterApp();
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.setSelection(position);
        /** 最后一张 */
        if (position == imageLists.size() - 1) {
            btn_guide.setVisibility(View.VISIBLE);
        } else {
            btn_guide.setVisibility(View.GONE);
        }
    }

}
