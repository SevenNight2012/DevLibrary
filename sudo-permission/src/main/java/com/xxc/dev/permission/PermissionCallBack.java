package com.xxc.dev.permission;

import java.util.List;

/**
 * 权限回调
 */
public interface PermissionCallBack {

    void onCallBack(List<String> permissions);

}
