package com.xxc.dev.presenter.impl;

import com.xxc.dev.presenter.CallBy;
import com.xxc.dev.presenter.ITestView;

public class SuperView {

    @CallBy(key = ITestView.PRINT)
    public void print() {
        System.out.println("我是父类");
    }

}
