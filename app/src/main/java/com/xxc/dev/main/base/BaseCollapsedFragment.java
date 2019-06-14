package com.xxc.dev.main.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.main.R;

/**
 * 具有折叠title功能的Fragment
 */
public abstract class BaseCollapsedFragment extends BaseFragmentV4 {

    protected AppBarLayout mFragmentAppbar;
    protected CollapsingToolbarLayout mFragmentCollapsingToolbar;
    protected FrameLayout mExpandTitleContainer;
    protected FrameLayout mCoreContentContainer;
    protected FrameLayout mCollapsedTitleContainer;

    protected OnOffsetChangedListener mOffsetChangedListener;

    @Override
    public int getContentId() {
        return R.layout.fragment_collapsed;
    }

    @Override
    public void initWidgets(Bundle savedInstanceState) {
        mFragmentAppbar = findViewById(R.id.fragment_appbar);
        mFragmentCollapsingToolbar = findViewById(R.id.fragment_collapsing_toolbar);
        mExpandTitleContainer = findViewById(R.id.fragment_expand_content);
        mCoreContentContainer = findViewById(R.id.fragment_content);
        mCollapsedTitleContainer = findViewById(R.id.fragment_title);

        Fragment expandTitle = createExpandTitle();
        Fragment collapsedTitle = createCollapsedTitle();
        Fragment coreContent = createCoreContent();

        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
               .add(R.id.fragment_expand_content, expandTitle)
               .add(R.id.fragment_content, coreContent)
               .add(R.id.fragment_title, collapsedTitle)
               .commitAllowingStateLoss();

        if (mOffsetChangedListener != null) {
            mFragmentAppbar.removeOnOffsetChangedListener(mOffsetChangedListener);
        }
        mOffsetChangedListener = (appBarLayout, verticalOffset) -> {
            int absOffset = Math.abs(verticalOffset);
            int height = mExpandTitleContainer.getHeight();
            float offsetAlpha = absOffset * 1.0f / height;
            float alpha = getAlphaValue(offsetAlpha);
            mCollapsedTitleContainer.setAlpha(alpha);
            mCollapsedTitleContainer.setVisibility(0 == alpha ? View.GONE : View.VISIBLE);
        };
        mFragmentAppbar.addOnOffsetChangedListener(mOffsetChangedListener);
    }

    /**
     * 透明度在[0.3，0.7]之间取值，如果小于0.3，直接透明，如果大于0.7，直接显示
     *
     * @param alpha 真正的透明度值
     * @return 经过过滤的透明度值
     */
    protected float getAlphaValue(float alpha) {
        return 0.3f >= Math.max(0.3f, alpha) ? 0f : (0.7f < Math.min(0.7f, alpha) ? 1.0f : alpha);
    }

    @NonNull
    public abstract Fragment createExpandTitle();

    @NonNull
    public abstract Fragment createCollapsedTitle();

    @NonNull
    public abstract Fragment createCoreContent();

    public Context appContext() {
        return AppUtils.application();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOffsetChangedListener != null) {
            mFragmentAppbar.removeOnOffsetChangedListener(mOffsetChangedListener);
        }
    }
}
