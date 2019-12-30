package org.cchao.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.cchao.carousel.listener.CarouselAdapter;
import org.cchao.sample.R;
import org.cchao.sample.model.MyModel;

import java.util.List;

/**
 * Created by shucc on 17/12/14.
 * cc@cchao.org
 */
public class MyAdapter implements CarouselAdapter {

    private List<MyModel> data;

    public MyAdapter(List<MyModel> data) {
        this.data = data;
    }

    @Override
    public View getView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        ImageView imgCarousel = view.findViewById(R.id.img_carousel);
        Glide.with(parent.getContext())
                .load(data.get(position).getImageUrl())
                .centerCrop()
                .into(imgCarousel);
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
