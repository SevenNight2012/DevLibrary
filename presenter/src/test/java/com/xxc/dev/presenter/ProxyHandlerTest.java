package com.xxc.dev.presenter;

import android.content.Context;
import android.util.LruCache;
import com.xxc.dev.common.callback.CallBack1;
import com.xxc.dev.presenter.impl.MotherView;
import com.xxc.dev.presenter.impl.SonView;
import com.xxc.dev.presenter.impl.SuperView;
import java.lang.reflect.Method;
import org.junit.Test;
import org.mockito.Mockito;

public class ProxyHandlerTest {

    private int count = 0;

    @Test
    public void invoke() {
        LruCache<MethodKey, Method> cache = new LruCache<>(64);
        ProxyHandler.setMethodCache(cache);
        invokePresenter(new SonView());
        invokePresenter(new SuperView());
        invokePresenter(new SonView());
        invokePresenter(new MotherView());
        invokePresenter(new SonView());
        invokePresenter(new SonView());
        invokePresenter(new SonView());
//        invokePresenter(new SuperView());
        invokePresenter(new MotherView());
        System.out.println("cache size:" + cache.size());
    }

    private void invokePresenter(Object real) {
        count++;
        TestPresenter presenter = new TestPresenter();
        presenter.attach(Mockito.mock(Context.class), real);
        if (count == 3) {
            presenter.detach();
        }
        presenter.postView((CallBack1<ITestView>) iView -> {
            iView.print("go go go");
        });
//        ITestView testView = presenter.getView();
//        if (testView != null) {
//            testView.print("go go go");
//        } else {
//            System.out.println("TestView is null");
//        }
    }
}