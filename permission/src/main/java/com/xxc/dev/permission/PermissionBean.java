package com.xxc.dev.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.xxc.dev.common.callback.CallBack1;
import com.xxc.dev.common.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionBean {

    /**
     * 默认的请求权限码
     */
    public static final int DEFAULT_REQUEST_PERMISSION_CODE = 1 << 7;

    /**
     * 默认跳转到设置界面的请求码
     */
    static final int START_SETTING_CODE = 2 << 10;

    private String[] mPermissions;
    private CallBack1<List<String>> mGranted;
    private CallBack1<List<String>> mRefused;
    private CallBack1<List<String>> mNeverAsk;
    private int mRequestCode = DEFAULT_REQUEST_PERMISSION_CODE;
    private SettingInfo mSettingInfo = new SettingInfo();

    String[] getPermissions() {
        return mPermissions;
    }

    public PermissionBean setPermissions(String[] permissions) {
        mPermissions = permissions;
        return this;
    }

    public PermissionBean setPermission(String permission) {
        mPermissions = new String[]{permission};
        return this;
    }

    CallBack1<List<String>> getGranted() {
        return mGranted;
    }

    public PermissionBean setGranted(CallBack1<List<String>> granted) {
        mGranted = granted;
        return this;
    }

    CallBack1<List<String>> getRefused() {
        return mRefused;
    }

    public PermissionBean setRefused(CallBack1<List<String>> refused) {
        mRefused = refused;
        return this;
    }

    CallBack1<List<String>> getNeverAsk() {
        return mNeverAsk;
    }

    public PermissionBean setNeverAsk(CallBack1<List<String>> neverAsk) {
        mNeverAsk = neverAsk;
        return this;
    }

    int getRequestCode() {
        return mRequestCode;
    }

    public PermissionBean setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    SettingInfo getSettingInfo() {
        return mSettingInfo;
    }

    public PermissionBean setSettingInfo(SettingInfo settingInfo) {
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
            if (CollectionUtils.isEmpty(neverAskPermissions)) {
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
}
