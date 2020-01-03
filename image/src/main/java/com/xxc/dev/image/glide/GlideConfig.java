package com.xxc.dev.image.glide;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.xxc.dev.image.glide.cache.GlideDiskCacheFactory;
import com.xxc.dev.image.glide.progress.ProgressInterceptor;
import java.io.InputStream;
import okhttp3.OkHttpClient;

@GlideModule
public class GlideConfig extends AppGlideModule {

    public static final String TAG = "GlideConfig";

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        // 写入咱们的okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 写入咱们的okhttp的拦截器,在拦截器中监听进度
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();
        // glide吧urlConnection替换为okhttp
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull final GlideBuilder builder) {
        builder.setLogLevel(Log.DEBUG);
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA);
        builder.setDiskCache(new GlideDiskCacheFactory(context))
               .setDefaultRequestOptions(options);
    }
}
