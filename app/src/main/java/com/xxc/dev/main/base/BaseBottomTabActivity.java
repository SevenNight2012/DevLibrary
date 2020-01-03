package com.xxc.dev.main.base;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import com.xxc.dev.common.callback.CallBack2;
import com.xxc.dev.main.R;
import com.xxc.dev.main.adapter.BaseTabPagerAdapter;
import com.xxc.dev.main.base.tab.BaseTabViewMaker;

/**
 * 底部带有tab的activity
 */
public abstract class BaseBottomTabActivity extends AbsActivity {

    protected ViewPager mBasePager;
    protected View mDivider;
    protected TabLayout mBaseTab;
    protected BaseTabPagerAdapter mTabPagerAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_bottom_tab;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mBasePager = findViewById(R.id.base_pager);
        mDivider = findViewById(R.id.base_divider);
        mBaseTab = findViewById(R.id.base_tab);
        initWidgets(savedInstanceState);
        setupTabLocation();
        initPager();
        initTab();
    }

    protected void initWidgets(Bundle savedInstanceState) {

    }

    /**
     * 设置tab在整个界面中的位置，此界面为在底部，可以将tab放置到顶部
     */
    protected void setupTabLocation() {

    }

    protected void initPager() {
        mTabPagerAdapter = new BaseTabPagerAdapter(getSupportFragmentManager());
        int count = tabCount();
        for (int i = 0; i < count; i++) {
            instanceFragment(i, (title, fragment) -> {
                if (!TextUtils.isEmpty(title) && null != fragment) {
                    mTabPagerAdapter.addFragment(title, fragment);
                }
            });
        }
        mBasePager.setAdapter(mTabPagerAdapter);
        mBaseTab.setupWithViewPager(mBasePager);
    }

    public int tabCount() {
        return 0;
    }

    public abstract void instanceFragment(int position, CallBack2<String, BaseFragmentV4> caller);

    protected void initTab() {
        BaseTabViewMaker maker = createTabViewMaker();
        if (null != maker) {
            for (int i = 0; i < tabCount(); i++) {
                Tab tab = mBaseTab.getTabAt(i);
                if (tab != null) {
                    tab.setCustomView(maker.onCreateTabView(i, getLayoutInflater(), mBaseTab));
                }
            }
            mBaseTab.addOnTabSelectedListener(maker);
            maker.onTabSelected(mBaseTab.getTabAt(mBaseTab.getSelectedTabPosition()));
        }
    }

    public BaseTabViewMaker createTabViewMaker() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mBaseTab) {
            mBaseTab.clearOnTabSelectedListeners();
        }
    }
}
