package org.cchao.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.sample.Constant;
import org.cchao.sample.R;
import org.cchao.sample.adapter.MyAdapter;
import org.cchao.sample.model.MyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class TestFragmentOne extends Fragment {

    private CarouselView carouselView;

    private List<MyModel> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_one, container, false);
        carouselView = rootView.findViewById(R.id.carouselView);
        initData();
        return rootView;
    }

    private void initData() {
        carouselView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "Click position" + position, Toast.LENGTH_SHORT).show();
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
                .start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            carouselView.stop();
        } else {
            carouselView.resume();
        }
    }
}
