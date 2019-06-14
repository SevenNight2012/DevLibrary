package com.xxc.dev.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        if (null == collection || collection.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection collection) {
        if (null != collection && collection.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map map) {
        if (null == map || map.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Map map) {
        if (null != map && map.size() > 0) {
            return true;
        }
        return false;
    }

}
