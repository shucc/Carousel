package org.cchao.sample.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by shucc on 18/1/18.
 * cc@cchao.org
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "LoadMoreAdapter";

    private final int TYPE_FOOTER = -200;
    private final int TYPE_HEADER = -300;
    private final int TYPE_DEFAULT = -400;

    private RecyclerView recyclerView;

    private OnItemClickListener onItemClickListener;

    private View headerView = null;
    private View footerView = null;

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 添加底部
     *
     * @param view
     */
    public void addFooterView(View view) {
        if (view == null) {
            throw new NullPointerException("FooterView is null!");
        }
        if (footerView != null) {
            return;
        }
        footerView = view;
        footerView.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 移除底部View
     */
    public void removeFooterView() {
        if (footerView != null) {
            footerView = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 添加头部
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (view == null) {
            throw new NullPointerException("HeadView is null");
        }
        if (headerView != null) {
            return;
        }
        headerView = view;
        headerView.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        notifyDataSetChanged();
    }

    public void removeHeaderView() {
        if (headerView != null) {
            headerView = null;
            notifyDataSetChanged();
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (footerView != null && position >= (headerView == null ? getCount() : getCount() + 1)) {
            return TYPE_FOOTER;
        }
        return getItemType(headerView == null ? position : position - 1);
    }

    protected int getItemType(int position) {
        return TYPE_DEFAULT;
    }

    @Override
    public final int getItemCount() {
        int count = getCount();
        if (footerView != null) {
            count++;
        }
        if (headerView != null) {
            count++;
        }
        return count;
    }

    protected abstract int getCount();

    protected abstract RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType);

    protected abstract void onBindView(RecyclerView.ViewHolder holder, int position);

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new SpecialHolder(footerView);
        } else if (viewType == TYPE_HEADER) {
            return new SpecialHolder(headerView);
        } else {
            final RecyclerView.ViewHolder viewHolder = onCreateView(parent, viewType);
            if (onItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getAdapterPosition();
                        if (headerView != null) {
                            pos = pos - 1;
                        }
                        onItemClickListener.onItemClick(v, pos);
                    }
                });
            }
            return viewHolder;
        }
    }

    @Override
    public final void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof SpecialHolder)) {
            position = holder.getAdapterPosition();
            if (headerView != null) {
                position = position - 1;
            }
            onBindView(holder, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView1) {
        super.onAttachedToRecyclerView(recyclerView1);
        if (recyclerView == null) {
            recyclerView = recyclerView1;
        }
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder);
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        return layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams;
    }

    private void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        int viewType = holder.getItemViewType();
        if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    private class SpecialHolder extends RecyclerView.ViewHolder {

        public SpecialHolder(View itemView) {
            super(itemView);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
