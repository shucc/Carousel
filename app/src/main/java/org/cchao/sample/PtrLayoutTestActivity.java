package org.cchao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.ImageloaderListener;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.sample.ptr.MyPtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by shucc on 17/4/11.
 * cc@cchao.org
 */
public class PtrLayoutTestActivity extends AppCompatActivity {

    private MyPtrClassicFrameLayout ptrLayout;

    private CarouselView carouselView;

    private List<String> imageUrls;

    public static void launch(Context context) {
        Intent starter = new Intent(context, PtrLayoutTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptrlayout);

        ptrLayout = (MyPtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        ptrLayout.disableWhenHorizontalMove(true);
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();
                        Toast.makeText(PtrLayoutTestActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

        imageUrls = new ArrayList<>();
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2334645085,3449212359&fm=23&gp=0.jpg");
        imageUrls.add("http://pic18.nipic.com/20120115/4999414_151322555150_2.jpg");
        imageUrls.add("http://att.bbs.duowan.com/forum/201204/30/201115l6j65f42kjuljluf.jpg");
        carouselView.with(this)
                .setImageSize(imageUrls.size())
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
                Toast.makeText(PtrLayoutTestActivity.this, "Click position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
