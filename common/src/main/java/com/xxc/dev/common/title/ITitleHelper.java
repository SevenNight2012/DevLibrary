package com.xxc.dev.common.title;

import android.content.Context;
import android.view.ViewGroup;

/**
 * 封装了title的初始化
 */
public interface ITitleHelper {

    int getTitleLayout();

    void init(Context context, ViewGroup parent);

}
