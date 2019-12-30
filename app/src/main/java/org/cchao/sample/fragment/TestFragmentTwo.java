package org.cchao.sample.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.cchao.carousel.CarouselView;
import org.cchao.carousel.listener.OnItemClickListener;
import org.cchao.carousel.listener.OnPageListener;
import org.cchao.sample.Constant;
import org.cchao.sample.R;
import org.cchao.sample.adapter.MyAdapter;
import org.cchao.sample.model.MyModel;
import org.cchao.sample.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/4/5.
 * cc@cchao.org
 */
public class TestFragmentTwo extends Fragment {

    private final String TAG = getClass().getName();

    private CarouselView carouselView;

    private List<MyModel> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_two, container, false);
        carouselView = rootView.findViewById(R.id.carouselView);
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
        data = new ArrayList<>();
        for (int i = 0; i < Constant.IMAGE_ARRAY.length; i++) {
            MyModel myModel = new MyModel();
            myModel.setImageUrl(Constant.IMAGE_ARRAY[i]);
            myModel.setTitle("我是标题" + i);
            data.add(myModel);
        }
        carouselView.with(this)
                .setAdapter(new MyAdapter(data))
                .setDelayTime(4 * 1000)
                .setShowIndicator(true)
                .setAutoSwitch(false)
                .setCanLoop(false)
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
        carouselView.setPageTransformer(true, new DepthPageTransformer());
    }
}
