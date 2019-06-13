package com.xxc.dev.image.listener;

import android.graphics.Bitmap;
import com.xxc.dev.image.ImageException;

/**
 * 回调对象为bitmap
 */
public abstract class BitmapImageListener implements ImageLoadListener<Bitmap> {

    @Override
    public void onFailed(ImageException e) {

    }
}
