package com.xxc.dev.network;

import com.lzy.okgo.OkGo;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.network.request.AbsRequest;
import com.xxc.dev.network.request.RequestOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络管理
 */
public class NetworkManager {

    public static final String TAG = "NetworkManager";

    private static final NetworkManager INSTANCE = new NetworkManager();

    private List<IRequestInterceptor> mInterceptors = new ArrayList<>();

    private Class<? extends AbsRequest> mRequestClass;

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    private NetworkManager() {
        OkGo.init(AppUtils.application());
    }

    public NetworkManager setRequestClass(Class<? extends AbsRequest> requestClass) {
        mRequestClass = requestClass;
        return this;
    }

    public Class<? extends AbsRequest> getRequestClass() {
        return mRequestClass;
    }

    public NetworkManager addGlobalInterceptor(IRequestInterceptor interceptor) {
        if (interceptor != null) {
            mInterceptors.add(interceptor);
        }
        return this;
    }

    public RequestOption doGet(String url) {
        return new RequestOption(url, RequestOption.REQUEST_GET);
    }

    public RequestOption doPost(String url) {
        return new RequestOption(url, RequestOption.REQUEST_POST);
    }

    public RequestOption doPut(String url) {
        return new RequestOption(url, RequestOption.REQUEST_PUT);
    }

    public RequestOption doDelete(String url) {
        return new RequestOption(url, RequestOption.REQUEST_DELETE);
    }


}
