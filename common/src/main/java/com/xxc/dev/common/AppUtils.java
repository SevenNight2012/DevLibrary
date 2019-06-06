package com.xxc.dev.common;

import android.content.Context;

/**
 * AppUtils
 */
public class AppUtils {

    private static Context application;

    public static void inject(Context context) {
        if (null != context) {
            application = context.getApplicationContext();
        }
    }

    public static int getColor(int id) {
        if (null != application) {
            return application.getResources().getColor(id);
        }
        return 0;
    }

}
