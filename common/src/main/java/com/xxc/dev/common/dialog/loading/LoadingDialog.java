package com.xxc.dev.common.dialog.loading;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.xxc.dev.common.R;
import com.xxc.dev.common.dialog.DialogUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载框
 */
public class LoadingDialog extends Dialog {

    public static final String TAG = "LoadingDialog";

    //默认loading圈的颜色
    public static final int DEFAULT_PROGRESS_COLOR = R.color.colorPrimary;
    //默认文字大小 sp单位
    public static final int DEFAULT_TEXT_SIZE = 16;
    //默认文字颜色
    public static final int DEFAULT_TEXT_COLOR = R.color.color_333333;

    private TextView mDialogHint;
    private ProgressBar mDialogProgress;
    private ILoadingResultListener mLoadingResultListener;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.progress_loading);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_loading);
        mDialogHint = findViewById(R.id.dialog_hint);
        mDialogProgress = findViewById(R.id.dialog_loading);
        setCanceledOnTouchOutside(false);
    }

    public LoadingDialog setTextColor(int textColor) {
        if (mDialogHint != null) {
            mDialogHint.setTextColor(textColor);
        }
        return this;
    }

    /**
     * 设置文字大小 单位为SP
     *
     * @param textSize sp大小
     * @return dialog对象
     */
    public LoadingDialog setTextSize(int textSize) {
        if (mDialogHint != null && 0 != textSize) {
            mDialogHint.setTextSize(textSize);
        }
        return this;
    }

    public LoadingDialog setTextHint(String textHint) {
        if (mDialogHint != null) {
            if (TextUtils.isEmpty(textHint)) {
                mDialogHint.setVisibility(View.GONE);
            } else {
                mDialogHint.setVisibility(View.VISIBLE);
                mDialogHint.setText(textHint);
            }
        }
        return this;
    }

    public LoadingDialog setTextHint(int textHintRes) {
        if (mDialogHint != null && 0 != textHintRes) {
            String text = getContext().getString(textHintRes);
            setTextHint(text);
        }
        return this;
    }

    public LoadingDialog setProgressColor(int progressColor) {
        if (mDialogProgress != null) {
            mDialogProgress.getIndeterminateDrawable()
                           .setColorFilter(progressColor, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public LoadingDialog setLoadingResultListener(ILoadingResultListener loadingResultListener) {
        if (loadingResultListener != null) {
            mLoadingResultListener = loadingResultListener;
        }
        return this;
    }

    public void loadCompleted() {
        loadCompleted(new HashMap<String, String>());
    }

    public void loadCompleted(Map<String, String> extra) {
        if (mLoadingResultListener != null) {
            mLoadingResultListener.onCompleted(extra);
        }
    }

    public void loadError() {
        loadError("");
    }

    public void loadError(String error) {
        if (mLoadingResultListener != null) {
            mLoadingResultListener.onError(error);
        }
    }

    public LoadingDialog showLoading(Context context) {
        DialogUtils.showSafe(this);
        return this;
    }

    public void dismissLoading() {
        DialogUtils.dismissSafe(this);
    }

    public static LoadingDialog createLoading(Context context) {
        return createLoading(context, "");
    }

    public static LoadingDialog createLoading(Context context, String hint) {
        return createLoading(context, hint, new SimpleLoadingResult());
    }

    public static LoadingDialog createLoading(Context context, String hint, ILoadingResultListener listener) {
        return createLoading(context, hint, DEFAULT_PROGRESS_COLOR, listener);
    }

    public static LoadingDialog createLoading(Context context, String hint, int progressColor) {
        return createLoading(context, hint, progressColor, new SimpleLoadingResult());
    }

    public static LoadingDialog createLoading(Context context, String hint, int progressColor, ILoadingResultListener listener) {
        return new LoadingDialog(context).setTextHint(hint)
                                         .setProgressColor(progressColor)
                                         .setLoadingResultListener(listener);
    }

}
