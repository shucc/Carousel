package org.cchao.carousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    private int nowSelect;
    
    private int fragmentSize = 0;

    private Context context;

    private ImageloaderListener imageloaderListener;

    private OnItemClickListener onItemClickListener;

    private OnPageSelectedListener onPageSelectedListener;

    public CarouselLoopPageAdapter(ViewPager viewPager, List<String> imageUrls, ImageloaderListener imageloaderListener) {
        this.viewPager = viewPager;
        this.imageUrls = imageUrls;
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
