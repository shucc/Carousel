package org.cchao.carousel;

import android.support.v4.app.Fragment;

/**
 * Created by shucc on 17/12/14.
 * cc@cchao.org
 */
public class CarouselV4Fragment extends Fragment {

    private CarouselLifecycleListener carouselLifecycleListener;

    public void setCarouselLifecycleListener(CarouselLifecycleListener carouselLifecycleListener) {
        this.carouselLifecycleListener = carouselLifecycleListener;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != carouselLifecycleListener) {
            carouselLifecycleListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != carouselLifecycleListener) {
            carouselLifecycleListener.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}