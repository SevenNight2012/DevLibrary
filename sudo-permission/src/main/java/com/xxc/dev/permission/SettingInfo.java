package com.xxc.dev.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;

/**
 * 跳转到setting界面的弹窗信息的封装
 */
public class SettingInfo {

    private String mTitle = "权限申请";
    private String mMessage = "应用需要相关权限后才能正常运行，请到设置中授权后体验对应功能";
    private String mPositiveText = "授权";
    private String mNegativeText = "取消";

    public SettingInfo setTitle(String title) {
        mTitle = title;
        return this;
    }

    public SettingInfo setMessage(String message) {
        mMessage = message;
        return this;
    }

    public SettingInfo setPositiveText(String positiveText) {
        mPositiveText = positiveText;
        return this;
    }

    public SettingInfo setNegativeText(String negativeText) {
        mNegativeText = negativeText;
        return this;
    }

    static Dialog createDialog(Activity activity, OnClickListener postiveClick, OnClickListener negativeClick) {
        return createDialog(activity, new SettingInfo(), postiveClick, negativeClick);
    }

    public static Dialog createDialog(Activity activity, SettingInfo dialogBean, OnClickListener postiveClick, OnClickListener negativeClick) {
        if (null == dialogBean) {
            dialogBean = new SettingInfo();
        }
        Builder builder = new AlertDialog.Builder(activity);
        return builder.setTitle(dialogBean.mTitle)
                      .setMessage(dialogBean.mMessage)
                      .setPositiveButton(dialogBean.mPositiveText, postiveClick)
                      .setNegativeButton(dialogBean.mNegativeText, negativeClick)
                      .create();
    }
}
