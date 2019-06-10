package com.xxc.dev.network.request;

/**
 * 上传监听
 */
public interface IProgressListener {

    void onProgress(Object tag, long total, long current);

}
