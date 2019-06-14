package com.xxc.dev.main.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 所有activity的基类
 */
public abstract class AbsActivity extends AppCompatActivity {

    public void beforeCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getContentId();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        writeInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void writeInstanceState(Bundle outState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
