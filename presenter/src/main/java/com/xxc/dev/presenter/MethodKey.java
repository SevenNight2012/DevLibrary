package com.xxc.dev.presenter;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 缓存用的Key
 */
class MethodKey {

    private Method mMethod;
    private Class mRealClass;

    public MethodKey(Method method, Class realClass) {
        mMethod = method;
        mRealClass = realClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MethodKey methodKey = (MethodKey) o;
        return Objects.equals(mMethod, methodKey.mMethod) && Objects.equals(mRealClass, methodKey.mRealClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMethod, mRealClass);
    }
}
