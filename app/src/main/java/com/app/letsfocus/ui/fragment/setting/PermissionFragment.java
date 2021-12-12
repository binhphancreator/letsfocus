package com.app.letsfocus.ui.fragment.setting;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.letsfocus.R;

public class PermissionFragment extends Fragment {

    TextView app_usage, notifi;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_setting_permission, container, false);

        app_usage = root.findViewById(R.id.app_usage_title);
        notifi = root.findViewById(R.id.notifi_title);

        app_usage.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
        notifi.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)));
        return root;
    }
}
