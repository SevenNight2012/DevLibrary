package com.xxc.dev.common.title;

import android.app.Activity;
import com.xxc.dev.common.AppUtils;

/**
 * title控制器基类
 *
 * @param <C>
 */
public abstract class BaseTitleHelper<C> implements ITitleHelper {

    protected C mController;

    public BaseTitleHelper(C controller) {
        mController = controller;
    }

    public String getString(int id) {
        return AppUtils.getString(id);
    }

    public abstract Activity getActivity();
}
