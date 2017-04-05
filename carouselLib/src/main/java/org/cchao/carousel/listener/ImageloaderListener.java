package org.cchao.carousel.listener;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by shucc on 17/4/1.
 * cc@cchao.org
 */
public interface ImageloaderListener {

    void loadImage(Context context, ImageView imageView, int position);
}
