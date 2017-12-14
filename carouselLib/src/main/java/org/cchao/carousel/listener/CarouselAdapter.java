package org.cchao.carousel.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shucc on 17/12/14.
 * cc@cchao.org
 */
public interface CarouselAdapter {
    View getView(ViewGroup parent, int position);

    int getCount();
}
