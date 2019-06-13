package com.xxc.dev.image;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片加载配置项
 */
public class ImageConfig<Option> {

    private boolean mEnableMemoryCache = true;
    private boolean mEnableDiskCache = true;
    private int mLoadingPlaceholder = -1;
    private int mErrorPlaceholder = -1;
    private Option mOption;
    private Map<String, Object> mExtra = new HashMap<>();

    public boolean isEnableMemoryCache() {
        return mEnableMemoryCache;
    }

    public ImageConfig<Option> setEnableMemoryCache(boolean enableMemoryCache) {
        mEnableMemoryCache = enableMemoryCache;
        return this;
    }

    public boolean isEnableDiskCache() {
        return mEnableDiskCache;
    }

    public ImageConfig<Option> setEnableDiskCache(boolean enableDiskCache) {
        mEnableDiskCache = enableDiskCache;
        return this;
    }

    public int getLoadingPlaceholder() {
        return mLoadingPlaceholder;
    }

    public ImageConfig<Option> setLoadingPlaceholder(int loadingPlaceholder) {
        mLoadingPlaceholder = loadingPlaceholder;
        return this;
    }

    public int getErrorPlaceholder() {
        return mErrorPlaceholder;
    }

    public ImageConfig<Option> setErrorPlaceholder(int errorPlaceholder) {
        mErrorPlaceholder = errorPlaceholder;
        return this;
    }

    public Option getOption() {
        return mOption;
    }

    public ImageConfig<Option> setOption(Option option) {
        mOption = option;
        return this;
    }

    public Map<String, Object> getExtra() {
        return mExtra;
    }

    public ImageConfig<Option> setExtra(Map<String, Object> extra) {
        mExtra = extra;
        return this;
    }
}
