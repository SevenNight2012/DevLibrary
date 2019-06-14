package com.xxc.dev.main.test.permission;

import android.Manifest.permission;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.xxc.dev.main.R;
import com.xxc.dev.permission.Sudo;

public class TestPermissionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_permission, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sudo.getInstance()
            .prepare()
            .setPermission(permission.READ_CONTACTS)
            .setGranted(strings -> Toast.makeText(getActivity(), "获得权限", Toast.LENGTH_SHORT).show())
            .request(this);
    }
}
