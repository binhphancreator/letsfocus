package com.app.letsfocus.ui.report2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.ReportAdapter;

import java.util.ArrayList;

public class Report2Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_report2, container, false);
        ExpandableListView expandableListView = root.findViewById(R.id.expandableListView);
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(new Object());
        expandableListView.setAdapter(new ReportAdapter(getActivity(), arrayList));
        return root;
    }
}