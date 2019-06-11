package com.xxc.dev.presenter;

import android.content.Context;
import com.xxc.dev.common.callback.CallBack1;
import com.xxc.dev.common.utils.SafeWeakReference;
import java.lang.reflect.Proxy;

/**
 * MVP模式，P层的基类
 */
public abstract class BasePresenter {

    private SafeWeakReference<Context> mContext;
    private Object mProxy;

    public void attach(Context context, Object real) {
        mContext = new SafeWeakReference<>(context);
        mProxy = Proxy.newProxyInstance(real.getClass().getClassLoader(), viewTypes(), new ProxyHandler(this, real));
    }


    public boolean isAttach() {
        return null != mContext.get();
    }

    /**
     * 获取view对象，外部调用时需要判定是否为空
     *
     * @param <V> view的泛型
     * @return view对象，实际是通过代理对象强转而成
     */
    public <V extends IView> V getView() {
        if (isAttach()) {
            if (null == mProxy) {
                return null;
            }
            try {
                return (V) mProxy;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过回调的方法获取view对象，是空安全的，内部已处理判空逻辑
     *
     * @param caller 回调对象
     * @param <V>    view的泛型
     */
    public <V extends IView> void postView(CallBack1<V> caller) {
        if (isAttach() && null != mProxy && null != caller) {
            try {
                caller.onCallBack((V) mProxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解绑
     */
    public void detach() {
        mContext.clear();
    }

    public abstract Class<? extends IView>[] viewTypes();

}
