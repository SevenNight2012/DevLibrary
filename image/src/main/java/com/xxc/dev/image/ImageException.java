package com.xxc.dev.image;

/**
 * 图片加载异常
 */
public class ImageException extends RuntimeException {

    private String mDes;
    private int mCode;
    private Throwable mThrowable;

    public ImageException(String des, int code, Throwable throwable) {
        super(des);
        mDes = des;
        mCode = code;
        mThrowable = throwable;
    }

    public ImageException(String des, int code) {
        super(des);
        mDes = des;
        mCode = code;
    }

    public ImageException(Throwable throwable) {
        super(throwable);
        mThrowable = throwable;
    }
}
