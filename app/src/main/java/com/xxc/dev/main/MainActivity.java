package com.xxc.dev.main;

import android.Manifest.permission;
import android.graphics.drawable.Drawable;
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
import com.xxc.dev.image.listener.DrawableImageListener;
import com.xxc.dev.main.test.Constant;
import com.xxc.dev.permission.Sudo;

public class MainActivity extends AppCompatActivity implements Callback {

    public static final int REQUEST_PERMISSION_CODE = 111;
    public static final String TAG = "MainActivity";

    ImageView mPreview;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(this);
        mPreview = findViewById(R.id.preview);

        Sudo.getInstance().prepare().setPermission(permission.WRITE_EXTERNAL_STORAGE).setGranted(permissions -> {
            Log.d(TAG, "onCreate: 所有权限通过");
            ImageEngine.getInstance()
                       .load(MainActivity.this, Constant.URL, mPreview, null, new DrawableImageListener() {
                           @Override
                           public void onSuccess(String url, Drawable drawable) {
                               mPreview.setImageDrawable(drawable);
                               mHandler.sendEmptyMessage(0);
                           }
                       }, progress -> Log.d(TAG, "加载进度:" + progress));
        }).request(this);
    }

    public void gotoTarget(View view) {
        XRouter.getInstance().bottomTabActivity().launch(this);
    }

    public void gotoPermission(View view) {
        XRouter.getInstance().permissionActivity().launch(this);
    }

    public void gotoHttps(View view) {
        XRouter.getInstance().httpsDemoActivity().launch(this);
    }

    public void gotoAdapter(View view) {
        XRouter.getInstance().adapterPreviewActivity().launch(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                ImageEngine.findDiskCache(Constant.URL, file -> {
                    if (file != null) {
                        Log.d(TAG, "handleMessage: " + file.getAbsolutePath());
                    } else {
                        Log.d(TAG, "handleMessage: 文件不存在  ");
                    }
                });
                break;
        }
        return false;
    }
}
