package com.xxc.dev.main.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.mrcd.xrouter.annotation.XPath;
import com.xxc.dev.main.R;
import com.xxc.dev.main.base.AbsActivity;
import com.xxc.dev.network.NetworkManager;
import com.xxc.dev.network.response.NetworkError;
import com.xxc.dev.network.response.StringNetworkResult;

@XPath
public class HttpsDemoActivity extends AbsActivity {

    public static final String TAG = "HttpsDemoActivity";

    private Button mPostRequest;

    @Override
    protected void init(Bundle savedInstanceState) {
        mPostRequest = findViewById(R.id.post_request);
        mPostRequest.setOnClickListener(v -> {
            doRequest();
        });
    }

    private void doRequest() {
        NetworkManager.getInstance().doGet("https://10.0.1.53:8443/hello").request(new StringNetworkResult() {
            @Override
            public void onSuccess(Object tag, String result) {
                Log.d(TAG, "onSuccess: " + result);
                mPostRequest.setText("请求成功:" + result);
            }

            @Override
            public void onFailure(NetworkError error) {
                Log.d(TAG, "onFailure: " + error);
                mPostRequest.setText("请求失败");
            }
        });
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_https_demo;
    }
}
