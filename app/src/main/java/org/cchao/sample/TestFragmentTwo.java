package org.cchao.sample;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.ImageloaderListener;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.carousel.listener.OnPageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class TestFragmentTwo extends Fragment {

    private final String TAG = getClass().getName();

    private CarouselView carouselView;

    private List<String> imageUrls;

    private List<String> titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_two, container, false);
        carouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
        initData();
        return rootView;
    }

    private void initData() {
        carouselView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "hehe" + position, Toast.LENGTH_SHORT).show();
            }
        });
        imageUrls = new ArrayList<>();
        titles = new ArrayList<>();
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2334645085,3449212359&fm=23&gp=0.jpg");
        imageUrls.add("http://pic18.nipic.com/20120115/4999414_151322555150_2.jpg");
        imageUrls.add("http://att.bbs.duowan.com/forum/201204/30/201115l6j65f42kjuljluf.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492060262991&di=4c3e773b8229057c53b1c36bf16d54c4&imgtype=0&src=http%3A%2F%2Fpic30.nipic.com%2F20130621%2F11295670_223619063339_2.jpg");
        titles.add("我是标题一");
        titles.add("我是标题二");
        titles.add("我是标题三");
        titles.add("我是标题四");
        carouselView.with(this)
                .setImageUrls(imageUrls)
                .setTitles(titles)
                .setDealyTime(4 * 1000)
                .setShowIndicator(true)
                .setShowTitle(true)
                .setAutoSwitch(false)
                .setCanLoop(false)
                .setImageLoaderListener(new ImageloaderListener() {
                    @Override
                    public void loadImage(Context context, ImageView imageView, int position) {
                        Glide.with(context)
                                .load(imageUrls.get(position))
                                .into(imageView);
                    }
                })
                .start();
        carouselView.setOnPageListener(new OnPageListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: ");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: ");
            }
        });
    }
}
