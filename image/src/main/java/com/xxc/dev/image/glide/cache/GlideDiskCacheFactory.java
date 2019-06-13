package com.xxc.dev.image.glide.cache;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.image.glide.SimpleDiskCacheKey;
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
    public static File getCacheFile(String url) {
        DiskCache diskCache = new GlideDiskCacheFactory(AppUtils.application()).build();
        if (diskCache != null) {
            return diskCache.get(new SimpleDiskCacheKey(url));
        }
        return null;
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
