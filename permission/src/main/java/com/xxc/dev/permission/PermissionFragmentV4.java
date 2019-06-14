package com.xxc.dev.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.xxc.dev.common.callback.CallBack1;
import java.util.List;

/**
 * 专门用于请求权限的Fragment，继承自V4包下的fragment
 */
public class PermissionFragmentV4 extends Fragment {

    public static final String PERMISSION_TAG = "PermissionFragmentTag";

    private static final int START_SETTING_CODE = 2 << 10;

    private PermissionBean mPermission;

    public PermissionFragmentV4 setPermission(PermissionBean permission) {
        mPermission = permission;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPermission != null) {
            doRequest();
        }
    }

    private void doRequest() {
        String[] permissions = mPermission.getPermissions();
        if (permissions != null && permissions.length > 0) {
            requestPermissions(mPermission.getPermissions(), mPermission.getRequestCode());
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
        if (requestCode == code) {
            mPermission.handleResult(activity, permissions, grantResults);
        } else {
            Log.e(Sudo.TAG, "request code error:" + code + "   " + requestCode);
        }
    }
}
