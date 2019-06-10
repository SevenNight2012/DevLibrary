package com.xxc.dev.common;

import android.app.Application;
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

    public static String getString(int id) {
        if (null != application) {
            return application.getString(id);
        }
        return "";
    }

    public static Application application() {
        return (Application) application;
    }

}
