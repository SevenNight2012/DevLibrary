package com.xxc.dev.main.base;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.xxc.dev.main.R;

/**
 * 底部带有tab的activity
 */
public abstract class BaseBottomTabActivity extends AbsActivity {


    @Override
    protected int getContentId() {
        return R.layout.activity_bottom_tab;
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {

    }
}
