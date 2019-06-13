package com.xxc.dev.network.okgo;

import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.xxc.dev.network.okgo.exception.GenericException;
import com.xxc.dev.network.request.IProgressListener;
import com.xxc.dev.network.request.RequestOption;
import com.xxc.dev.network.response.INetworkResult;
import com.xxc.dev.network.response.NetworkError;
import com.xxc.dev.network.utils.NetworkGenericUtils;
import java.lang.reflect.Type;
import okhttp3.Call;
import okhttp3.Response;

public class OkGoUploadStringCallBack extends StringCallback {

    private static Gson sGson = new Gson();

    private INetworkResult mResult;
    private IProgressListener mListener;
    private RequestOption mOption;

    public OkGoUploadStringCallBack(INetworkResult result, IProgressListener listener, RequestOption option) {
        mResult = result;
        mListener = listener;
        mOption = option;
    }

    @Override
    public void onSuccess(String json, Call call, Response response) {
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

    @Override
    public void onError(Call call, Response response, Exception e) {
        if (mResult != null) {
            mResult.onFailure(new NetworkError(response.message(), response.code(), e));
        }
    }

    @Override
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        if (mListener != null) {
            mListener.onProgress(mOption.getTag(), totalSize, currentSize);
        }
    }
}
