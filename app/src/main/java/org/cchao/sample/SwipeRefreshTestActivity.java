package org.cchao.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.sample.adapter.MyAdapter;
import org.cchao.sample.model.MyModel;
import org.cchao.sample.widget.VpSwipeRefreshLayout;

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

    private List<MyModel> data;

    public static void launch(Context context) {
        Intent starter = new Intent(context, SwipeRefreshTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiperefresh);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        carouselView = findViewById(R.id.carouselView);

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

        data = new ArrayList<>();
        for (int i = 0; i < Constant.IMAGE_ARRAY.length; i++) {
            MyModel myModel = new MyModel();
            myModel.setImageUrl(Constant.IMAGE_ARRAY[i]);
            myModel.setTitle("我是标题" + i);
            data.add(myModel);
        }

        carouselView.with(this)
                .setAdapter(new MyAdapter(data))
                .setDelayTime(5 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(false)
                .start();
        carouselView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(SwipeRefreshTestActivity.this, "Click position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
