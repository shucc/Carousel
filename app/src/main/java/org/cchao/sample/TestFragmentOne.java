package org.cchao.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class TestFragmentOne extends Fragment {

    private CarouselView carouselView;

    private List<String> imageUrls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_one, container, false);
        carouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
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
        imageUrls = new ArrayList<>();
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491387197409&di=8cf281305ca82c2e697fccce92e33d0c&imgtype=0&src=http%3A%2F%2Fimg1.mydrivers.com%2Fimg%2F20140831%2F9fc2b8079ae04e0bb61f384302a2a33e.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491387197409&di=03873637a14711a3c0435d93024adb63&imgtype=0&src=http%3A%2F%2Fpic37.nipic.com%2F20140107%2F17589918_003530809000_2.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491387197409&di=bc41cecf5921c4c5cdc647d1e14fae08&imgtype=0&src=http%3A%2F%2Fpic46.nipic.com%2F20140823%2F18505720_094237483000_2.jpg");
        carouselView.with(this)
                .setImageUrls(imageUrls)
                .setImageLoaderListener(new ImageloaderListener() {
                    @Override
                    public void loadImage(Context context, ImageView imageView, int position) {
                        Glide.with(context)
                                .load(imageUrls.get(position))
                                .into(imageView);
                    }
                })
                .start();
    }
}
