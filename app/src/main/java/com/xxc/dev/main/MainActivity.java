package com.xxc.dev.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.mrcd.xrouter.XRouter;
import com.xxc.dev.image.ImageEngine;
import com.xxc.dev.image.glide.cache.GlideDiskCacheFactory;
import com.xxc.dev.main.test.Constant;
import java.io.File;

public class MainActivity extends AppCompatActivity implements Callback {

    public static final String TAG = "MainActivity";

    ImageView mPreview;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(this);
        mPreview = findViewById(R.id.preview);

        ImageEngine.getInstance().load(this, Constant.URL, mPreview, progress -> {
            if (progress == 100) {
                mHandler.sendEmptyMessage(0);
            }
            Log.d(TAG, "onCreate: " + progress);
        });
    }

    public void gotoTarget(View view) {
        XRouter.getInstance().bottomTabActivity().launch(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                File file = GlideDiskCacheFactory.getCacheFile(Constant.URL);
                if (file != null) {
                    Log.d(TAG, "handleMessage: " + file.getAbsolutePath());
                } else {
                    Log.d(TAG, "handleMessage: 文件不存在  ");
                }
                break;
        }
        return false;
    }
}
