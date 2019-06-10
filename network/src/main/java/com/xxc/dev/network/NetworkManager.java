package com.xxc.dev.network;

import com.lzy.okgo.OkGo;
import com.xxc.dev.common.AppUtils;
import com.xxc.dev.network.okgo.OkGoRequest;
import com.xxc.dev.network.request.AbsRequest;
import com.xxc.dev.network.request.RequestOption;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络管理
 */
public class NetworkManager {

    public static final String TAG = "NetworkManager";

    private static final NetworkManager INSTANCE = new NetworkManager();

    private List<IRequestInterceptor> mInterceptors = new ArrayList<>();

    private Class<? extends AbsRequest> mRequestClass = OkGoRequest.class;

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

    public List<IRequestInterceptor> getGlobalInterceptors() {
        return mInterceptors;
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

    public void cancel(Object tag) {
        try {
            Method cancelMethod = mRequestClass.getMethod("cancel", Object.class);
            cancelMethod.invoke(null, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAll() {
        try {
            Method cancelMethod = mRequestClass.getMethod("cancelAll");
            cancelMethod.invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
