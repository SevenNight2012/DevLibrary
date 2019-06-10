package com.xxc.dev.network;

import com.xxc.dev.network.request.INetworkRequest;
import com.xxc.dev.network.request.RequestOption;

/**
 * 请求拦截器
 */
public interface IRequestInterceptor {

    void onIntercept(RequestOption option, INetworkRequest request);

}
