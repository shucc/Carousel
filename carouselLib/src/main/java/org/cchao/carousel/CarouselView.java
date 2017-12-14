package org.cchao.carousel;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.cchao.carousel.listener.CarouselAdapter;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.carousel.listener.OnPageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/3/30.
 * cc@cchao.org
 */
public class CarouselView extends FrameLayout implements Handler.Callback {

    private final String TAG = getClass().getName();

    private final String CAROUSEL_FRAGMENT_TAG = "org.cchao.carousel";

    private final int WHAT_SWITCH = 0;

    //默认自动切换时间
    private final int DEFAULT_DELAY_TIME = 5 * 1000;

    //自动切换间隔时间
    private int delayTime;

    //是否自动切换
    private boolean isAutoSwitch;

    //能否手动循环切换
    private boolean canLoop;

    //是否显示indicator
    private boolean isShowIndicator;

    //单个指示器宽高
    private int indicatorWidth;
    private int indicatorHeight;
    private int indicatorSelectedWidth;
    private int indicatorSelectedHeight;
    private int indicatorUnSelectedWidth;
    private int indicatorUnSelectedHeight;
    private boolean haveSpecialIndicator = false;

    //指示器间距
    private int indicatorPadding;

    //指示器距底部高度
    private int indicatorMarginBottom;

    //当前选中与未选中图标
    private int indicatorSelected;
    private int indicatorUnselected;

    private Context context;

    private ViewPager vpCarousel;

    private LinearLayout llIndicator;

    private CarouselLoopPageAdapter loopPageAdapter;

    private OnItemClickListener onItemClickListener;

    private CarouselLifecycleListener carouselLifecycleListener;

    private CarouselAdapter carouselPageAdapter;

    //当前选中
    private int nowSelect = 0;

    //之前选中
    private int preSelect = 0;

    //是否正在自动切换中
    private boolean isPlaying = false;

    private List<View> indicatorViews;

    private Handler handler;

    public CarouselView(Context context) {
        this(context, null);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initTypedArray(attrs);
        bindView(context);
        initView();
        handler = new Handler(this);
        carouselLifecycleListener = new CarouselLifecycleListener() {
            @Override
            public void onStop() {
                if (isAutoSwitch) {
                    stop();
                }
            }

            @Override
            public void onResume() {
                if (isAutoSwitch) {
                    resume();
                }
            }
        };
    }

