package com.xxc.dev.network.response;

import org.json.JSONObject;

/**
 * json类型的网络回调
 */
public class JsonNetworkResult implements INetworkResult<JSONObject> {

    @Override
    public void onSuccess(Object tag, JSONObject object) {

    }

    @Override
    public void onFailure(NetworkError error) {

    }
}
