package com.xxc.dev.main.adapter;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;

public abstract class BaseHolder<D> extends ViewHolder {

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void attachItem(D data);

    public <V extends View> V findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public Context getContext() {
        return itemView.getContext();
    }
}
