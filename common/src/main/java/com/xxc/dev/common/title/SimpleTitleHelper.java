package com.xxc.dev.common.title;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

/**
 * 简单的title控制器
 */
public class SimpleTitleHelper extends BaseTitleHelper<Activity> {

    public SimpleTitleHelper(Activity controller) {
        super(controller);
    }

    @Override
    public Activity getActivity() {
        return mController;
    }

    @Override
    public int getTitleLayout() {
        return 0;
    }

    @Override
    public void init(Context context, ViewGroup parent) {

    }
}
