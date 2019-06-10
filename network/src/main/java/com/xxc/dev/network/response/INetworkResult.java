package com.xxc.dev.network.response;

/**
 * 网络回调
 */
public interface INetworkResult<Result> {

    void onSuccess(Object tag, Result result);

    void onFailure(NetworkError error);

}
