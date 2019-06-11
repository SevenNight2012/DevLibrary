package com.xxc.dev.common.widgets.banner;

/**
 * 指示器资源封装类
 */
public class CursorSelector {

    private int mSelectedRes;
    private int mUnselectedRes;

    public CursorSelector(int selectedRes, int unselectedRes) {
        mSelectedRes = selectedRes;
        mUnselectedRes = unselectedRes;
    }

    public int getSelectedRes() {
        return mSelectedRes;
    }

    public CursorSelector setSelectedRes(int selectedRes) {
        mSelectedRes = selectedRes;
        return this;
    }

    public int getUnselectedRes() {
        return mUnselectedRes;
    }

    public CursorSelector setUnselectedRes(int unselectedRes) {
        mUnselectedRes = unselectedRes;
        return this;
    }
}
