package org.cchao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.cchao.carousel.CarouselView;
import org.cchao.sample.adapter.MyAdapter;
import org.cchao.sample.adapter.MyRecyclerViewAdapter;
import org.cchao.sample.model.MyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 18/1/18.
 * cc@cchao.org
 */
public class RecyclerViewTestActivity extends AppCompatActivity {

    private List<String> data;

    public static void launch(Context context) {
        Intent starter = new Intent(context, RecyclerViewTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView rvData = findViewById(R.id.rv_data);

        View headView = getLayoutInflater().inflate(R.layout.view_head, null);
        CarouselView cvData = headView.findViewById(R.id.cv_data);
        List<MyModel> imageData = new ArrayList<>();
        for (int i = 0; i < Constant.IMAGE_ARRAY.length; i++) {
            MyModel myModel = new MyModel();
            myModel.setImageUrl(Constant.IMAGE_ARRAY[i]);
            myModel.setTitle("我是标题" + i);
            imageData.add(myModel);
        }
        cvData.with(this)
                .setAdapter(new MyAdapter(imageData))
                .setDelayTime(3 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(true)
                .start();

        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("标题" + i);
        }
        rvData.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(data);
        rvData.setAdapter(adapter);
        adapter.addHeaderView(headView);
    }
}
