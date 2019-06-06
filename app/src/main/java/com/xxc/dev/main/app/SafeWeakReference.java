package com.xxc.dev.main.app;

import com.xxc.dev.common.callback.CallBack1;
import java.lang.ref.WeakReference;

/**
 * 通过回调的方式获取弱引用的对象，过滤判空操作
 */
public class SafeWeakReference<O> {

    private WeakReference<O> mWeakReference;

    private SafeWeakReference(O obj) {
        mWeakReference = new WeakReference<>(obj);
    }

    public void postSafe(CallBack1<O> callBack1) {
        if (callBack1 != null && null != mWeakReference && mWeakReference.get() != null) {
            callBack1.onCallBack(mWeakReference.get());
        }
    }

    public O get() {
        if (null != mWeakReference) {
            return mWeakReference.get();
        }
        return null;
    }
}
