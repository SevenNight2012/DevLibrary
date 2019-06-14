package com.xxc.dev.main.test.permission;

import android.app.Activity;
import android.os.Bundle;
import com.mrcd.xrouter.annotation.XPath;
import com.xxc.dev.main.R;

@XPath
public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_request_permission);
        getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, new TestPermissionFragment())
                            .commitAllowingStateLoss();
    }

}
