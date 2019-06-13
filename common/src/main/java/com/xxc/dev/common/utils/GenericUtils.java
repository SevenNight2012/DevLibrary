package com.xxc.dev.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取泛型类型工具类
 */
public class GenericUtils {

    public static Type getInterfaceGenericType(Class clazz, String interfaceName) {
        Class callbackClass = iterateType(clazz, interfaceName);
        if (null == callbackClass) {
            return null;
        }
        Type[] interfaces = callbackClass.getGenericInterfaces();
        if (interfaces.length > 0) {
            Type type = interfaces[0];
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                return parameterizedType.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    private static Class iterateType(Class clazz, String interfaceName) {
        if (Object.class.getName().equalsIgnoreCase(clazz.getName())) {
            return null;
        }
        Class[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            for (Class c : interfaces) {
                if (c.getName().equalsIgnoreCase(interfaceName)) {
                    return clazz;
                }
            }
            return iterateType(clazz.getSuperclass(), interfaceName);
        } else {
            return iterateType(clazz.getSuperclass(), interfaceName);
        }
    }

}
