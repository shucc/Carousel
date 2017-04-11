package org.cchao.sample.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by shucc on 17/2/10.
 * cc@cchao.org
 */
public class MyPtrClassicFrameLayout extends PtrFrameLayout {

    private final String TAG = getClass().getName();

    private float startY;
    private float startX;
    // 记录viewPager是否拖拽的标记
    private boolean mIsVpDragger;
    private final int mTouchSlop;

    private PtrClassicHeader mPtrClassicHeader;

    public MyPtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyPtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsVpDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if(mIsVpDragger) {
                    getChildAt(0).dispatchTouchEvent(ev);
                    return false;
                }
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceY < mTouchSlop) {
                    return false;
                }
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if(distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsVpDragger = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public PtrClassicHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}
