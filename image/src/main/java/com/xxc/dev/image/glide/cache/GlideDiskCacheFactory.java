package com.xxc.dev.image.glide.cache;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.common.callback.CallBack1;
import java.io.File;

/**
 * 自定义的磁盘缓存
 */
public class GlideDiskCacheFactory extends DiskLruCacheFactory {

    /**
     * 获取本地缓存的方法
     *
     * @param url 图片URL
     * @return
     */
    public static void getCacheFile(String url, CallBack1<File> caller) {
        Glide.with(AppUtils.application())
             .downloadOnly()
             .load(url)
             .onlyRetrieveFromCache(true)
             .into(new FileTarget(caller));
    }

    public static File getDefaultCacheDir(Context context) {
        return getCacheDir(context, Factory.DEFAULT_DISK_CACHE_DIR);
    }

    public static File getCacheDir(Context context, String diskCacheName) {
        //此处以外部存储路劲优先，Glide源码中以内部存储优先，此处调整
        File cacheDirectory = context.getExternalCacheDir();
        // Shared storage is not available.
        if ((cacheDirectory == null || !cacheDirectory.canWrite()) && !TextUtils.isEmpty(diskCacheName)) {
            return new File(context.getCacheDir(), diskCacheName);
        }
        if (diskCacheName != null) {
            return new File(cacheDirectory, diskCacheName);
        }
        return cacheDirectory;
    }

    public GlideDiskCacheFactory(Context context) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public GlideDiskCacheFactory(Context context, long diskCacheSize) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public GlideDiskCacheFactory(final Context context, final String diskCacheName, final long diskCacheSize) {
        super(new CacheDirectoryGetter() {
            @Nullable
            private File getInternalCacheDirectory() {
                File cacheDirectory = context.getCacheDir();
                if (cacheDirectory == null) {
                    return null;
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }

            @Override
            public File getCacheDirectory() {
                //此处以外部存储路劲优先，Glide源码中以内部存储优先，此处调整
                File cacheDirectory = context.getExternalCacheDir();

                // Shared storage is not available.
                if ((cacheDirectory == null) || (!cacheDirectory.canWrite())) {
                    return getInternalCacheDirectory();
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }
}
