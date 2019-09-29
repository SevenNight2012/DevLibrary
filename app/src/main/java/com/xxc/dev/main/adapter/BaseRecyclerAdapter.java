package com.xxc.dev.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<Data, Holder extends BaseHolder<? extends Data>> extends RecyclerView.Adapter<Holder> {

    protected List<Data> mData = new ArrayList<>();

    public void addDatas(List<Data> data) {
        if (data != null && data.size() > 0) {
            int oldCount = mData.size();
            mData.addAll(data);
            if (mData.size() == data.size()) {
                notifyDataSetChanged();
            } else {
                notifyItemRangeInserted(oldCount, mData.size());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        Data data = mData.get(position);
        try {
            holder.attachItem(data);
            onBindData((Holder) holder, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBindData(Holder holder, Data data) {

    }

    public void addData(Data data) {
        if (data != null) {
            int oldCount = mData.size();
            mData.add(data);
            notifyItemRangeInserted(oldCount, 1);
        }
    }

    public void removeData(int position) {
        if (position < 0 || position >= mData.size()) {
            return;
        }
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

}
