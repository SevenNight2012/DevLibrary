package com.xxc.dev.network.okgo;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.xxc.dev.network.IRequestInterceptor;
import com.xxc.dev.network.request.AbsRequest;
import com.xxc.dev.network.request.IProgressListener;
import com.xxc.dev.network.request.RequestOption;
import com.xxc.dev.network.response.INetworkResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认的网络请求实现方式，对OkGo进一步封装，方便项目内部切换网络框架
 */
public class OkGoRequest extends AbsRequest {

    private IProgressListener mProgressListener;

    private List<IRequestInterceptor> mInterceptors = new ArrayList<>();

    public OkGoRequest(RequestOption option) {
        super(option);
    }

    @Override
    public void addInterceptor(IRequestInterceptor interceptor) {
        if (!mInterceptors.contains(interceptor)) {
            mInterceptors.add(interceptor);
        }
    }

    @Override
    public void setProgressListener(IProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    @Override
    public void request(INetworkResult result) {
        switch (mOption.getRequestType()) {
            case RequestOption.REQUEST_GET:
                requestGet(result);
                break;
            case RequestOption.REQUEST_POST:
                requestPost(result);
                break;
            case RequestOption.REQUEST_PUT:
                requestPut(result);
                break;
            case RequestOption.REQUEST_DELETE:
                requestDelete(result);
                break;
        }
    }

    @Override
    public void download(INetworkResult result) {

    }

    @Override
    public void upload(INetworkResult result) {

    }

    private void requestGet(INetworkResult result) {
        OkGo.get(mOption.getUrl())     // 请求方式和请求url
            .tag(mOption.getTag())                       // 请求的 tag, 主要用于取消对应的请求
            .headers(convertHeader())
            .params(convertParam())
            .readTimeOut(mOption.getReadTime())
            .connTimeOut(mOption.getRequestTime())
            .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)    // 缓存模式，详细请看缓存介绍
            .execute(new OkGoStringCallBack(result, mOption));
    }

    private void requestPost(INetworkResult result) {

    }

    private void requestPut(INetworkResult result) {

    }

    private void requestDelete(INetworkResult result) {

    }

    private HttpHeaders convertHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, String> headers = mOption.getHeaders();
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                httpHeaders.put(key, headers.get(key));
            }
        }
        return httpHeaders;
    }

    private HttpParams convertParam() {
        HttpParams httpParams = new HttpParams();
        Map<String, String> params = mOption.getParams();
        if (params != null) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                httpParams.put(key, params.get(key));
            }
        }
        return httpParams;
    }
}
