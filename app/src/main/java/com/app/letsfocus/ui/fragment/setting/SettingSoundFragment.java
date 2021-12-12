package com.app.letsfocus.ui.fragment.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.ReportAdapter;

import java.util.ArrayList;

public class SettingSoundFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_sound, container, false);
        RelativeLayout ringtone_btn = (RelativeLayout) view.findViewById(R.id.setting_ringtone);
        Switch switchSound = view.findViewById(R.id.switch_sound);
        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchSound.isChecked()){
                    ringtone_btn.setVisibility(View.VISIBLE);
                }
                else {
                    ringtone_btn.setVisibility(View.GONE);
                }
            }

        });
        Switch switchVibration = view.findViewById(R.id.switch_vibration);
//        switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(switchVibration.isChecked()){
//
//                }
//                else {
//
//                }
//            }
//
//        });
        return view;
    }

}