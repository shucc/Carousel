package org.cchao.carousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.cchao.carousel.listener.ImageloaderListener;
import org.cchao.carousel.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by shucc on 17/3/30.
 * cc@cchao.org
 */
public class CarouselLoopPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    private List<String> imageUrls;

    private List<String> titles;

    private boolean showTitle;

    private int titleColor;

    private int titleSize;

    private int titleMarginBottom;

    private int nowSelect;
    
    private int fragmentSize = 0;

    private Context context;

    private ImageloaderListener imageloaderListener;

    private OnItemClickListener onItemClickListener;

    private OnPageSelectedListener onPageSelectedListener;

    public CarouselLoopPageAdapter(ViewPager viewPager, List<String> imageUrls, List<String> titles
            , boolean showTitle, int titleColor, int titleSize, int titleMarginBottom, ImageloaderListener imageloaderListener) {
        this.viewPager = viewPager;
        this.imageUrls = imageUrls;
        this.showTitle = showTitle;
        this.titles = titles;
        this.titleColor = titleColor;
        this.titleSize = titleSize;
        this.titleMarginBottom = titleMarginBottom;
        this.imageloaderListener = imageloaderListener;
        fragmentSize = imageUrls.size();
        viewPager.setOnPageChangeListener(this);
        nowSelect = fragmentSize * 10000;
    }

    public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener) {
        this.onPageSelectedListener = onPageSelectedListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setRealCurrentItem(int position) {
        int index = position - (nowSelect % fragmentSize);
        if (index < 0) {
            index = index + fragmentSize;
        }
        viewPager.setCurrentItem(nowSelect + index, true);
    }

    @Override
    public int getCount() {
        if (imageUrls.size() == 1) {
            return 1;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int pos = position % fragmentSize;
        context = container.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_item, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.img_carousel);

        if (showTitle && pos < titles.size()){
            TextView textTitle = (TextView) view.findViewById(R.id.text_carousel_title);
            RelativeLayout.MarginLayoutParams params = (RelativeLayout.MarginLayoutParams) textTitle.getLayoutParams();
            params.bottomMargin = titleMarginBottom;
            textTitle.setLayoutParams(params);
            textTitle.setTextColor(titleColor);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            textTitle.setText(titles.get(pos));
            textTitle.setVisibility(View.VISIBLE);
            View shadowView = view.findViewById(R.id.view_carousel_shadow);
            shadowView.setVisibility(View.VISIBLE);
        }
        imageloaderListener.loadImage(context, imageView, pos);
        if (onItemClickListener != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(imageView, pos);
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void onPageSelected(int position) {
        nowSelect = position;
        if (onPageSelectedListener != null) {
            onPageSelectedListener.onPageSelected(position % fragmentSize);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        
    }

    protected interface OnPageSelectedListener {
        void onPageSelected(int position);
    }
}
