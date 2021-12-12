package com.app.letsfocus.ui.fragment.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;

public class SettingFragment extends Fragment {

    TextView permission;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_setting_new, container, false);
        permission = root.findViewById(R.id.permission_setting);
        permission.setOnClickListener(v -> Helper.loadFragment(R.id.nav_host_fragment_activity_main, new PermissionFragment(), getFragmentManager()));
        return root;
    }
}