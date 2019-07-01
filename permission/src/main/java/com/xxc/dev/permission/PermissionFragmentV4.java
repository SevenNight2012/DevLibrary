package com.xxc.dev.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * 专门用于请求权限的Fragment，继承自V4包下的fragment
 */
public class PermissionFragmentV4 extends Fragment {

    public static final String TAG = "PermissionFragmentTag";

    private PermissionBean mPermission;

    public PermissionFragmentV4 setPermission(PermissionBean permission) {
        mPermission = permission;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doRequest(mPermission);
    }

    void doRequest(PermissionBean permission) {
        if (null == permission) {
            return;
        }
        String[] permissions = permission.getPermissions();
        if (permissions != null && permissions.length > 0) {
            requestPermissions(permission.getPermissions(), permission.getRequestCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionBean.START_SETTING_CODE && null != mPermission) {
            mPermission.handleActivityResult(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Activity activity = getActivity();
        if (mPermission != null && null != activity) {
            mPermission.processPermissionResult(activity, requestCode, permissions, grantResults, (dialog, which) -> {
                //引导用户至设置页手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, PermissionBean.START_SETTING_CODE);
            });
        }
    }
}
