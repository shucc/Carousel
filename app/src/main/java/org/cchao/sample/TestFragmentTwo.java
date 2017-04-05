package org.cchao.sample;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.ImageloaderListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class TestFragmentTwo extends Fragment {

    private CarouselView carouselView;

    private List<String> imageUrls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_two, container, false);
        carouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
        initData();
        return rootView;
    }

    private void initData() {
        imageUrls = new ArrayList<>();
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2334645085,3449212359&fm=23&gp=0.jpg");
        imageUrls.add("http://pic18.nipic.com/20120115/4999414_151322555150_2.jpg");
        imageUrls.add("http://att.bbs.duowan.com/forum/201204/30/201115l6j65f42kjuljluf.jpg");
        carouselView.with(this)
                .setImageUrls(imageUrls)
                .setDealyTime(4 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(true)
                .setImageLoaderListener(new ImageloaderListener() {
                    @Override
                    public void loadImage(Context context, ImageView imageView, int position) {
                        Glide.with(context)
                                .load(imageUrls.get(position))
                                .into(imageView);
                    }
                })
                .start();
    }
}
