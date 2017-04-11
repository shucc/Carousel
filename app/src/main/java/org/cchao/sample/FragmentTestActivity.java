package org.cchao.sample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.ImageloaderListener;
import org.cchao.carousel.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentTestActivity extends AppCompatActivity {

    private CarouselView carouselView;

    private List<String> imageUrls;

    public static void launch(Context context) {
        Intent starter = new Intent(context, FragmentTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        imageUrls = new ArrayList<>();
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2334645085,3449212359&fm=23&gp=0.jpg");
        imageUrls.add("http://pic18.nipic.com/20120115/4999414_151322555150_2.jpg");
        imageUrls.add("http://att.bbs.duowan.com/forum/201204/30/201115l6j65f42kjuljluf.jpg");

        carouselView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(FragmentTestActivity.this, "Click position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        carouselView
                .with(this)
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

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_one, new TestFragmentOne()).commitAllowingStateLoss();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_two, new TestFragmentTwo()).commitAllowingStateLoss();
    }
}
