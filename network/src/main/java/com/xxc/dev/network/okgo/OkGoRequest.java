package com.xxc.dev.network.okgo;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;
import com.xxc.dev.common.callback.CallBack2;
import com.xxc.dev.network.IRequestInterceptor;
import com.xxc.dev.network.NetworkManager;
import com.xxc.dev.network.request.AbsRequest;
import com.xxc.dev.network.request.IProgressListener;
import com.xxc.dev.network.request.RequestOption;
import com.xxc.dev.network.response.INetworkResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认的网络请求实现方式，对OkGo进一步封装，方便项目内部切换网络框架
 */
public class OkGoRequest extends AbsRequest {

    public static final String CACHE_MODE = "cacheMode";

    public static final String NO_CACHE = "noCache";
    public static final String CACHE_FAILED = "cacheFailed";
    public static final String CACHE_DEFAULT = "cacheDefault";

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
    public void download(INetworkResult<File> result) {
        invokeInterceptors();
        OkGo.get(mOption.getUrl())
            .tag(mOption.getTag())
            .headers(convertHeader())
            .params(convertParam())
            .execute(new OkGoFileCallBack(result, mOption, mProgressListener));
    }

    @Override
    public void upload(INetworkResult result) {
        invokeInterceptors();
        PostRequest postRequest = OkGo.post(mOption.getUrl())//
                                      .tag(mOption.getTag())//
                                      .headers(convertHeader())//
                                      .params(convertParam())//
                                      .connTimeOut(mOption.getRequestTime())
                                      .readTimeOut(mOption.getReadTime())
                                      .params(convertFileParam());// 可以添加文件上传
        convertFileListParam(postRequest::addFileParams);
        postRequest.execute(new OkGoUploadStringCallBack(result, mProgressListener, mOption));
    }

    private void requestGet(INetworkResult result) {
        invokeInterceptors();
        OkGo.get(mOption.getUrl())     // 请求方式和请求url
            .tag(mOption.getTag())                       // 请求的 tag, 主要用于取消对应的请求
            .headers(convertHeader())
            .params(convertParam())
            .readTimeOut(mOption.getReadTime())
            .connTimeOut(mOption.getRequestTime())
            .cacheMode(getCacheMode())    // 缓存模式，详细请看缓存介绍
            .execute(new OkGoStringCallBack(result, mOption));
    }

    private void requestPost(INetworkResult result) {
        invokeInterceptors();
        OkGo.post(mOption.getUrl())
            .tag(mOption.getTag())
            .headers(convertHeader())
            .params(convertParam())
            .readTimeOut(mOption.getReadTime())
            .connTimeOut(mOption.getRequestTime())
            .cacheMode(getCacheMode())
            .execute(new OkGoStringCallBack(result, mOption));
    }

    private void requestPut(INetworkResult result) {
        invokeInterceptors();
        OkGo.put(mOption.getUrl())
            .tag(mOption.getTag())
            .headers(convertHeader())
            .params(convertParam())
            .readTimeOut(mOption.getReadTime())
            .connTimeOut(mOption.getRequestTime())
            .cacheMode(getCacheMode())
            .execute(new OkGoStringCallBack(result, mOption));
    }

    private void requestDelete(INetworkResult result) {
        invokeInterceptors();
        OkGo.delete(mOption.getUrl())
            .tag(mOption.getTag())
            .headers(convertHeader())
            .params(convertParam())
            .readTimeOut(mOption.getReadTime())
            .connTimeOut(mOption.getRequestTime())
            .cacheMode(getCacheMode())
            .execute(new OkGoStringCallBack(result, mOption));
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

    private HttpParams convertFileParam() {
        HttpParams params = new HttpParams();
        Map<String, File> files = mOption.getFiles();
        if (files != null) {
            Set<String> keySet = files.keySet();
            for (String key : keySet) {
                params.put(key, files.get(key));
            }
        }
        return params;
    }

    private void convertFileListParam(CallBack2<String, List<File>> callBack2) {
        Map<String, List<File>> fileList = mOption.getFileList();
        if (fileList != null) {
            Set<String> keySet = fileList.keySet();
            for (String key : keySet) {
                List<File> files = fileList.get(key);
                if (files != null) {
                    callBack2.onCallBack(key, files);
                }
            }
        }
    }

    private void invokeInterceptors() {
        List<IRequestInterceptor> globalInterceptors = NetworkManager.getInstance().getGlobalInterceptors();
        if (globalInterceptors != null) {
            for (IRequestInterceptor interceptor : globalInterceptors) {
                interceptor.onIntercept(mOption, this);
            }
        }
        if (mInterceptors != null) {
            for (IRequestInterceptor interceptor : mInterceptors) {
                interceptor.onIntercept(mOption, this);
            }
        }
    }

    private CacheMode getCacheMode() {
        Map<String, String> extra = mOption.getExtra();
        if (extra != null) {
            String mode = extra.get(CACHE_MODE);
            if (CACHE_DEFAULT.equalsIgnoreCase(mode)) {
                return CacheMode.DEFAULT;
            } else if (NO_CACHE.equalsIgnoreCase(mode)) {
                return CacheMode.NO_CACHE;
            } else if (CACHE_FAILED.equalsIgnoreCase(mode)) {
                return CacheMode.REQUEST_FAILED_READ_CACHE;
            }
        }
        return CacheMode.REQUEST_FAILED_READ_CACHE;
    }

    public static void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    public static void cancelAll() {
        OkGo.getInstance().cancelAll();
    }
}
