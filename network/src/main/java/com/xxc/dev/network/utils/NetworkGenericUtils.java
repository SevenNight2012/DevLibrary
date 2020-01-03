package com.xxc.dev.network.utils;

import androidx.annotation.NonNull;
import com.xxc.dev.common.utils.GenericUtils;
import com.xxc.dev.network.response.INetworkResult;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 */
public class NetworkGenericUtils {

    /**
     * 获取接口上的泛型
     *
     * @param callback 回调接口
     * @return 泛型type
     */
    public static Type getCallBackType(@NonNull INetworkResult callback) {
        return GenericUtils.getInterfaceGenericType(callback.getClass(), INetworkResult.class.getName());
    }

    //    public static Type getImageType()

    //    private static Type getInterfaceGenericType(Class clazz, String interfaceName) {
    //        Class callbackClass = iterateType(clazz, interfaceName);
    //        if (null == callbackClass) {
    //            return null;
    //        }
    //        Type[] interfaces = callbackClass.getGenericInterfaces();
    //        if (interfaces.length > 0) {
    //            Type type = interfaces[0];
    //            if (type instanceof ParameterizedType) {
    //                ParameterizedType parameterizedType = (ParameterizedType) type;
    //                return parameterizedType.getActualTypeArguments()[0];
    //            }
    //        }
    //        return null;
    //    }
    //
    //    private static Class iterateType(Class clazz, String interfaceName) {
    //        if (Object.class.getName().equalsIgnoreCase(clazz.getName())) {
    //            return null;
    //        }
    //        Class[] interfaces = clazz.getInterfaces();
    //        if (interfaces.length > 0) {
    //            for (Class c : interfaces) {
    //                if (c.getName().equalsIgnoreCase(interfaceName)) {
    //                    return clazz;
    //                }
    //            }
    //            return iterateType(clazz.getSuperclass(), interfaceName);
    //        } else {
    //            return iterateType(clazz.getSuperclass(), interfaceName);
    //        }
    //    }


}
