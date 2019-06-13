package com.xxc.dev.image;

import android.content.Context;
import android.widget.ImageView;
import com.xxc.dev.common.callback.CallBack1;
import com.xxc.dev.image.glide.GlideImageEngine;
import com.xxc.dev.image.listener.ImageLoadListener;

/**
 * 图片加载
 */
public class ImageEngine implements IEngine {

    private static final ImageEngine INSTANCE = new ImageEngine();

    public static ImageEngine getInstance() {
        return INSTANCE;
    }

    private ImageEngine() {
    }

    private IEngine mEngine = new GlideImageEngine();

    public void setEngine(IEngine engine) {
        mEngine = engine;
    }

    public void load(Context context, String url, ImageView imageView) {
        if (mEngine != null) {
            mEngine.load(context, url, imageView);
        }
    }

    public void load(Context context, String url, ImageView imageView, ImageConfig config) {
        if (mEngine != null) {
            mEngine.load(context, url, imageView, config);
        }
    }

    @Override
    public void load(Context context, String url, ImageView imageView, CallBack1<Integer> progressListener) {
        if (mEngine != null) {
            mEngine.load(context, url, imageView, progressListener);
        }
    }

    public void load(Context context, String url, ImageView imageView, ImageConfig config, ImageLoadListener listener) {
        if (mEngine != null) {
            mEngine.load(context, url, imageView, config, listener);
        }
    }

    public void load(Context context, String url, ImageConfig config, ImageLoadListener listener) {
        if (mEngine != null) {
            mEngine.load(context, url, config, listener);
        }
    }

    @Override
    public void load(Context context, String url, ImageLoadListener listener, CallBack1<Integer> progressListener) {
        if (mEngine != null) {
            mEngine.load(context, url, listener, progressListener);
        }
    }

    public void load(Context context, String url, ImageLoadListener listener) {
        if (mEngine != null) {
            mEngine.load(context, url, listener);
        }
    }

    public void load(Context context, String url, ImageConfig config, ImageLoadListener listener, CallBack1<Integer> progressListener) {
        if (mEngine != null) {
            mEngine.load(context, url, config, listener, progressListener);
        }
    }

    public void load(Context context, String url, ImageView imageView, ImageConfig config, ImageLoadListener listener, CallBack1<Integer> progressListener) {
        if (mEngine != null) {
            mEngine.load(context, url, imageView, config, listener, progressListener);
        }
    }
}
