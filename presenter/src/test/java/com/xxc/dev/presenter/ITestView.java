package com.xxc.dev.presenter;

public interface ITestView extends IView {

    String PRINT = "print";

    @CallBy(key = PRINT)
    void print(String content);

}
