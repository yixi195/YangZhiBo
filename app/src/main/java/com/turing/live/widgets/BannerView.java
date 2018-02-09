package com.turing.live.widgets;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.turing.framework.utils.DisplayUtil;
import com.turing.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * android banner图
 */
public class BannerView extends FrameLayout implements OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mDotLl;
    private List<String> mUrlList;

    private List<ImageView> dotList = null;
    private MyAdapter mAdapter = null;
    private Handler mHandler = null;
    private AutoRollRunnable mAutoRollRunnable = null;

    private int prePosition = 0;

    private HeaderViewClickListener headerViewClickListener;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        initData();
        initListener();
    }

    //初始化view
    private void initView() {
        View.inflate(mContext, R.layout.view_banner, this);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mDotLl = (LinearLayout) findViewById(R.id.ll_dot);

//        //让banner的高度是屏幕的1/4
//        ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
////        vParams.height = (int) (DisplayUtil.getMobileHeight(mContext) * 0.3);
//        vParams.height = DisplayUtil.dip2px(mContext,200);
//        mViewPager.setLayoutParams(vParams);
    }

    //初始化数据
    private void initData() {
        if (dotList == null){
            dotList = new ArrayList<>();
        }
        if (mAutoRollRunnable == null){
            mAutoRollRunnable = new AutoRollRunnable();
        }
        if (mHandler == null){
            mHandler = new Handler();
        }
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
    }


    /**
     * 设置数据
     *
     * @param urlList
     */
    public void setImgUrlData(List<String> urlList) {
        initData();
        this.mUrlList = urlList;
        if (mUrlList != null && !mUrlList.isEmpty()) {
            //让banner的高度位200px
            ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
            vParams.height = DisplayUtil.dip2px(mContext,100);
            mViewPager.setLayoutParams(vParams);
            //清空数据
            dotList.clear();
            mDotLl.removeAllViews();
            ImageView dotIv;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < mUrlList.size(); i++) {
                dotIv = new ImageView(mContext);
                if (i == 0) {
                    dotIv.setBackgroundResource(R.mipmap.icon_dot_select);
                } else {
                    dotIv.setBackgroundResource(R.mipmap.icon_dot_unselect);
                }
                //设置点的间距
                params.setMargins(0, 0, DisplayUtil.dip2px(mContext, 5), 0);

                dotIv.setLayoutParams(params);

                //添加点到view上
                mDotLl.addView(dotIv);
                //添加到集合中, 以便控制其切换
                dotList.add(dotIv);
            }

            mAdapter = new MyAdapter();
            mViewPager.setAdapter(mAdapter);
            //设置viewpager初始位置, +10000就够了
            mViewPager.setCurrentItem(urlList.size() + 10000);
//        startRoll();
        }else{
            //让banner的高度置0
            ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
            vParams.height = DisplayUtil.dip2px(mContext,0);
            mViewPager.setLayoutParams(vParams);
        }

    }


    /**
     * 设置点击事件
     *
     * @param headerViewClickListener
     */
    public void setOnHeaderViewClickListener(HeaderViewClickListener headerViewClickListener) {
        this.headerViewClickListener = headerViewClickListener;
    }


    //开始轮播
    public void startRoll() {
        if (mAutoRollRunnable != null){
            mAutoRollRunnable.start();
        }
    }

    // 停止轮播
    public void stopRoll() {
        if (mAutoRollRunnable != null){
            mAutoRollRunnable.stop();
        }
    }

    private class AutoRollRunnable implements Runnable {

        //是否在轮播的标志
        boolean isRunning = false;

        public void start() {
//            Logger.i("ysl","BannerView start()>>>" + !isRunning);
            if (!isRunning) {
                isRunning = true;
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, 3000);
            }
        }

        public void stop() {
//            Logger.i("ysl","BannerView stop()>>>" + isRunning);
            if (isRunning) {
                mHandler.removeCallbacks(this);
                isRunning = false;
            }
        }

        @Override
        public void run() {
//            Logger.i("ysl","isRunning>>>" + isRunning);
            if (isRunning) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                mHandler.postDelayed(this, 3000);
            }
        }
    }

    public interface HeaderViewClickListener {
        void HeaderViewClick(int position);
    }

    private class MyAdapter extends PagerAdapter {

        //为了复用
        private List<ImageView> imgCache = new ArrayList<>();

        @Override
        public int getCount() {
            //无限滑动
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView iv;
            if (mUrlList == null) return null;

            //获取ImageView对象
            if (imgCache.size() > 0) {
                iv = imgCache.remove(0);
            } else {
                iv = new ImageView(mContext);
            }
            iv.setScaleType(ScaleType.FIT_XY);

            iv.setOnTouchListener(new OnTouchListener() {
                private int downX = 0;
                private long downTime = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mAutoRollRunnable.stop();
                            //获取按下的x坐标
                            downX = (int) v.getX();
                            downTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            mAutoRollRunnable.start();
                            int moveX = (int) v.getX();
                            long moveTime = System.currentTimeMillis();
                            if (downX == moveX && (moveTime - downTime < 500) && mUrlList.size() > 0) {//点击的条件
                                //轮播图回调点击事件
                                headerViewClickListener.HeaderViewClick(position % mUrlList.size());
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mAutoRollRunnable.start();
                            break;
                    }
                    return true;
                }
            });

            if (mUrlList.size() > 0){
                //加载图片
                Glide.with(mContext).load(mUrlList.get(position % mUrlList.size())).error(R.mipmap.default_img).into(iv);
            }

            ((ViewPager) container).addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            Logger.i("ysl","BannerView destroyItem>>>");
            if (object != null && object instanceof ImageView) {
                ImageView iv = (ImageView) object;
                ((ViewPager) container).removeView(iv);
                imgCache.add(iv);
            }
        }
    }

    /**
     * 获取数据list
     * @return
     */
    public List<String> getDataList(){
        return mUrlList;
    }

    @Override
    public void onPageSelected(int position) {
        if (mUrlList == null || mUrlList.size() == 0) return;
//        Logger.i("ysl","(position - 10000) % dotList.size()--" + (position - 10000) +  "|" + dotList.size() + "|" + ((position - 10000) % dotList.size()));
        dotList.get(prePosition == dotList.size() ? prePosition - 1 : prePosition).setBackgroundResource(R.mipmap.icon_dot_unselect);
        dotList.get((position - 10000) % dotList.size()).setBackgroundResource(R.mipmap.icon_dot_select);
        prePosition = (position - 10000) % dotList.size();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }


    @Override
    protected void onAttachedToWindow() {
//        Logger.i("ysl","BannerView onAttachedToWindow>>>");
        super.onAttachedToWindow();
        startRoll();
    }

    //停止轮播
    @Override
    protected void onDetachedFromWindow() {
//        Logger.i("ysl","BannerView onDetachedFromWindow>>>");
        if (((Activity) getContext()).isFinishing()) {
            super.onDetachedFromWindow();
        }
        stopRoll();
    }
}
