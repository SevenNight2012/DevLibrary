package com.xxc.dev.network.utils;

import android.support.annotation.NonNull;
import com.xxc.dev.network.response.INetworkResult;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 */
public class GenericUtils {

    /**
     * 获取接口上的泛型
     *
     * @param callback 回调接口
     * @return 泛型type
     */
    public static Type getCallBackType(@NonNull INetworkResult callback) {
        Class callbackClass = iterateType(callback.getClass());
        if (null == callbackClass) {
            return null;
        }
        Type[] interfaces = callbackClass.getGenericInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            Type type = interfaces[0];
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                return parameterizedType.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    private static Class iterateType(Class clazz) {
        if (Object.class.getName().equalsIgnoreCase(clazz.getName())) {
            return null;
        }
        Class[] interfaces = clazz.getInterfaces();
        if (null != interfaces && interfaces.length > 0) {
            for (Class c : interfaces) {
                if (c.getName().equalsIgnoreCase(INetworkResult.class.getName())) {
                    return clazz;
                }
            }
            return iterateType(clazz.getSuperclass());
        } else {
            return iterateType(clazz.getSuperclass());
        }
    }


}
