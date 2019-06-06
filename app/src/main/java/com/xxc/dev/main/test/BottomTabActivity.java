package com.xxc.dev.main.test;

import android.os.Bundle;
import com.mrcd.xrouter.annotation.XPath;
import com.xxc.dev.common.callback.CallBack2;
import com.xxc.dev.main.base.BaseBottomTabActivity;
import com.xxc.dev.main.base.BaseFragmentV4;
import com.xxc.dev.main.base.tab.BaseTabViewMaker;
import com.xxc.dev.main.base.tab.SimpleStringMaker;
import com.xxc.dev.main.test.fragment.CoreFragment;
import com.xxc.dev.main.test.fragment.HomeFragment;
import com.xxc.dev.main.test.fragment.SettingFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XPath
public class BottomTabActivity extends BaseBottomTabActivity {


    private List<BaseFragmentV4> mFragments = new ArrayList<>();
    private List<String> mTitles = Arrays.asList(new String[]{"主页", "内容", "设置"});

    @Override
    public int tabCount() {
        return mFragments.size();
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        mFragments.add(new HomeFragment());
        mFragments.add(new CoreFragment());
        mFragments.add(new SettingFragment());
    }

    @Override
    public BaseTabViewMaker createTabViewMaker() {
        return new SimpleStringMaker(mTitles);
    }

    @Override
    public void instanceFragment(int position, CallBack2<String, BaseFragmentV4> caller) {
        caller.onCallBack(mTitles.get(position), mFragments.get(position));
    }
}
