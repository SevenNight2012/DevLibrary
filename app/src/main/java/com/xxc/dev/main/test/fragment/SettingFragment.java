package com.xxc.dev.main.test.fragment;

import android.os.Bundle;
import android.widget.TextView;
import com.xxc.dev.main.R;
import com.xxc.dev.main.base.BaseFragmentV4;

public class SettingFragment extends BaseFragmentV4 {

    private TextView mText;

    @Override
    public int getContentId() {
        return R.layout.base_fragment;
    }

    @Override
    public void initWidgets(Bundle savedInstanceState) {
        mText = findViewById(R.id.text_hint);
        mText.setText("设置");
    }
}
