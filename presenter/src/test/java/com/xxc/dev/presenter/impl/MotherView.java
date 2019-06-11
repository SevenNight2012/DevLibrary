package com.xxc.dev.presenter.impl;

import com.xxc.dev.presenter.CallBy;
import com.xxc.dev.presenter.ITestView;

public class MotherView {

    @CallBy(key = ITestView.PRINT)
    public void say(String content) {
        System.out.println("mother test " + content);
    }

    @CallBy(key = ITestView.PRINT)
    public String say() {
        return "do your homework";
    }

}
