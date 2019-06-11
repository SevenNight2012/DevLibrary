package com.xxc.dev.network.request;

import com.xxc.dev.network.NetworkManager;
import com.xxc.dev.network.okgo.OkGoRequest;
import java.lang.reflect.Constructor;

/**
 * 网络请求对象
 */
public abstract class AbsRequest implements INetworkRequest {

    public static final String ERROR_CONSTRUCTOR_FORMAT = "Requester is null,the %s constructor may be illegal";

    protected RequestOption mOption;

    public AbsRequest(RequestOption option) {
        mOption = option;
    }

    /**
     * 动态创建请求对象，如果在{@link NetworkManager}中不做配置，那么用内部默认的OkGo请求
     *
     * @param option 请求配置
     * @return 请求对象
     */
    static AbsRequest instance(RequestOption option) {
        Class<? extends AbsRequest> requestClass = NetworkManager.getInstance().getRequestClass();
        if (null == requestClass) {
            return new OkGoRequest(option);
        }
        try {
            Constructor<? extends AbsRequest> constructor = requestClass.getConstructor(RequestOption.class);
            return constructor.newInstance(option);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
