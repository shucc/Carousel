package org.cchao.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.ImageloaderListener;
import org.cchao.carousel.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class SwipeRefreshTestActivity extends Activity {

    private final String TAG = getClass().getName();

    private VpSwipeRefreshLayout swipeRefreshLayout;

    private CarouselView carouselView;

    private List<String> imageUrls;

    public static void launch(Context context) {
        Intent starter = new Intent(context, SwipeRefreshTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiperefresh);
        swipeRefreshLayout = (VpSwipeRefreshLayout) findViewById(R.id.swiperefresh);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SwipeRefreshTestActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        imageUrls = new ArrayList<>();
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2334645085,3449212359&fm=23&gp=0.jpg");
        imageUrls.add("http://pic18.nipic.com/20120115/4999414_151322555150_2.jpg");
        imageUrls.add("http://att.bbs.duowan.com/forum/201204/30/201115l6j65f42kjuljluf.jpg");
        carouselView.with(this)
                .setImageUrls(imageUrls)
                .setDealyTime(5 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(false)
                .setImageLoaderListener(new ImageloaderListener() {
                    @Override
                    public void loadImage(Context context, ImageView imageView, int position) {
                        Glide.with(context)
                                .load(imageUrls.get(position))
                                .into(imageView);
                    }
                })
                .start();
        carouselView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(SwipeRefreshTestActivity.this, "Click position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
