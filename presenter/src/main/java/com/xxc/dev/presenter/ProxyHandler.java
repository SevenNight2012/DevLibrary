package com.xxc.dev.presenter;

import android.util.LruCache;
import com.xxc.dev.common.utils.SafeWeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 代理
 * 内部会通过代理对象调用方法的注解寻找真实对象需要执行的方法
 * 通过注解的方式管理起对应的方法，避免在Activity中方法冗余
 */
public class ProxyHandler implements InvocationHandler {

    /**
     * 64个方法缓存
     */
    private static LruCache<MethodKey, Method> sMethodCache = new LruCache<>(1 << 6);

    private BasePresenter mPresenter;
    private SafeWeakReference<Object> mRealReference;

    public static void setMethodCache(LruCache<MethodKey, Method> methodCache) {
        if (methodCache != null) {
            sMethodCache = methodCache;
        }
    }

    public ProxyHandler(BasePresenter presenter, Object real) {
        mPresenter = presenter;
        mRealReference = new SafeWeakReference<>(real);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (null == mPresenter || !mPresenter.isAttach()) {
            return null;
        }
        CallBy callBy = method.getAnnotation(CallBy.class);
        Object realObj = mRealReference.get();
        if (null == realObj) {
            return null;
        } else if (callBy != null) {
            String key = callBy.key();
            Method cacheMethod = sMethodCache.get(new MethodKey(method, realObj.getClass()));
            if (cacheMethod != null && cacheMethod.getDeclaringClass().isInstance(realObj)) {
                return cacheMethod.invoke(realObj, args);
            }
            //先遍历当前类的方法，如果找到，那么直接就返回，否则继续遍历父类的方法
            Method methodFromMyself = findMethodFromMyself(key, method, realObj);
            if (methodFromMyself != null) {
                Object result = methodFromMyself.invoke(realObj, args);
                sMethodCache.put(new MethodKey(method, realObj.getClass()), methodFromMyself);
                return result;
            }
            Method methodFromSuper = findMethodFromSuper(key, method, realObj);
            if (methodFromSuper != null) {
                Object result = methodFromSuper.invoke(realObj, args);
                sMethodCache.put(new MethodKey(method, realObj.getClass()), methodFromSuper);
                return result;
            }
        } else {
            return method.invoke(realObj, args);
        }
        return null;
    }

    /**
     * 从当前类中找对应的方法
     *
     * @param name    注解中配置的key
     * @param method  代理方法
     * @param realObj 对象
     * @return 找到的方法
     */
    private Method findMethodFromMyself(String name, Method method, Object realObj) {
        Method[] declaredMethods = realObj.getClass().getDeclaredMethods();
        for (Method itemMethod : declaredMethods) {
            CallBy annotation = itemMethod.getAnnotation(CallBy.class);
            if (annotation != null && annotation.key().equals(name) && checkMethods(method, itemMethod)) {
                return itemMethod;
            }
        }
        return null;
    }

    /**
     * 从父类中找对应的方法
     *
     * @param name    注解中配置的key
     * @param method  代理方法对象
     * @param realObj 当前类，真正将会执行的对象
     * @return 对应的方法
     */
    private Method findMethodFromSuper(String name, Method method, Object realObj) {
        Method[] methods = realObj.getClass().getMethods();
        for (Method item : methods) {
            CallBy annotation = item.getAnnotation(CallBy.class);
            if (annotation != null && annotation.key().equals(name) && checkMethods(method, item)) {
                return item;
            }
        }
        return null;
    }


    private boolean checkMethods(Method proxyMethod, Method realMethod) {
        Class<?>[] proxyMethodParameterTypes = proxyMethod.getParameterTypes();
        Class<?>[] realMethodParameterTypes = realMethod.getParameterTypes();
        String proxyName = proxyMethod.getReturnType().getName();
        String realName = realMethod.getReturnType().getName();
        return Arrays.equals(proxyMethodParameterTypes, realMethodParameterTypes) && proxyName.equals(realName);
    }
}