    private void initTypedArray(AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CarouselView);
        delayTime = typedArray.getInt(R.styleable.CarouselView_carousel_delayTime, DEFAULT_DELAY_TIME);
        isAutoSwitch = typedArray.getBoolean(R.styleable.CarouselView_carousel_auto_switch, false);
        canLoop = typedArray.getBoolean(R.styleable.CarouselView_carousel_can_loop, true);
        isShowIndicator = typedArray.getBoolean(R.styleable.CarouselView_carousel_show_indicator, false);
        indicatorWidth = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_width
                , getResources().getDimensionPixelOffset(R.dimen.carousel_default_indicator_width));
        indicatorHeight = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_height
                , getResources().getDimensionPixelOffset(R.dimen.carousel_default_indicator_height));
        indicatorSelectedWidth = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_selected_width, 0);
        indicatorSelectedHeight = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_selected_height, 0);
        indicatorUnSelectedWidth = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_unselected_width, 0);
        indicatorUnSelectedHeight = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_unselected_height, 0);
        indicatorPadding = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_padding
                , getResources().getDimensionPixelOffset(R.dimen.carousel_default_indicator_padding));
        indicatorMarginBottom = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_margin_bottom
                , getResources().getDimensionPixelOffset(R.dimen.carousel_default_indicator_margin_bottom));
        indicatorSelected = typedArray.getResourceId(R.styleable.CarouselView_carousel_indicator_drawable_selected
                , R.drawable.ic_default_indicator_selected);
        indicatorUnselected = typedArray.getResourceId(R.styleable.CarouselView_carousel_indicator_drawable_unselected
                , R.drawable.ic_default_indicator_unselected);
        typedArray.recycle();
        haveSpecialIndicator = ((indicatorSelectedWidth > 0 && indicatorSelectedHeight > 0) || (indicatorUnSelectedWidth > 0 && indicatorUnSelectedHeight > 0));
    }

    private void bindView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel, this, true);
        vpCarousel = view.findViewById(R.id.vp_carousel);
        llIndicator = view.findViewById(R.id.ll_carousel_indicator);
    }

    private void initView() {
        RelativeLayout.MarginLayoutParams indicatorParams = (RelativeLayout.MarginLayoutParams) llIndicator.getLayoutParams();
        indicatorParams.bottomMargin = indicatorMarginBottom;
        llIndicator.setLayoutParams(indicatorParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = getLayoutParams().height;
        int width = getLayoutParams().width;

        if (height > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        if (width > 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == WHAT_SWITCH) {
            nowSelect++;
            loopPageAdapter.setRealCurrentItem(nowSelect);
        }
        return false;
    }

    public CarouselRequestManager with(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        CarouselFragment carouselFragment = new CarouselFragment();
        carouselFragment.setCarouselLifecycleListener(carouselLifecycleListener);
        fm.beginTransaction().add(carouselFragment, CAROUSEL_FRAGMENT_TAG).commitAllowingStateLoss();
        return new CarouselRequestManager(this);
    }

    public CarouselRequestManager with(Fragment fragment) {
        FragmentManager fm = fragment.getFragmentManager();
        CarouselFragment carouselFragment = new CarouselFragment();
        carouselFragment.setCarouselLifecycleListener(carouselLifecycleListener);
        fm.beginTransaction().add(carouselFragment, CAROUSEL_FRAGMENT_TAG).commitAllowingStateLoss();
        return new CarouselRequestManager(this);
    }

    public CarouselRequestManager with(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentManager fm = fragment.getChildFragmentManager();
        CarouselV4Fragment carouselFragment = new CarouselV4Fragment();
        carouselFragment.setCarouselLifecycleListener(carouselLifecycleListener);
        fm.beginTransaction().add(carouselFragment, CAROUSEL_FRAGMENT_TAG).commitAllowingStateLoss();
        return new CarouselRequestManager(this);
    }

    protected CarouselView setAutoSwitch(boolean autoSwitch) {
        isAutoSwitch = autoSwitch;
        return this;
    }

    protected CarouselView setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        return this;
    }

    protected CarouselView setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    protected CarouselView setShowIndicator(boolean showIndicator) {
        isShowIndicator = showIndicator;
        return this;
    }

    protected CarouselView setAdapter(@NonNull CarouselAdapter carouselPageAdapter) {
        this.carouselPageAdapter = carouselPageAdapter;
        return this;
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        this.onItemClickListener = listener;
        if (loopPageAdapter != null) {
            loopPageAdapter.setOnItemClickListener(listener);
        }
    }

    public void setOnPageListener(@NonNull OnPageListener onPageListener) {
        if (loopPageAdapter != null) {
            loopPageAdapter.setOnPageListener(onPageListener);
        }
    }

    public void setPageTransformer(boolean reverseDrawingOrder, @Nullable ViewPager.PageTransformer transformer) {
        vpCarousel.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * 开始轮播
     */
    protected void start() {
        if (null == carouselPageAdapter) {
            throw new NullPointerException("CarouselAdapter must not be null!");
        }
        if (carouselPageAdapter.getCount() <= 0) {
            throw new NullPointerException("Image list must not be null!");
        }
        if (carouselPageAdapter.getCount() == 1) {
            isAutoSwitch = false;
            llIndicator.setVisibility(GONE);
        } else {
            if (isShowIndicator) {
                llIndicator.setVisibility(VISIBLE);
            }
            indicatorViews = new ArrayList<>();
            addIndicator();
        }
        loopPageAdapter = new CarouselLoopPageAdapter(vpCarousel, carouselPageAdapter, canLoop);
        if (onItemClickListener != null) {
            setOnItemClickListener(onItemClickListener);
        }
        preSelect = 0;
        nowSelect = 0;
        loopPageAdapter.setOnPageSelectedListener(new CarouselLoopPageAdapter.OnPageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                stop();
                if (null != indicatorViews && preSelect != position) {
                    indicatorViews.get(preSelect).setBackgroundResource(indicatorUnselected);
                    indicatorViews.get(position).setBackgroundResource(indicatorSelected);
                    if (haveSpecialIndicator) {
                        LinearLayout.LayoutParams preViewParam = (LinearLayout.LayoutParams) indicatorViews.get(preSelect).getLayoutParams();
                        LinearLayout.LayoutParams nowViewParam = (LinearLayout.LayoutParams) indicatorViews.get(position).getLayoutParams();
                        if (indicatorSelectedWidth > 0 && indicatorSelectedHeight > 0) {
                            nowViewParam.width = indicatorSelectedWidth;
                            nowViewParam.height = indicatorSelectedHeight;
                        } else {
                            nowViewParam.width = indicatorWidth;
                            nowViewParam.height = indicatorHeight;
                        }
                        if (indicatorUnSelectedWidth > 0 && indicatorUnSelectedHeight > 0) {
                            preViewParam.width = indicatorUnSelectedWidth;
                            preViewParam.height = indicatorUnSelectedHeight;
                        } else {
                            preViewParam.width = indicatorWidth;
                            preViewParam.height = indicatorHeight;
                        }
                        indicatorViews.get(preSelect).setLayoutParams(preViewParam);
                        indicatorViews.get(position).setLayoutParams(nowViewParam);
                    }
                }
                preSelect = position;
                nowSelect = position;
                resume();
            }
        });
        vpCarousel.setAdapter(loopPageAdapter);
        if (carouselPageAdapter.getCount() > 1) {
            vpCarousel.setCurrentItem(canLoop ? carouselPageAdapter.getCount() * 10000 : 0);
        }
        resume();
    }

    /**
     * 添加底部Indicator点
     */
    private void addIndicator() {
        llIndicator.removeAllViews();
        indicatorViews.clear();
        for (int i = 0; i < carouselPageAdapter.getCount(); i++) {
            View indicatorView = new View(context);
            indicatorView.setBackgroundResource(i == 0 ? indicatorSelected : indicatorUnselected);
            llIndicator.addView(indicatorView);
            LinearLayout.MarginLayoutParams params = (LinearLayout.MarginLayoutParams) indicatorView.getLayoutParams();
            if (i == 0 && indicatorSelectedWidth > 0 && indicatorSelectedHeight > 0) {
                params.width = indicatorSelectedWidth;
                params.height = indicatorSelectedHeight;
            } else if (i > 0 && indicatorUnSelectedWidth > 0 && indicatorUnSelectedHeight > 0) {
                params.width = indicatorUnSelectedWidth;
                params.height = indicatorUnSelectedHeight;
            } else {
                params.width = indicatorWidth;
                params.height = indicatorHeight;
            }
            params.leftMargin = indicatorPadding / 2;
            params.rightMargin = indicatorPadding / 2;
            indicatorView.setLayoutParams(params);
            indicatorViews.add(indicatorView);
        }
    }

    /**
     * 恢复轮播
     */
    public void resume() {
        if (!isAutoSwitch) {
            return;
        }
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        handler.sendEmptyMessageDelayed(WHAT_SWITCH, delayTime);
    }

    /**
     * 停止轮播
     */
    public void stop() {
        isPlaying = false;
        handler.removeMessages(WHAT_SWITCH);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != handler) {
            stop();
        }
    }
}
