package com.xxc.dev.network.response;

/**
 * 网络异常封装
 */
public class NetworkError {

    private String mMessage;
    private int mErrorCode;
    private Throwable mThrowable;

    public String getMessage() {
        return mMessage;
    }

    public NetworkError setMessage(String message) {
        mMessage = message;
        return this;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public NetworkError setErrorCode(int errorCode) {
        mErrorCode = errorCode;
        return this;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public NetworkError setThrowable(Throwable throwable) {
        mThrowable = throwable;
        return this;
    }

    public NetworkError(String message, int errorCode) {
        mMessage = message;
        mErrorCode = errorCode;
    }

    public NetworkError(String message, int errorCode, Throwable throwable) {
        mMessage = message;
        mErrorCode = errorCode;
        mThrowable = throwable;
    }

    @Override
    public String toString() {
        return "NetworkError{" + "mMessage='" + mMessage + '\'' + ", mErrorCode=" + mErrorCode + ", mThrowable=" + mThrowable + '}';
    }
}
