package org.cchao.sample.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
 * Created by shucc on 17/5/3.
 * cc@cchao.org
 */
public class ViewPagerFragment extends Fragment {

    private final String TAG = getClass().getName();

    private CarouselView carouselView;

    private List<MyModel> data;

    public static ViewPagerFragment newInstance() {
        Bundle args = new Bundle();
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
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
    }
}
