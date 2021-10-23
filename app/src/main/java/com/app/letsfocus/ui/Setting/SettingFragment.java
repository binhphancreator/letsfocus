package com.app.letsfocus.ui.Setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.app.letsfocus.R;
import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {

    private ArrayList<SettingList> TextList;
    private ArrayList<SettingList> VersionList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);

        TextList = new ArrayList<SettingList>() {
            {
                add(new SettingList("Theme", "Auto (System Detail)"));
                add(new SettingList("Language", "Auto (System Detail)"));
            }
        };

        VersionList = new ArrayList<SettingList>(){
            {
                add(new SettingList("Current version", "2021.09.05"));
            }
        };

        final ListView lv1;
        final ListView lv2;
        lv1 = view.findViewById(R.id.setting_list);
        lv2 = view.findViewById(R.id.version_list);
        SettingAdapter setting = new SettingAdapter(getActivity(), TextList);
        lv1.setAdapter(setting);
        SettingAdapter version = new SettingAdapter(getActivity(), VersionList);
        lv2.setAdapter(version);

        return view;
    }

}

