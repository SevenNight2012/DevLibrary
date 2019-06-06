package com.xxc.dev.main.base.tab;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
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
