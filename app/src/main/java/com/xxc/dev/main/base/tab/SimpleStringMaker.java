package com.xxc.dev.main.base.tab;

import com.google.android.material.tabs.TabLayout.Tab;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.main.R;
import java.util.List;

/**
 * 单纯的字符串Tab
 */
public class SimpleStringMaker extends BaseTabViewMaker {

    private List<String> mTitles;

    public SimpleStringMaker(List<String> titles) {
        mTitles = titles;
    }

    @Override
    public View onCreateTabView(int position, LayoutInflater inflater, ViewGroup parent) {
        TextView textView = (TextView) inflater.inflate(R.layout.simple_tab_view, parent, false);
        textView.setText(mTitles.get(position));
        return textView;
    }

    @Override
    public void onTabSelected(Tab tab) {
        TextView text = (TextView) tab.getCustomView();
        if (text != null) {
            text.setTextColor(AppUtils.getColor(R.color.color_00ffff));
        }
    }

    @Override
    public void onTabUnselected(Tab tab) {
        TextView text = (TextView) tab.getCustomView();
        if (text != null) {
            text.setTextColor(AppUtils.getColor(R.color.color_333333));
        }
    }
}
