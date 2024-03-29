package com.xxc.dev.network.request;

import com.xxc.dev.network.IRequestInterceptor;
import com.xxc.dev.network.response.INetworkResult;
import java.io.File;

/**
 * 网络请求
 */
public interface INetworkRequest {

    void addInterceptor(IRequestInterceptor interceptor);

    void setProgressListener(IProgressListener progressListener);

    void request(INetworkResult result);

    void download(INetworkResult<File> result);

    void upload(INetworkResult result);
}
