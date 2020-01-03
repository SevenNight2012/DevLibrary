package com.xxc.dev.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限管理
 * 1. 如果不设置NeverAsk回调，内部会有默认的回调处理，处理逻辑为，展示一个跳转到设置界面的dialog，相关信息可通过SettingInfo设置，
 * 用户如果跳转到设置页面后进行了授权，在返回到应用时，会再次检测权限，如果申请的权限全部拥有，那么直接回调Granted，否则回调Refused
 * 2. 如果设置了NeverAsk的回调，那么设置的SettingInfo信息将会失效，所有不再询问的逻辑处理将有开发者自行处理
 */
public class Sudo {

    public static final String TAG = "Sudo";

    private static final Sudo INSTANCE = new Sudo();

    public static Sudo getInstance() {
        return INSTANCE;
    }

    private Sudo() {
    }

    public static boolean hasPermission(Context context, String permission) {
        return checkPermission(context, permission);
    }

    public static boolean hasPermission(Context context, String[] permissions) {
        if (null == context || null == permissions) {
            return false;
        }
        for (String permission : permissions) {
            boolean result = hasPermission(context, permission);
            if (!result) {
                //只要有一个权限没有通过  那么直接返回false
                return false;
            }
        }
        return true;
    }

    /**
     * 从所有需要的权限集合中，获取未授权的权限
     *
     * @param context     activity对象
     * @param permissions 所有需要的权限集合
     * @return 未授权的权限集合
     */
    public List<String> getUnauthorizedPermission(Context context, List<String> permissions) {
        if (null == context || null == permissions) {
            return new ArrayList<>();
        }
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    public List<String> getUnauthorizedPermission(Context context, String[] permissions) {
        return getUnauthorizedPermission(context, Arrays.asList(permissions));
    }

    public PermissionHandler prepare() {
        return new PermissionHandler();
    }

    private static boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED || PermissionChecker
            .checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            //PermissionChecker在检测权限时更保险
            //详见https://juejin.im/post/5bdd25386fb9a049b13da206
            return false;
        }
        return true;
    }

}
