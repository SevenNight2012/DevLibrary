package com.xxc.dev.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PermissionHandler {

    /**
     * 默认的请求权限码
     */
    public static final int DEFAULT_REQUEST_PERMISSION_CODE = 1 << 7;

    /**
     * 默认跳转到设置界面的请求码
     */
    static final int START_SETTING_CODE = 2 << 10;

    private String[] mPermissions;
    private PermissionCallBack mGranted;
    private PermissionCallBack mRefused;
    private PermissionCallBack mNeverAsk;
    private int mRequestCode = DEFAULT_REQUEST_PERMISSION_CODE;
    private SettingInfo mSettingInfo = new SettingInfo();

    String[] getPermissions() {
        return mPermissions;
    }

    public PermissionHandler setPermissions(String[] permissions) {
        mPermissions = permissions;
        return this;
    }

    public PermissionHandler setPermission(String permission) {
        mPermissions = new String[]{permission};
        return this;
    }

    PermissionCallBack getGranted() {
        return mGranted;
    }

    public PermissionHandler setGranted(PermissionCallBack granted) {
        mGranted = granted;
        return this;
    }

    PermissionCallBack getRefused() {
        return mRefused;
    }

    public PermissionHandler setRefused(PermissionCallBack refused) {
        mRefused = refused;
        return this;
    }

    PermissionCallBack getNeverAsk() {
        return mNeverAsk;
    }

    public PermissionHandler setNeverAsk(PermissionCallBack neverAsk) {
        mNeverAsk = neverAsk;
        return this;
    }

    int getRequestCode() {
        return mRequestCode;
    }

    public PermissionHandler setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    SettingInfo getSettingInfo() {
        return mSettingInfo;
    }

    public PermissionHandler setSettingInfo(SettingInfo settingInfo) {
        mSettingInfo = settingInfo;
        return this;
    }

    public void request(Fragment fragment) {
        FragmentActivity host = fragment.getActivity();
        if (host != null) {
            request(host);
        }
    }

    public void request(android.app.Fragment fragment) {
        if (fragment != null && null != fragment.getActivity()) {
            request(fragment.getActivity());
        }
    }

    public void request(Activity activity) {
        if (null == activity || null == mPermissions || null == mGranted) {
            return;
        }
        if (Sudo.hasPermission(activity, mPermissions)) {
            mGranted.onCallBack(Arrays.asList(mPermissions));
            return;
        }
        android.app.FragmentManager manager = activity.getFragmentManager();
        android.app.Fragment fragment = manager.findFragmentByTag(PermissionFragment.TAG);
        if (fragment instanceof PermissionFragment) {
            ((PermissionFragment) fragment).doRequest(this);
            return;
        }
        manager.beginTransaction()
               .add(new PermissionFragment().setPermission(this), PermissionFragment.TAG)
               .commitAllowingStateLoss();
    }

    public void request(FragmentActivity activity) {
        if (activity == null || null == mPermissions || null == mGranted) {
            return;
        }
        if (Sudo.hasPermission(activity, mPermissions)) {
            mGranted.onCallBack(Arrays.asList(mPermissions));
            return;
        }
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(PermissionFragmentV4.TAG);
        if (fragment instanceof PermissionFragmentV4) {
            ((PermissionFragmentV4) fragment).doRequest(this);
            return;
        }
        fm.beginTransaction()
          .add(new PermissionFragmentV4().setPermission(this), PermissionFragmentV4.TAG)
          .commitAllowingStateLoss();
    }

    void processPermissionResult(Activity activity, int requestCode, String[] permissions, int[] grantResults, OnClickListener clickListener) {
        if (null == activity) {
            return;
        }
        if (mNeverAsk == null) {
            mNeverAsk = strings -> {
                OnClickListener cancelClick = (dialog, which) -> dialog.dismiss();
                Dialog settingDialog = SettingInfo.createDialog(activity, mSettingInfo, clickListener, cancelClick);
                settingDialog.show();
            };
        }

        if (requestCode == mRequestCode) {
            handleResult(activity, permissions, grantResults);
        } else {
            Log.e(Sudo.TAG, "request code error:" + mRequestCode + "   " + requestCode);
        }
    }

    void handleResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Sudo.hasPermission(activity, permissions)) {
            //全部都授权了
            if (mGranted != null) {
                mGranted.onCallBack(Arrays.asList(permissions));
            }
        } else {
            List<String> deniedPermissions = new ArrayList<>();
            List<String> neverAskPermissions = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                        // 此处代表之前向用户请求过权限，但是被用户拒绝了
                        deniedPermissions.add(permissions[i]);
                    } else {
                        // 想用户请求过权限，不仅被拒绝，而且用户还选择了不再询问
                        neverAskPermissions.add(permissions[i]);
                    }
                }
            }
            if (isCollectionEmpty(neverAskPermissions)) {
                if (mRefused != null) {
                    mRefused.onCallBack(deniedPermissions);
                }
            } else {
                if (mNeverAsk != null) {
                    mNeverAsk.onCallBack(neverAskPermissions);
                }
            }
        }
    }

    void handleActivityResult(Activity activity) {
        if (null == activity) {
            return;
        }
        if (Sudo.hasPermission(activity, mPermissions)) {
            if (null != mGranted) {
                mGranted.onCallBack(Arrays.asList(mPermissions));
            }
        } else if (null != mRefused) {
            mRefused.onCallBack(Sudo.getInstance().getUnauthorizedPermission(activity, mPermissions));
        } else {
            activity.finish();
        }
    }

    boolean isCollectionEmpty(Collection collection) {
        return null == collection || collection.size() == 0;
    }
}
