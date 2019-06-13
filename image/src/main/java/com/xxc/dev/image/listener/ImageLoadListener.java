package com.xxc.dev.image.listener;

import com.xxc.dev.image.ImageException;

/**
 * 图片加载
 *
 * @param <T> 加载完成以后的对象
 */
public interface ImageLoadListener<T> {

    void onSuccess(String url, T drawable);

    void onFailed(ImageException e);

}
