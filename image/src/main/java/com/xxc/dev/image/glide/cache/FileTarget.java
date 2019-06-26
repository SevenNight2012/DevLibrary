package com.xxc.dev.image.glide.cache;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xxc.dev.common.callback.CallBack1;
import java.io.File;

public class FileTarget extends CustomTarget<File> {

    private CallBack1<File> mCaller;

    public FileTarget(CallBack1<File> fileCallBack) {
        mCaller = fileCallBack;
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        if (mCaller != null) {
            mCaller.onCallBack(null);
        }
    }

    @Override
    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
        if (mCaller != null) {
            mCaller.onCallBack(resource);
        }
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
        if (mCaller != null) {
            mCaller.onCallBack(null);
        }
    }
}
