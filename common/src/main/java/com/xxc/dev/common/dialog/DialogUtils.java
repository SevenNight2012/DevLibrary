package com.xxc.dev.common.dialog;

import android.app.Dialog;

/**
 * 弹窗工具类
 */
public class DialogUtils {

    public static void showSafe(Dialog dialog) {
        if (dialog != null) {
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void dismissSafe(Dialog dialog) {
        if (null != dialog) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
