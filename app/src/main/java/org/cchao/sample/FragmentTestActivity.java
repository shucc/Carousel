package org.cchao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.sample.adapter.MyAdapter;
import org.cchao.sample.fragment.TestFragmentOne;
import org.cchao.sample.fragment.TestFragmentTwo;
import org.cchao.sample.model.MyModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentTestActivity extends AppCompatActivity {

    private CarouselView carouselView;

    private List<MyModel> data;

    public static void launch(Context context) {
        Intent starter = new Intent(context, FragmentTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        carouselView = findViewById(R.id.carouselView);
        data = new ArrayList<>();
        for (int i = 0; i < Constant.IMAGE_ARRAY.length; i++) {
            MyModel myModel = new MyModel();
            myModel.setImageUrl(Constant.IMAGE_ARRAY[i]);
            myModel.setTitle("我是标题" + i);
            data.add(myModel);
        }

        carouselView
                .with(this)
                .setAdapter(new MyAdapter(data))
                .setDelayTime(5 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(true)
                .start();
        carouselView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(FragmentTestActivity.this, "点击:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_one, new TestFragmentOne()).commitAllowingStateLoss();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_two, new TestFragmentTwo()).commitAllowingStateLoss();
    }
}
