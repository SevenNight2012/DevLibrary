package com.xxc.dev.image.utils;

import com.xxc.dev.common.utils.GenericUtils;
import com.xxc.dev.image.listener.ImageLoadListener;
import java.lang.reflect.Type;

/**
 * 获取回调接口上的类型
 */
public class ImageGenericUtils {


    public static Type getImageResultType(ImageLoadListener listener) {
        return GenericUtils.getInterfaceGenericType(listener.getClass(), ImageLoadListener.class.getName());
    }

}
