package com.xxc.dev.network.okgo;

import com.lzy.okgo.callback.FileCallback;
import com.xxc.dev.network.request.IProgressListener;
import com.xxc.dev.network.request.RequestOption;
import com.xxc.dev.network.response.INetworkResult;
import com.xxc.dev.network.response.NetworkError;
import java.io.File;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 文件下载回调
 */
public class OkGoFileCallBack extends FileCallback {

    private INetworkResult<File> mResult;
    private RequestOption mOption;
    private IProgressListener mListener;

    public OkGoFileCallBack(INetworkResult<File> result, RequestOption option, IProgressListener listener) {
        mResult = result;
        mOption = option;
        mListener = listener;
    }

    @Override
    public void onSuccess(File file, Call call, Response response) {
        if (mResult != null) {
            mResult.onSuccess(mOption.getTag(), file);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        if (mResult != null) {
            mResult.onFailure(new NetworkError(response.message(), response.code(), e));
        }
    }

    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        if (mListener != null) {
            mListener.onProgress(mOption.getTag(), totalSize, currentSize);
        }
    }
}
