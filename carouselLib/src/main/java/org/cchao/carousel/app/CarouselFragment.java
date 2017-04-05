package org.cchao.carousel.app;

import android.app.Fragment;

import org.cchao.carousel.listener.CarouselLifecycleListener;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class CarouselFragment extends Fragment {

    private final String TAG = getClass().getName();

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
        carouselLifecycleListener.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        carouselLifecycleListener.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
