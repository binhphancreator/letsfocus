package com.app.letsfocus.ui.fragment.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.app.letsfocus.R;

public class SettingFragment extends Fragment {

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("switch_state", Context.MODE_PRIVATE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_setting_new, container, false);
    SwitchCompat switchVisibility = view.findViewById(R.id.switch_notify);


    switchVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putBoolean("state",b);
                editor.commit();
        }
    });
    switchVisibility.setChecked(sharedPreferences.getBoolean("state",true));
    RelativeLayout sound_btn = (RelativeLayout) view.findViewById(R.id.setting_sound);
    sound_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.nav_host_fragment_activity_main, new SettingSoundFragment());
            fr.commit();
        }
    });
    RelativeLayout permission_btn = (RelativeLayout) view.findViewById(R.id.setting_permission);
    permission_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.nav_host_fragment_activity_main, new SettingPresimisson());
            fr.commit();
        }
    });

    return view;
}
}
