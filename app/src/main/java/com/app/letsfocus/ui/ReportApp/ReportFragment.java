package com.app.letsfocus.ui.ReportApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.support.v4.app.Fragment;

import com.app.letsfocus.R;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    ExpandableListView exlistview;
    private ArrayList<groupItem> parent;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reportapp, container, false);

        parent = getdata();
        exlistview = root.findViewById(R.id.expandableListView);
        ReportAdapter adapt = new ReportAdapter(getActivity(), parent);
        exlistview.setAdapter(adapt);
        return root;
    }

    public ArrayList<groupItem> getdata() {

        groupItem new1 = new groupItem("Social media");

        new1.child.add(new childItem("Facebook", "40min"));
        new1.child.add(new childItem("Instagram", "30min"));

        ArrayList<groupItem> all_new = new ArrayList<groupItem>();
        all_new.add(new1);
        all_new.add(new groupItem("Gaming app"));
        all_new.add(new groupItem("Study app"));
        all_new.add(new groupItem("Health & Fitness"));
        all_new.add(new groupItem("Finance"));
        all_new.add(new groupItem("Books & Reference"));
        return all_new;
    }
}
