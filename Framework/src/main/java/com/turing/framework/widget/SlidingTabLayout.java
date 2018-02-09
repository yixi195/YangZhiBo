package com.turing.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.turing.framework.R;
import com.turing.framework.utils.Logger;

public class SlidingTabLayout extends LinearLayout {
    private static final String TAG = "SlidingTabLayout";

    private static final int DEFAULT_DIVIDER_LINE_HEIGHT = 1;
    private static final int DEFAULT_SHADOW_LINE_HEIGHT = 3;
    private static final int DEFAULT_SHADOW_WRAPPER_OFFSET = 10;
    private static final int MINIMUM_OFFSET_DRAG = 10;


    private static final int DEFAULT_DIVIDER_LINE_COLOR = 0xffdfdfdf;
    private static final int DEFAULT_SHADOW_LINE_COLOR = 0xfffed001;
    private static final int DEFAULT_SELECTED_TEXT_COLOR = 0xfffed001;
    private static final int DEFAULT_NORMAL_TEXT_COLOR = 0xff999999;

    private ViewPager mViewPager = null;
    private int mCurrentSelectedItem = 0;       //当前选中的项
    private int mDividerLineColor = DEFAULT_DIVIDER_LINE_COLOR; //分割线的颜色
    private int mShadowLineColor = DEFAULT_SHADOW_LINE_COLOR;       //指示线条的颜色
    private float mShadowLineWrapperOffset = DEFAULT_SHADOW_WRAPPER_OFFSET; //底部指示线相对于文字的偏移
    private int mSelectedTitleTextColor = DEFAULT_SELECTED_TEXT_COLOR;  //选中之后的标题栏颜色
    private int mNormalTitleTextColor = DEFAULT_NORMAL_TEXT_COLOR;  //正常的标题栏的颜色
    private boolean mShadowLineFillTab = true;      //指示的线条是否填充满整个标题title的底部
    private boolean mShowShadowLine = true;         //是否显示指示的线条

    private int mScrollPosition = 0;    //水平scrollview 滚东的位置
    private int mScreenWidth = 0;       //屏幕的宽度

    private HorizontalScrollView mParentScrollView;    //title 滚动的scrollview

    private float mScrollOffset = 0;
    private int mViewPagerState = 0;
    private boolean mMoveToRight = true;
    private int mLastPixesOffset = 0;
    private float mScreenDensity = 0;
    private float mTitleTextSize = 0;

    private LinearLayout mChildLayout;

