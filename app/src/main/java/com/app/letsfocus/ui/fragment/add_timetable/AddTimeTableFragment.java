package com.app.letsfocus.ui.fragment.add_timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.letsfocus.R;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

public class AddTimeTableFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_timetable_add_task, container, false);
        Button report1_btn = (Button) view.findViewById(R.id.save_task_btn);
        report1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new TimeTableListFragment());
                fr.commit();
            }
        });
        return view;
    }
}
