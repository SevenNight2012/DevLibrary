package com.xxc.dev.presenter.impl;

import com.xxc.dev.presenter.CallBy;
import com.xxc.dev.presenter.ITestView;

public class SonView extends SuperView {

    @CallBy(key = ITestView.PRINT)
    @Override
    public void print() {
        System.out.println("我是子类");
    }

    @CallBy(key = ITestView.PRINT)
    public void say(String content) {
        System.out.println("I am son, " + content);
    }
}
