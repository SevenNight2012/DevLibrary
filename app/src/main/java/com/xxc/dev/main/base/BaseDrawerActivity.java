package com.xxc.dev.main.base;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.xxc.dev.common.title.BaseTitleHelper;
import com.xxc.dev.common.title.SimpleTitleHelper;
import com.xxc.dev.main.R;

/**
 * 带侧滑栏的Activity基类
 */
public abstract class BaseDrawerActivity extends AbsActivity {

    protected View mRootView;
    protected FrameLayout mTitleContainer;
    protected DrawerLayout mDrawerLayout;
    protected FrameLayout mDrawerContentContainer;
    protected FrameLayout mDrawerLeftContainer;
    protected FrameLayout mDrawerRightContainer;

    protected LayoutInflater mInflater;

    protected BaseTitleHelper mTitleHelper;

    @Override
    protected int getContentId() {
        return R.layout.activity_base_drawer;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRootView = findViewById(R.id.root_view);
        mTitleContainer = findViewById(R.id.drawer_title);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerContentContainer = findViewById(R.id.drawer_content);
        mDrawerLeftContainer = findViewById(R.id.drawer_left);
        mDrawerRightContainer = findViewById(R.id.drawer_right);

        mInflater = getLayoutInflater();
        mTitleHelper = createTitleHelper();
        if (null != mTitleHelper) {
            mInflater.inflate(mTitleHelper.getTitleLayout(), mTitleContainer, false);
            mTitleHelper.init(this, mTitleContainer);
        }

        initWidgets(savedInstanceState);

        if (0 != leftDrawerLayout()) {
            mInflater.inflate(leftDrawerLayout(), mDrawerLeftContainer);
            initLeftDrawer(mDrawerLeftContainer);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        }
        if (0 != rightDrawerLayout()) {
            mInflater.inflate(rightDrawerLayout(), mDrawerRightContainer);
            initRightDrawer(mDrawerRightContainer);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        }
    }

    public void initLeftDrawer(FrameLayout container) {

    }

    private void initRightDrawer(FrameLayout container) {

    }

    public BaseTitleHelper createTitleHelper() {
        return new SimpleTitleHelper(this);
    }

    protected int leftDrawerLayout() {
        return 0;
    }

    protected int rightDrawerLayout() {
        return 0;
    }

    public abstract void initWidgets(Bundle savedInstanceState);
}
