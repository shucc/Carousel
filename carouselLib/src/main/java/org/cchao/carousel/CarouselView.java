package org.cchao.carousel;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/3/30.
 * cc@cchao.org
 */
public class CarouselView extends RelativeLayout {

    private final String TAG = getClass().getName();

    private final int WHAT_SWITCH = 0;

    //默认自动切换时间
    private final int DEFAULT_DELAY_TIME = 5 * 1000;

    //自动切换间隔时间
    private int delayTime;

    //是否自动切换
    private boolean isAutoSwitch;

    //是否显示indicator
    private boolean isShowIndicator;

    //单个指示器宽高
    private int indicatorWidth;
    private int indicatorHeight;

    //指示器间距
    private int indicatorPadding;

    //指示器距底部高度
    private int indicatorMarginBottom;

    //当前选中与未选中图标
    private int indicatorSelected;
    private int indicatorUnselected;

    private Context context;

    private List<String> imageUrls;

    private ViewPager vpCarousel;

    private LinearLayout llIndicator;

    private CarouselLoopPageAdapter loopPageAdapter;

    private ImageloaderListener imageloaderListener;

    private OnItemClickListener onItemClickListener;

    //当前选中
    private int nowSelect = 0;

    //之前选中
    private int preSelect = 0;

    private int imageSize = 0;

    //是否正在自动切换中
    private boolean isPlaying = false;

    private List<View> indicatorViews;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_SWITCH) {
                nowSelect++;
                loopPageAdapter.setRealCurrentItem(nowSelect);
            }
        }
    };

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
        imageUrls = new ArrayList<>();
    }

    private void initTypedArray(AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CarouselView);
        delayTime = typedArray.getInt(R.styleable.CarouselView_carousel_delayTime, DEFAULT_DELAY_TIME);
        isAutoSwitch = typedArray.getBoolean(R.styleable.CarouselView_carousel_auto_switch, false);
        isShowIndicator = typedArray.getBoolean(R.styleable.CarouselView_carousel_show_indicator, false);
        indicatorWidth = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_width, getResources().getDimensionPixelOffset(R.dimen.indicator_default_width));
        indicatorHeight= typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_height, getResources().getDimensionPixelOffset(R.dimen.indicator_default_height));
        indicatorPadding = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_padding, getResources().getDimensionPixelOffset(R.dimen.indicator_default_padding));
        indicatorMarginBottom = typedArray.getDimensionPixelOffset(R.styleable.CarouselView_carousel_indicator_margin_bottom, getResources().getDimensionPixelOffset(R.dimen.indicator_default_margin_bottom));
        indicatorSelected = typedArray.getResourceId(R.styleable.CarouselView_carousel_indicator_drawable_selected, R.drawable.ic_default_indicator_selected);
        indicatorUnselected = typedArray.getResourceId(R.styleable.CarouselView_carousel_indicator_drawable_unselected, R.drawable.ic_default_indicator_unselected);
        typedArray.recycle();
    }

    private void bindView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel, this, true);
        vpCarousel = (ViewPager) view.findViewById(R.id.vp_carousel);
        llIndicator = (LinearLayout) view.findViewById(R.id.ll_carousel_indicator);
    }

    private void initView() {
        RelativeLayout.MarginLayoutParams indicatorParams = (RelativeLayout.MarginLayoutParams) llIndicator.getLayoutParams();
        indicatorParams.bottomMargin = indicatorMarginBottom;
        llIndicator.setLayoutParams(indicatorParams);
    }

    public CarouselView setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        if (null != imageUrls) {
            imageSize = imageUrls.size();
        }
        return this;
    }

    public CarouselView setAutoSwitch(boolean autoSwitch) {
        isAutoSwitch = autoSwitch;
        return this;
    }

    public CarouselView setDealyTime(int dealyTime) {
        this.delayTime = dealyTime;
        return this;
    }

    public CarouselView setShowIndicator(boolean showIndicator) {
        isShowIndicator = showIndicator;
        return this;
    }

    public CarouselView setImageLoaderListener(ImageloaderListener imageLoaderListener) {
        this.imageloaderListener = imageLoaderListener;
        return this;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 开始轮播
     */
    public void start() {
        if (null == imageUrls || imageUrls.isEmpty()) {
            throw new NullPointerException("Images must not be null!");
        }
        if (null == imageloaderListener) {
            throw new NullPointerException("ImageLoaderListener must not be null!");
        }
        if (imageSize == 1) {
            isAutoSwitch = false;
            llIndicator.setVisibility(GONE);
        } else {
            if (isShowIndicator) {
                llIndicator.setVisibility(VISIBLE);
            }
            indicatorViews = new ArrayList<>();
            addIndicator();
        }
        loopPageAdapter = new CarouselLoopPageAdapter(vpCarousel, imageUrls, imageloaderListener);
        if (onItemClickListener != null) {
            loopPageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    onItemClickListener.onClick(view, position);
                }
            });
        }
        loopPageAdapter.setOnPageSelectedListener(new CarouselLoopPageAdapter.OnPageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                if (null != indicatorViews) {
                    indicatorViews.get(preSelect).setBackgroundResource(indicatorUnselected);
                    indicatorViews.get(position).setBackgroundResource(indicatorSelected);
                }
                preSelect = position;
                nowSelect = position;
                stop();
                resume();
            }
        });
        vpCarousel.setAdapter(loopPageAdapter);
        if (imageSize > 1) {
            vpCarousel.setCurrentItem(imageUrls.size() * 10000);
        }
        resume();
    }

    /**
     * 添加底部Indicator点
     */
    private void addIndicator() {
        llIndicator.removeAllViews();
        indicatorViews.clear();
        for (int i = 0; i < imageSize; i++) {
            View indicatorView = new View(context);
            indicatorView.setBackgroundResource(i == 0 ? indicatorSelected : indicatorUnselected);
            llIndicator.addView(indicatorView);
            LinearLayout.MarginLayoutParams params = (LinearLayout.MarginLayoutParams) indicatorView.getLayoutParams();
            params.width = indicatorWidth;
            params.height = indicatorHeight;
            if (i > 0) {
                params.leftMargin = indicatorPadding;
            }
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
}
