package com.xxc.dev.main.base.tab;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import android.view.View;

/**
 * 创建TabView的基类
 */
public abstract class BaseTabViewMaker implements ITabViewMaker, OnTabSelectedListener {

    @Override
    public void updateTabView(int position, TabLayout tabLayout) {

    }

    @Override
    public View getTabView(int position, TabLayout tabLayout) {
        return null;
    }

    @Override
    public void onTabReselected(Tab tab) {
    }

    @Override
    public void onTabSelected(Tab tab) {

    }

    @Override
    public void onTabUnselected(Tab tab) {

    }
}
