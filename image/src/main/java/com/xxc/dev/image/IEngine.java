package com.xxc.dev.image;

import android.content.Context;
import android.widget.ImageView;
import com.xxc.dev.common.callback.CallBack1;
import com.xxc.dev.image.listener.ImageLoadListener;

/**
 * 按需加载的接口
 */
public interface IEngine {

    void load(Context context, String url, ImageView imageView);

    void load(Context context, String url, ImageView imageView, ImageConfig config);

    void load(Context context, String url, ImageView imageView, CallBack1<Integer> progressListener);

    void load(Context context, String url, ImageView imageView, ImageConfig config, ImageLoadListener listener);

    void load(Context context, String url, ImageView imageView, ImageLoadListener listener, CallBack1<Integer> progressListener);

    void load(Context context, String url, ImageView imageView, ImageConfig config, ImageLoadListener listener, CallBack1<Integer> progressListener);

    void load(Context context, String url, ImageConfig config, ImageLoadListener listener, CallBack1<Integer> progressListener);

    void load(Context context, String url, ImageConfig config, ImageLoadListener listener);

    void load(Context context, String url, ImageLoadListener listener, CallBack1<Integer> progressListener);

    void load(Context context, String url, ImageLoadListener listener);

}
