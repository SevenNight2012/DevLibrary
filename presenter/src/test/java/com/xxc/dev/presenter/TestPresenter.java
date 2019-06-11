package com.xxc.dev.presenter;

public class TestPresenter extends BasePresenter {



    @Override
    public Class<? extends IView>[] viewTypes() {
        return new Class[]{ITestView.class};
    }
}
