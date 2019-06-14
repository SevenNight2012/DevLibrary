package com.xxc.dev.main.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 继承自V4包下的BaseFragment
 */
public abstract class BaseFragmentV4 extends Fragment {

    protected View mRootView;
    protected LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mRootView = mInflater.inflate(getContentId(), container, false);
        initWidgets(savedInstanceState);
        return mRootView;
    }

    public abstract int getContentId();

    public abstract void initWidgets(Bundle savedInstanceState);

    public <T> T findViewById(int id) {
        if (null == mRootView) {
            return null;
        }
        return (T) mRootView.findViewById(id);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        writeInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void writeInstanceState(Bundle outState) {

    }

}
