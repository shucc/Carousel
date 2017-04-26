package org.cchao.carousel.listener;

/**
 * Created by shucc on 17/4/26.
 * cc@cchao.org
 */
public interface OnPageListener {

    void onPageSelected(int position);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageScrollStateChanged(int state);
}
