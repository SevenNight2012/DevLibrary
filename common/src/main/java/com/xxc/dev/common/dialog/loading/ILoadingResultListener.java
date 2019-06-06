package com.xxc.dev.common.dialog.loading;

import java.util.Map;

/**
 * 加载监听器
 */
public interface ILoadingResultListener {

    void onCompleted(Map<String, String> extra);

    void onError(String error);
}
