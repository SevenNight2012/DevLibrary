package com.xxc.dev.permission;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import com.xxc.dev.common.callback.CallBack1;
import java.util.List;

/**
 * 继承自{@link Fragment}的权限请求fragment
 */
public class PermissionFragment extends Fragment {

    public static final String TAG = "PermissionFragment";

    private static final int START_SETTING_CODE = 2 << 10;

    private PermissionBean mPermission;

    public PermissionFragment setPermission(PermissionBean permission) {
        mPermission = permission;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPermission != null) {
            doRequest();
        }
    }

    /**
     * copy from {@link android.support.v4.app.ActivityCompat#requestPermissions(Activity, String[], int)}
     */
    private void doRequest() {
        String[] permissions = mPermission.getPermissions();
        if (permissions != null && permissions.length > 0) {
            Activity activity = getActivity();

            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                requestPermissions(mPermission.getPermissions(), mPermission.getRequestCode());
            } else {
                int[] grantResults = new int[permissions.length];
                PackageManager packageManager = activity.getPackageManager();
                String packageName = activity.getPackageName();
                int permissionCount = permissions.length;
                for (int i = 0; i < permissionCount; ++i) {
                    grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
                }
                onRequestPermissionsResult(mPermission.getRequestCode(), permissions, grantResults);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_SETTING_CODE && null != mPermission) {
            mPermission.handleActivityResult(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (null == mPermission || null == getActivity()) {
            return;
        }
        Activity activity = getActivity();

        SettingInfo info = mPermission.getSettingInfo();
        CallBack1<List<String>> neverAsk = mPermission.getNeverAsk();
        if (neverAsk == null) {
            mPermission.setNeverAsk(strings -> {
                Dialog settingDialog = SettingInfo.createDialog(getActivity(), info, (dialog, which) -> {
                    //引导用户至设置页手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, START_SETTING_CODE);
                }, (dialog, which) -> dialog.dismiss());
                settingDialog.show();
            });
        }

        int code = mPermission.getRequestCode();
        if (code == requestCode) {
            mPermission.handleResult(activity, permissions, grantResults);
        } else {
            Log.e(Sudo.TAG, "request code error:" + code + "   " + requestCode);
        }
    }
}