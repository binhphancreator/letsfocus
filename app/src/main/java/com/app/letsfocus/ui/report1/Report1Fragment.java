package com.app.letsfocus.ui.report1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.letsfocus.R;
import com.app.letsfocus.ui.add_timetable.AddTimeTableFragment;
import com.app.letsfocus.ui.report2.Report2Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Report1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Report1Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report1, container, false);

        Button report1_btn = (Button) view.findViewById(R.id.app_usage_btn);
        report1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new Report2Fragment());
                fr.commit();
            }
        });

        return view;
    }
}