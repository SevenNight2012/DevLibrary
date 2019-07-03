package com.xxc.dev.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;

/**
 * 继承自{@link Fragment}的权限请求fragment
 */
public class PermissionFragment extends Fragment {

    public static final String TAG = "PermissionFragment";

    private PermissionHandler mPermission;

    public PermissionFragment setPermission(PermissionHandler permission) {
        mPermission = permission;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doRequest(mPermission);
    }

    /**
     * copy from {@link android.support.v4.app.ActivityCompat#requestPermissions(Activity, String[], int)}
     */
    void doRequest(PermissionHandler permission) {
        if (permission == null) {
            return;
        }
        String[] permissions = permission.getPermissions();
        if (permissions != null && permissions.length > 0) {
            Activity activity = getActivity();

            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                requestPermissions(permission.getPermissions(), permission.getRequestCode());
            } else {
                int[] grantResults = new int[permissions.length];
                PackageManager packageManager = activity.getPackageManager();
                String packageName = activity.getPackageName();
                int permissionCount = permissions.length;
                for (int i = 0; i < permissionCount; ++i) {
                    grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
                }
                onRequestPermissionsResult(permission.getRequestCode(), permissions, grantResults);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionHandler.START_SETTING_CODE && null != mPermission) {
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
                startActivityForResult(intent, PermissionHandler.START_SETTING_CODE);
            });
        }
    }
}
