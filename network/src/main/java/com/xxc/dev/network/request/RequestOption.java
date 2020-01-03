package com.xxc.dev.network.request;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import android.util.Log;
import com.xxc.dev.network.NetworkManager;
import com.xxc.dev.network.okgo.OkGoRequest;
import com.xxc.dev.network.response.INetworkResult;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求配置
 */
public class RequestOption {

    /**
     * get方式请求
     */
    public static final int REQUEST_GET = 1;

    /**
     * post方式请求
     */
    public static final int REQUEST_POST = 2;

    /**
     * put方式请求
     */
    public static final int REQUEST_PUT = 3;

    /**
     * delete方式请求
     */
    public static final int REQUEST_DELETE = 4;

    @IntDef(value = {REQUEST_GET, REQUEST_POST, REQUEST_PUT, REQUEST_DELETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestType {

    }

    private String mUrl;
    private Map<String, String> mHeaders = new HashMap<>();
    private Map<String, String> mParams = new HashMap<>();
    private Map<String, File> mFiles = new HashMap<>();
    private Map<String, List<File>> mFileList = new HashMap<>();
    private Map<String, String> mExtra = new HashMap<>();
    private Object mTag;

    private int mRequestTime = 15 * 1000;
    private int mReadTime = 15 * 1000;

    private int mRequestType = REQUEST_GET;

    public RequestOption(@NonNull String url, @RequestType int requestType) {
        mUrl = url;
        mRequestType = requestType;
    }

    public RequestOption setHeaders(Map<String, String> headers) {
        if (headers != null) {
            mHeaders.putAll(headers);
        }
        return this;
    }

    public RequestOption setParams(Map<String, String> params) {
        if (params != null) {
            mParams.putAll(params);
        }
        return this;
    }

    public RequestOption setFiles(Map<String, File> files) {
        if (files != null) {
            mFiles.putAll(files);
        }
        return this;
    }

    public RequestOption setExtra(Map<String, String> extra) {
        if (extra != null) {
            mExtra.putAll(extra);
        }
        return this;
    }

    public RequestOption setRequestTime(int requestTime) {
        if (requestTime > 0) {
            mRequestTime = requestTime;
        }
        return this;
    }

    public RequestOption setReadTime(int readTime) {
        if (readTime > 0) {
            mReadTime = readTime;
        }
        return this;
    }

    public RequestOption putHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    public RequestOption putParam(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    public RequestOption putFile(String key, File file) {
        mFiles.put(key, file);
        return this;
    }

    public RequestOption putFiles(String key, List<File> files) {
        if (files != null) {
            mFileList.put(key, files);
        }
        return this;
    }

    public Map<String, String> getExtra() {
        return mExtra;
    }

    public RequestOption putExtra(String key, String value) {
        mExtra.put(key, value);
        return this;
    }

    public RequestOption setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public Object getTag() {
        return mTag;
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public Map<String, File> getFiles() {
        return mFiles;
    }

    public Map<String, List<File>> getFileList() {
        return mFileList;
    }

    public int getRequestTime() {
        return mRequestTime;
    }

    public int getReadTime() {
        return mReadTime;
    }

    public int getRequestType() {
        return mRequestType;
    }

    public void request(INetworkResult result) {
        AbsRequest requester = AbsRequest.instance(this);
        if (requester != null) {
            requester.request(result);
        }
    }

    public void download(INetworkResult<File> result) {
        AbsRequest requester = AbsRequest.instance(this);
        if (requester != null) {
            requester.download(result);
        }
    }

    public void upload(INetworkResult result) {
        AbsRequest requester = AbsRequest.instance(this);
        if (requester != null) {
            requester.upload(result);
        }
    }

    public AbsRequest prepare() {
        AbsRequest instance = AbsRequest.instance(this);
        if (null == instance) {
            String className = NetworkManager.getInstance().getRequestClass().getName();
            Log.e(NetworkManager.TAG, String.format(AbsRequest.ERROR_CONSTRUCTOR_FORMAT, className));
            return new OkGoRequest(this);
        }
        return instance;
    }
}
