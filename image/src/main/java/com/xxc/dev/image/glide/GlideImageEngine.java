package com.xxc.dev.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.common.callback.CallBack1;
import com.xxc.dev.image.IEngine;
import com.xxc.dev.image.ImageConfig;
import com.xxc.dev.image.glide.cache.GlideDiskCacheFactory;
import com.xxc.dev.image.glide.progress.ProgressInterceptor;
import com.xxc.dev.image.listener.ImageLoadListener;
import com.xxc.dev.image.utils.ImageGenericUtils;
import java.lang.reflect.Type;

/**
 * 通过Glide去加载图片
 */
public class GlideImageEngine implements IEngine {

    public static final String TAG = "GlideImageEngine";

    private static final int NO_LISTENER = -1;
    private static final int BITMAP_TYPE = 0;
    private static final int DRAWABLE_TYPE = 1;
    private static final int GIF_TYPE = 2;

    @Override
    public void load(Context context, String url, ImageView imageView) {
        load(context, url, imageView, null, null, null);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, ImageConfig config) {
        load(context, url, imageView, config, null, null);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, CallBack1<Integer> progressListener) {
        load(context, url, imageView, null, null, progressListener);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, ImageConfig config, ImageLoadListener listener) {
        load(context, url, imageView, config, listener, null);
    }

    @Override
    public void load(Context context, final String url, ImageView imageView, ImageConfig config, final ImageLoadListener listener, CallBack1<Integer> progressListener) {
        if (null == context) {
            context = AppUtils.application();
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (progressListener != null) {
            if (null != GlideDiskCacheFactory.getCacheFile(url)) {
                progressListener.onCallBack(100);
            } else {
                ProgressInterceptor.addListener(url, progressListener::onCallBack);
            }
        }
        GlideRequests requests = GlideApp.with(context);
        ImageConfig safeConfig = safeNullConfig(config);

        int imageType = -1;
        if (null != listener) {
            Type type = ImageGenericUtils.getImageResultType(listener);
            String typeName = "";
            if (null != type) {
                typeName = type.toString();
            }
            if (typeName.endsWith(Bitmap.class.getName())) {
                imageType = BITMAP_TYPE;
            } else if (typeName.endsWith(Drawable.class.getName())) {
                imageType = DRAWABLE_TYPE;
            } else if (typeName.endsWith(GifDrawable.class.getName())) {
                imageType = GIF_TYPE;
            }
        }
        RequestOptions options = (RequestOptions) safeConfig.getOption();
        switch (imageType) {
            case BITMAP_TYPE:
                GlideRequest<Bitmap> bitmapRequest = requests.asBitmap().apply(options).load(url);
                bitmapRequest.into(new ProxyTarget<Bitmap>(url, listener));
                break;
            case DRAWABLE_TYPE:
                GlideRequest<Drawable> drawableRequest = requests.asDrawable().apply(options).load(url);
                drawableRequest.into(new ProxyTarget<Drawable>(url, listener));
                break;
            case GIF_TYPE:
                GlideRequest<GifDrawable> gifRequest = requests.asGif().apply(options).load(url);
                gifRequest.into(new ProxyTarget<GifDrawable>(url, listener));
                break;
            case NO_LISTENER:
                if (null != imageView) {
                    requests.load(url).apply(options).into(imageView);
                } else {
                    Log.e(TAG, "No ImageLoadListener,no ImageView target");
                }
                break;
        }
    }

    @Override
    public void load(Context context, String url, ImageConfig config, ImageLoadListener listener, CallBack1<Integer> progressListener) {
        load(context, url, null, config, listener, progressListener);
    }

    @Override
    public void load(Context context, String url, ImageConfig config, ImageLoadListener listener) {
        load(context, url, null, config, listener, null);
    }

    @Override
    public void load(Context context, String url, ImageLoadListener listener, CallBack1<Integer> progressListener) {
        load(context, url, null, null, listener, progressListener);
    }

    @Override
    public void load(Context context, String url, ImageLoadListener listener) {
        load(context, url, null, null, listener, null);
    }

    private ImageConfig safeNullConfig(ImageConfig config) {
        ImageConfig safeConfig = new ImageConfig();
        RequestOptions option = GlideImageOption.defaultOption();
        if (null != config) {
            Object opt = config.getOption();
            option = opt instanceof RequestOptions ? (RequestOptions) opt : option;
            safeConfig.setLoadingPlaceholder(config.getLoadingPlaceholder());
            safeConfig.setErrorPlaceholder(config.getErrorPlaceholder());
            safeConfig.setEnableDiskCache(config.isEnableDiskCache());
            safeConfig.setEnableMemoryCache(config.isEnableMemoryCache());
        }
        option = option.diskCacheStrategy(safeConfig.isEnableDiskCache() ? DiskCacheStrategy.RESOURCE : DiskCacheStrategy.NONE)
                       .skipMemoryCache(!safeConfig.isEnableMemoryCache());
        if (safeConfig.getLoadingPlaceholder() != -1) {
            option = option.placeholder(safeConfig.getLoadingPlaceholder());
        }
        if (safeConfig.getErrorPlaceholder() != -1) {
            option = option.error(safeConfig.getErrorPlaceholder());
        }
        return safeConfig.setOption(option);
    }
}
