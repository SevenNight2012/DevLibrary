package com.xxc.dev.common.widgets.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.xxc.dev.common.R;
import java.util.List;

/**
 * 首页搜索页的bannerItem的渲染器
 */
public abstract class SimplePagerItemInflater<D> implements PagerItemInflater {

    protected List<D> mModels;

    public SimplePagerItemInflater(@NonNull List<D> models) {
        this.mModels = models;
    }

    @Override
    public int modelCounts() {
        return mModels.size();
    }

    @NonNull
    @Override
    public View onRefreshItemView(View itemView, ViewGroup parent, int position) {
        Context context = parent.getContext();
        SimpleViewHolder itemViewHolder;
        if (null == itemView) {
            itemViewHolder = onCreateViewHolder(context, parent, position);
            itemView = itemViewHolder.mItemView;
            itemView.setTag(R.id.banner_item_holder, itemViewHolder);
        } else {
            itemViewHolder = (SimpleViewHolder) itemView.getTag(R.id.banner_item_holder);
        }
        itemViewHolder.bindData(mModels.get(position), position);
        return itemView;
    }

    @Override
    public void onItemSelected(int position) {

    }

    public abstract SimpleViewHolder onCreateViewHolder(Context context, ViewGroup parent, int position);

    public static abstract class SimpleViewHolder<D> {

        protected View mItemView;

        public SimpleViewHolder(@NonNull View itemView) {
            mItemView = itemView;
        }

        public abstract void bindData(D data, int position);
    }

}
