package org.cchao.carousel;

import android.support.annotation.NonNull;

import org.cchao.carousel.listener.CarouselAdapter;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class CarouselRequestManager {

    private CarouselView carouselView;

    public CarouselRequestManager(CarouselView carouselView) {
        this.carouselView = carouselView;
    }

    public CarouselRequestManager setAutoSwitch(boolean autoSwitch) {
        carouselView.setAutoSwitch(autoSwitch);
        return this;
    }

    public CarouselRequestManager setCanLoop(boolean canLoop) {
        carouselView.setCanLoop(canLoop);
        return this;
    }

    public CarouselRequestManager setDelayTime(int delayTime) {
        carouselView.setDelayTime(delayTime);
        return this;
    }

    public CarouselRequestManager setShowIndicator(boolean showIndicator) {
        carouselView.setShowIndicator(showIndicator);
        return this;
    }

    public CarouselRequestManager setAdapter(@NonNull CarouselAdapter carouselPageAdapter) {
        carouselView.setAdapter(carouselPageAdapter);
        return this;
    }

    public void start() {
        carouselView.start();
    }
}
