package com.xxc.dev.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import com.xxc.dev.main.base.BaseFragmentV4;
import java.util.ArrayList;
import java.util.List;

/**
 * 关联了fragment的adapter
 */
public class BaseTabPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles = new ArrayList<>();
    private List<BaseFragmentV4> mFragments = new ArrayList<>();

    public BaseTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(String title, BaseFragmentV4 fragment) {
        if (!TextUtils.isEmpty(title) && null != fragment) {
            mTitles.add(title);
            mFragments.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
