package com.app.letsfocus.ui.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.ProfileAdapter;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    View root;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listView = root.findViewById(R.id.profile_list);
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(new Object());
        arrayList.add(new Object());
        arrayList.add(new Object());

        listView.setAdapter(new ProfileAdapter(getActivity(), arrayList));
        return root;
    }
}