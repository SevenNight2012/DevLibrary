package com.xxc.dev.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.mrcd.xrouter.XRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoTarget(View view) {
        XRouter.getInstance().bottomTabActivity().launch(this);
    }
}
