package org.cchao.sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.ImageloaderListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CarouselView carouselView;

    private List<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        imageUrls = new ArrayList<>();
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2334645085,3449212359&fm=23&gp=0.jpg");
        imageUrls.add("http://pic18.nipic.com/20120115/4999414_151322555150_2.jpg");
        imageUrls.add("http://att.bbs.duowan.com/forum/201204/30/201115l6j65f42kjuljluf.jpg");

        carouselView
                .setImageUrls(imageUrls)
                .setDealyTime(5 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(true)
                .setImageLoaderListener(new ImageloaderListener() {
                    @Override
                    public void loadImage(Context context, ImageView imageView, int position) {
                        Glide.with(context)
                                .load(imageUrls.get(position))
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(imageView);
                    }
                })
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carouselView.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        carouselView.stop();
    }
}
