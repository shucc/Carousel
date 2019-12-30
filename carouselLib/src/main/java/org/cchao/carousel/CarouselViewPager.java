package org.cchao.carousel;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Created by shucc on 18/1/18.
 * cc@cchao.org
 */
public class CarouselViewPager extends ViewPager {

    public CarouselViewPager(@NonNull Context context) {
        super(context);
    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null == getAdapter()) {
            return;
        }
        try {
            //处理调用onDetachFromWindow后动画停用问题
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
            getAdapter().notifyDataSetChanged();
            setCurrentItem(getCurrentItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
