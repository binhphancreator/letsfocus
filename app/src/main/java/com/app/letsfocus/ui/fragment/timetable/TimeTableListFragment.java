package com.app.letsfocus.ui.fragment.timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.app.Helper;
import com.app.letsfocus.ui.fragment.add_timetable.AddTimeTableFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTableListFragment extends Fragment {
    private View view;
    private Button addTimetableBtn;
    private TextView todayTimeTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable_list, container, false);
        bindComponent();
        renderView();
        registerEvent();
        return view;
    }

    private void bindComponent()
    {
        addTimetableBtn = view.findViewById(R.id.add_timetable_btn);
        todayTimeTextView = view.findViewById(R.id.todayTimeTextView);
    }

    private void renderView()
    {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        todayTimeTextView.setText(dateFormat.format(currentTime));
    }

    private void registerEvent()
    {
        addTimetableBtn.setOnClickListener(v -> {
            Helper.loadFragment(R.id.nav_host_fragment_activity_main, new AddTimeTableFragment(), getFragmentManager());
        });
    }
}
