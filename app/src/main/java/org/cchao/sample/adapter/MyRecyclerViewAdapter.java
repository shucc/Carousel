package org.cchao.sample.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cchao.sample.R;

import java.util.List;

/**
 * Created by shucc on 18/1/18.
 * cc@cchao.org
 */
public class MyRecyclerViewAdapter extends BaseAdapter {

    private List<String> data;

    public MyRecyclerViewAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    protected int getCount() {
        return data.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new MyHolder(view);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder) holder).textData.setText(data.get(position));
    }

    protected class MyHolder extends RecyclerView.ViewHolder {

        TextView textData;

        public MyHolder(View itemView) {
            super(itemView);
            textData = itemView.findViewById(R.id.text_data);
        }
    }
}
