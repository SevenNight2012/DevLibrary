package com.xxc.dev.image.listener;

import android.graphics.drawable.Drawable;
import com.xxc.dev.image.ImageException;

/**
 * 回调对象为Drawable
 */
public abstract class DrawableImageListener implements ImageLoadListener<Drawable> {

    @Override
    public void onFailed(ImageException e) {

    }
}
