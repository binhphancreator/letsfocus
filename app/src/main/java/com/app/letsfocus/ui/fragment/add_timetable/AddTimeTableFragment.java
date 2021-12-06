package com.app.letsfocus.ui.fragment.add_timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.RepeatTypeAdapter;
import com.app.letsfocus.model.TypeRepeat;
import com.app.letsfocus.ui.bottomsheet.TypeRepeatBottomSheetFragment;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

import java.util.ArrayList;
import java.util.List;

public class AddTimeTableFragment extends Fragment {
    private View view;
    private Button repeatBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_timetable_add_task, container, false);
        bindComponent();
        registerEvent();
        return view;
    }

    private void bindComponent()
    {
        repeatBtn = view.findViewById(R.id.repeat_btn);
    }

    private void registerEvent()
    {
        repeatBtn.setOnClickListener((v -> {
            TypeRepeatBottomSheetFragment bottomSheetFragment = new TypeRepeatBottomSheetFragment();
            bottomSheetFragment.show(getFragmentManager(), "Choose Type Repeat");
        }));
    }
}
