package org.cchao.carousel;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by shucc on 17/4/26.
 * cc@cchao.org
 */
public class CarouselViewPager extends ViewPager {

    private final String TAG = getClass().getName();

    private float startY;
    private float startX;
    // 记录viewPager是否拖拽的标记
    private boolean isVPDragger;
    private final int touchSlop;

    private boolean interceptParent = true;

    public CarouselViewPager(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!interceptParent) {
            return super.onInterceptTouchEvent(ev);
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                isVPDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (isVPDragger) {
                    return false;
                }
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceY > touchSlop && distanceY > distanceX) {
                    isVPDragger = true;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                isVPDragger = false;
                break;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    public void setInterceptParent(boolean interceptParent) {
        this.interceptParent = interceptParent;
    }
}
