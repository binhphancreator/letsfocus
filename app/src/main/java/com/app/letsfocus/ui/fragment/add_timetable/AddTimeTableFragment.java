package com.app.letsfocus.ui.fragment.add_timetable;

import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.core.Validation;
import com.app.letsfocus.model.TimeTable;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.bottomsheet.TypeRepeatBottomSheetFragment;
import com.app.letsfocus.ui.fragment.home.HomeFragment;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTimeTableFragment extends Fragment {
    private TextView repeatBtn, taskNameEdt, timeStartTaskTv, timeEndTaskTv;
    private Button saveTaskBtn;
    private AddTimeTableViewModel addTimeTableViewModel;
    View view;
    private int hourTemp, minuteTemp;
    private boolean isEdit;
    private TimeTable timeTableTemp;
    private int idTimeTableEdit, idTypeRepeat;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable_add_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindComponent();
        addTimeTableViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(AddTimeTableViewModel.class);
        registerEvent();
        try {
            getDataById();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindComponent()
    {
        repeatBtn = view.findViewById(R.id.repeat_btn);
        taskNameEdt = view.findViewById(R.id.taskNameEdt);
        timeStartTaskTv = view.findViewById(R.id.timeStartTaskTv);
        timeEndTaskTv = view.findViewById(R.id.timeEndTaskTv);
        saveTaskBtn = view.findViewById(R.id.saveTaskBtn);
    }


    private void registerEvent()
    {
        addTimeTableViewModel.getTypeRepeatLiveData().observe(getViewLifecycleOwner(), (typeRepeat) -> {
            repeatBtn.setText(typeRepeat.getName());
            idTypeRepeat = typeRepeat.getId();
        });
        repeatBtn.setOnClickListener(view -> {
            TypeRepeatBottomSheetFragment bottomSheetFragment = new TypeRepeatBottomSheetFragment();
            bottomSheetFragment.show(getFragmentManager(), "Choose Type Repeat");
        });
        saveTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = taskNameEdt.getText().toString();
                String time_start = timeStartTaskTv.getText().toString();
                String time_end = timeEndTaskTv.getText().toString();
                int repeat = idTypeRepeat;
                if(isEdit) {
                    if (Validation.require(name, time_start, time_end)) {
                        timeTableTemp.updateTimeTable(idTimeTableEdit,name,time_start,time_end,repeat);
                        Helper.loadFragment(R.id.nav_host_fragment_activity_main, new TimeTableListFragment(), getFragmentManager());
                    }
                    else {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", name);
                    contentValues.put("start_time", time_start);
                    contentValues.put("end_time", time_end);
                    contentValues.put("repeat", repeat);
                    if (Validation.require(name, time_start, time_end)) {
                        Model timetableModel = new TimeTable(getContext()).create(contentValues);
                        Helper.loadFragment(R.id.nav_host_fragment_activity_main, new TimeTableListFragment(), getFragmentManager());
                    }
                    else {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        setTimeEvent(timeStartTaskTv, true);
        setTimeEvent(timeEndTaskTv, true);
    }

    private void setTimeEvent(TextView textView, boolean isClock)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                hourTemp = hourOfDay;
                                minuteTemp = minute;
                                //Store hour and minute in string
                                String time = hourTemp + ":" + minuteTemp;
                                //Initialize 24 hours time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat("hh:mm");
                                if(isClock) {
                                    try {
                                        Date date = f24Hours.parse(time);
                                        //Initialize 12 hours time format
                                        SimpleDateFormat f12Hours = new SimpleDateFormat(
                                                "hh:mm aa"
                                        );
                                        //Set selected time on text view
                                        textView.setText(f12Hours.format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    textView.setText(String.format(Locale.getDefault(), "%02d:%02d", hourTemp, minuteTemp));
                                }
                            }
                        },12,0, !isClock
                );
                // Set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //  Displayed previous selected time
                timePickerDialog.updateTime(hourTemp, minuteTemp);
                //Show dialog
                timePickerDialog.show();
            }
        });
    }

    private void getDataById() {
        Bundle bundle = this.getArguments();
        String idTimeTableStr = bundle.getString("idTimeTable");
        idTimeTableEdit = Integer.parseInt(idTimeTableStr);
        timeTableTemp = new TimeTable(getContext());
        timeTableTemp = timeTableTemp.getTimeTableById(idTimeTableEdit);
        taskNameEdt.setText(timeTableTemp.get("name"));
        timeStartTaskTv.setText(timeTableTemp.get("start_time"));
        timeEndTaskTv.setText(timeTableTemp.get("end_time"));
        repeatBtn.setText(timeTableTemp.get("repeat"));
        addTimeTableViewModel.setTypeRepeatLiveData(Integer.parseInt(timeTableTemp.get("repeat")));
        isEdit=true;
    }

}
