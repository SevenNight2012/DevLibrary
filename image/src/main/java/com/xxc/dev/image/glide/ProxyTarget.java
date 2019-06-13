package com.xxc.dev.image.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xxc.dev.image.ImageException;
import com.xxc.dev.image.listener.ImageLoadListener;

/**
 * 代理对象
 *
 * @param <T>
 */
public class ProxyTarget<T> extends CustomTarget<T> {

    private ImageLoadListener<T> mLoadListener;
    private String mUrl;

    public ProxyTarget(String url, ImageLoadListener<T> loadListener) {
        mUrl = url;
        mLoadListener = loadListener;
    }

    @Override
    public void onResourceReady(@NonNull T resource, @Nullable Transition<? super T> transition) {
        if (mLoadListener != null) {
            mLoadListener.onSuccess(mUrl, resource);
        }
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        if (mLoadListener != null) {
            mLoadListener.onFailed(new ImageException("Load failed,the image url is  -->>> " + mUrl, -1));
        }
    }
}
