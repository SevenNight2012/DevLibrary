package com.xxc.dev.main.base.tab;

import com.google.android.material.tabs.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建tabView
 */
public interface ITabViewMaker {

    View onCreateTabView(int position, LayoutInflater inflater, ViewGroup parent);

    void updateTabView(int position, TabLayout tabLayout);

    View getTabView(int position, TabLayout tabLayout);

}
