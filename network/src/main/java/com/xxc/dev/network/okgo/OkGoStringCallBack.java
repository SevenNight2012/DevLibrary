package com.xxc.dev.network.okgo;

import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.xxc.dev.network.okgo.exception.GenericException;
import com.xxc.dev.network.request.RequestOption;
import com.xxc.dev.network.response.INetworkResult;
import com.xxc.dev.network.response.NetworkError;
import com.xxc.dev.network.utils.NetworkGenericUtils;
import java.lang.reflect.Type;
import okhttp3.Call;
import okhttp3.Response;

public class OkGoStringCallBack extends StringCallback {

    private static Gson sGson = new Gson();
    private INetworkResult mResult;
    private RequestOption mOption;

    public OkGoStringCallBack(INetworkResult result, RequestOption option) {
        mResult = result;
        mOption = option;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        postResponse(call, s);
    }

    @Override
    public void onCacheSuccess(String s, Call call) {
        postResponse(call, s);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        if (mResult != null) {
            mResult.onFailure(new NetworkError(response.message(), response.code(), e));
        }
    }

    private void postResponse(Call call, String json) {
        if (mResult != null) {
            Type type = NetworkGenericUtils.getCallBackType(mResult);
            if (type != null) {
                if (mResult != null) {
                    mResult.onSuccess(mOption.getTag(), sGson.fromJson(json, type));
                }
            } else {
                onError(call, null, new GenericException());
            }
        }
    }
}
