package org.cchao.carousel;

import org.cchao.carousel.listener.ImageLoaderListener;

import java.util.List;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class CarouselRequestManager {

    private CarouselView carouselView;

    public CarouselRequestManager(CarouselView carouselView) {
        this.carouselView = carouselView;
    }

    public CarouselRequestManager setImageSize(int imageSize) {
        carouselView.setImageSize(imageSize);
        return this;
    }

    public CarouselRequestManager setTitles(List<String> titles) {
        carouselView.setTitles(titles);
        return this;
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

    public CarouselRequestManager setShowTitle(boolean showTitle) {
        carouselView.setShowTitle(showTitle);
        return this;
    }

    public CarouselRequestManager setInterceptParent(boolean interceptParent) {
        carouselView.setInterceptParent(interceptParent);
        return this;
    }

    public CarouselRequestManager setImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        carouselView.setImageLoaderListener(imageLoaderListener);
        return this;
    }

    public void start() {
        carouselView.start();
    }
}
