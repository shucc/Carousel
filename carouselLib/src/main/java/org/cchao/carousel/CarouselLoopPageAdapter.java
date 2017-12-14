package org.cchao.carousel;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import org.cchao.carousel.listener.CarouselAdapter;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.carousel.listener.OnPageListener;

/**
 * Created by shucc on 17/3/30.
 * cc@cchao.org
 */
class CarouselLoopPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private final String TAG = getClass().getName();

    private ViewPager viewPager;

    private boolean canLoop;

    private int nowSelect;

    private int fragmentSize = 0;

    private OnItemClickListener onItemClickListener;

    private OnPageListener onPageListener;

    private OnPageSelectedListener onPageSelectedListener;

    private CarouselAdapter carouselPageAdapter;

    public CarouselLoopPageAdapter(ViewPager viewPager, CarouselAdapter carouselPageAdapter, boolean canLoop) {
        this.viewPager = viewPager;
        this.carouselPageAdapter = carouselPageAdapter;
        this.canLoop = canLoop;
        fragmentSize = carouselPageAdapter.getCount();
        viewPager.setOnPageChangeListener(this);
        if (!canLoop) {
            nowSelect = 0;
        } else {
            nowSelect = fragmentSize * 10000;
        }
    }

    public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener) {
        this.onPageSelectedListener = onPageSelectedListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnPageListener(OnPageListener onPageListener) {
        this.onPageListener = onPageListener;
    }

    public void setRealCurrentItem(int position) {
        if (canLoop) {
            int index = position - (nowSelect % fragmentSize);
            if (index < 0) {
                index = index + fragmentSize;
            }
            viewPager.setCurrentItem(nowSelect + index, true);
        } else {
            viewPager.setCurrentItem(position % fragmentSize, true);
        }
    }

    @Override
    public int getCount() {
        if (fragmentSize == 1) {
            return 1;
        }
        if (canLoop) {
            return Integer.MAX_VALUE;
        } else {
            return fragmentSize;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int pos = position % fragmentSize;
        View view = carouselPageAdapter.getView(container, pos);
        if (null != onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(view, pos);
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
        if (onPageListener != null) {
            onPageListener.onPageSelected(position % fragmentSize);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageListener != null) {
            onPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageListener != null) {
            onPageListener.onPageScrollStateChanged(state);
        }
    }

    protected interface OnPageSelectedListener {
        void onPageSelected(int position);
    }
}