    public SlidingTabLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);


    }

    private void init(Context context, AttributeSet attrs) {
        mChildLayout = new LinearLayout(getContext());
        mChildLayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mChildLayout.setLayoutParams(params);

        mTitleTextSize = getResources().getDimension(R.dimen.default_sliding_tab_layout_text_size);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout);
            int count = array.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = array.getIndex(i);

                if (attr == R.styleable.SlidingTabLayout_dividerLineColor) {
                    mDividerLineColor = array.getColor(attr, DEFAULT_DIVIDER_LINE_COLOR);
                } else if (attr == R.styleable.SlidingTabLayout_shadowLineColor) {
                    mShadowLineColor = array.getColor(attr, DEFAULT_SHADOW_LINE_COLOR);
                } else if (attr == R.styleable.SlidingTabLayout_normalTextColor) {
                    mNormalTitleTextColor = array.getColor(attr, DEFAULT_NORMAL_TEXT_COLOR);
                } else if (attr == R.styleable.SlidingTabLayout_highlightTextColor) {
                    mSelectedTitleTextColor = array.getColor(attr, DEFAULT_SELECTED_TEXT_COLOR);
                } else if (attr == R.styleable.SlidingTabLayout_shadowLineWrapperOffset) {
                    mShadowLineWrapperOffset = array.getDimension(attr, DEFAULT_SHADOW_WRAPPER_OFFSET);
                } else if (attr == R.styleable.SlidingTabLayout_shadowLineFillParent) {
                    mShadowLineFillTab = array.getBoolean(attr, mShadowLineFillTab);
                } else if (attr == R.styleable.SlidingTabLayout_showShadowLine) {
                    mShowShadowLine = array.getBoolean(attr, mShowShadowLine);
                } else if (attr == R.styleable.SlidingTabLayout_titleTextSize) {
                    mTitleTextSize = array.getDimension(attr, mTitleTextSize);
                }
            }
            array.recycle();
        }

        setBackgroundColor(Color.TRANSPARENT);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenDensity = dm.density;
        mScreenWidth = dm.widthPixels;
    }

    private View createDefaultTitle(CharSequence title){
        View v = inflate(getContext(), R.layout.sliding_tab_layout, null);
        TextView tv = (TextView)v.findViewById(R.id.tv_title);
        tv.setTextColor(mNormalTitleTextColor);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        LayoutParams lps = new LayoutParams( 0, LayoutParams.WRAP_CONTENT, 1);
        v.setLayoutParams(lps);
        return v;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;

        PagerAdapter adapter = mViewPager.getAdapter();
        int count = adapter.getCount();
        if (count <= 0) {
            return;
        }

        int totalViewWidth = 0;
        int maxWidth = 0;

        for (int i = 0 ; i < adapter.getCount(); i++) {
            View view = createDefaultTitle(adapter.getPageTitle(i));
            view.setTag(i);
            view.setOnClickListener(mPageSelectedListener);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int viewWidth = view.getMeasuredWidth();
            totalViewWidth += viewWidth;
            if (viewWidth > maxWidth) {
                maxWidth = viewWidth;
            }
            Logger.d(TAG, adapter.getPageTitle(i) + ",  view.getMeasuredWidth()=" + viewWidth);
            mChildLayout.addView(view);
        }


        float individualWidth = ((float)mScreenWidth) / adapter.getCount();
        Logger.d(TAG, "maxWidth=" + maxWidth + ", individualWidth=" + individualWidth);

        if (totalViewWidth > mScreenWidth || maxWidth > individualWidth) {
            mParentScrollView = new HorizontalScrollView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mParentScrollView.setLayoutParams(params);
            mParentScrollView.setHorizontalScrollBarEnabled(false);
            mParentScrollView.addView(mChildLayout);
            addView(mParentScrollView);
        } else {
            addView(mChildLayout);
        }

        mPageSelectedListener.onClick(mChildLayout.getChildAt(0));
        viewPager.addOnPageChangeListener(mPageChangeListener);
    }


    ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPagerState == ViewPager.SCROLL_STATE_DRAGGING) {
                if (mLastPixesOffset == 0) {
                    mLastPixesOffset = positionOffsetPixels;
                    return;
                }

                int offset = Math.abs(mLastPixesOffset - positionOffsetPixels);
                if (offset < MINIMUM_OFFSET_DRAG) {
                    return;
                }

                if (positionOffsetPixels > mLastPixesOffset){
                    mMoveToRight = true;
                    mScrollOffset = positionOffset;
                } else if (positionOffsetPixels < mLastPixesOffset) {
                    mMoveToRight = false;
                    mScrollOffset = 1 - positionOffset;
                }
                invalidate();
            }
        }

        @Override
        public void onPageSelected(int position) {
            Logger.d(TAG, "onPageSelected=" + position);
            swithFragment(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mViewPagerState = state;
            Logger.d(TAG, "onPageScrollStateChanged=" + state);
            if (state == ViewPager.SCROLL_STATE_SETTLING) {
                mLastPixesOffset = 0;
                mScrollOffset = 0;
                invalidate();
            }
        }
    };

    OnClickListener mPageSelectedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer)v.getTag();
            swithFragment(index);
        }
    };

    private void swithFragment(int index) {
        if (index != mCurrentSelectedItem) {
            updateTitleTextColor(mNormalTitleTextColor);

            //滚动标题栏
            if (mParentScrollView != null) {
                scrollTitle(mCurrentSelectedItem, index);
            }
        }

        mViewPager.setCurrentItem(index, true);
        mCurrentSelectedItem = index;
        updateTitleTextColor(mSelectedTitleTextColor);
        invalidate();
    }

    private void updateTitleTextColor(int color) {
        View v = mChildLayout.getChildAt(mCurrentSelectedItem);
        TextView tv = (TextView)v.findViewById(R.id.tv_title);
        tv.setTextColor(color);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowShadowLine) {
            drawDividerLine(canvas);
        }
    }


    /**
     * 画下面的分割线和指示线
     *
     * */
    private void drawDividerLine(Canvas canvas) {
        float height = getHeight();
        float width = getWidth();

        View v = mChildLayout.getChildAt(mCurrentSelectedItem);
        float left = v.getLeft();
        float right = v.getRight();

        if (!mShadowLineFillTab) {
            TextView tv = (TextView)v.findViewById(R.id.tv_title);
            left = v.getLeft() + tv.getLeft();
            right = left + tv.getWidth();
            float wrapperOffset = mShadowLineWrapperOffset * mScreenDensity;
            left -= wrapperOffset;
            right += wrapperOffset;
        }

        float offset = mScrollOffset * v.getWidth();
        offset = mMoveToRight ? offset : -offset;

        left += offset;
        right += offset;

        Paint paint = new Paint();
        paint.setStrokeWidth(DEFAULT_DIVIDER_LINE_HEIGHT * mScreenDensity);
        paint.setColor(mDividerLineColor);
        canvas.drawLine(0, height, width, height, paint);


        paint.setColor(mShadowLineColor);
        paint.setStrokeWidth(DEFAULT_SHADOW_LINE_HEIGHT * mScreenDensity);
        float offsetHeight = height - (DEFAULT_DIVIDER_LINE_HEIGHT * mScreenDensity);
        canvas.drawLine(left, offsetHeight, right, offsetHeight, paint);
    }


    /**
     * 滚动父控件scrollview
     * */
    private void scrollTitle(int currentIndex, int scrollTo) {
        if (currentIndex == scrollTo || mParentScrollView == null) {
            return;
        }

        View v = mChildLayout.getChildAt(scrollTo);

        int left = v.getLeft();
        int right = v.getRight();

        boolean moveRight = scrollTo > currentIndex;
        //向右滑动
        if (moveRight && right - mScrollPosition > mScreenWidth) {
            mScrollPosition = right - mScreenWidth;
        }

       //向左滑动
       if (!moveRight && left < mScrollPosition) {
           mScrollPosition = left;
       }

       mParentScrollView.scrollTo(mScrollPosition,0);
    }
}
