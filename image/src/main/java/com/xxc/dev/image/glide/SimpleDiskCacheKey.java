package com.xxc.dev.image.glide;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.signature.EmptySignature;
import java.security.MessageDigest;

/**
 * 根据内部的DataCacheKey自定义的key
 */
public class SimpleDiskCacheKey implements Key {

    private final Key mSourceKey;
    private final Key mSignature;

    public SimpleDiskCacheKey(String url) {
        this.mSourceKey = new GlideUrl(url);
        this.mSignature = EmptySignature.obtain();
    }

    Key getSourceKey() {
        return mSourceKey;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SimpleDiskCacheKey) {
            SimpleDiskCacheKey other = (SimpleDiskCacheKey) o;
            return mSourceKey.equals(other.mSourceKey) && mSignature.equals(other.mSignature);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = mSourceKey.hashCode();
        result = 31 * result + mSignature.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DataCacheKey{" + "sourceKey=" + mSourceKey + ", signature=" + mSignature + '}';
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        mSourceKey.updateDiskCacheKey(messageDigest);
        mSignature.updateDiskCacheKey(messageDigest);
    }
}
