package com.app.letsfocus.ui.fragment.add_timetable;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.lifecycle.ViewModelProvider;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.core.Validation;
import com.app.letsfocus.model.TimeTable;
import com.app.letsfocus.ui.bottomsheet.TypeRepeatBottomSheetFragment;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AddTimeTableFragment extends Fragment {
    private TextView repeatBtn, taskNameEdt, timeStartTaskTv, timeEndTaskTv, chooseDays, clearDayTv;
    private Button saveTaskBtn;
    private AddTimeTableViewModel addTimeTableViewModel;
    View view;
    private int hourTemp, minuteTemp;
    private boolean isEdit;
    private TimeTable timeTableTemp;
    private int idTimeTableEdit, idTypeRepeat;
    private boolean[] selectedDay;
    ArrayList<Integer> dayList = new ArrayList<>();
    String[] dayArray = {"Monday", "Tuesday", "Wednesday", "Thursday"
            , "Friday", "Saturday", "Sunday"};
    ArrayList<Integer> dayOfMonthList = new ArrayList<>();
    String dayOfMonthStr = "";
    String chooseDaysTmp= "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable_add_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindComponent();
        addTimeTableViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(AddTimeTableViewModel.class);
        selectedDay = new boolean[dayArray.length];
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
        chooseDays = view.findViewById(R.id.chooseDaysTv);
        clearDayTv = view.findViewById(R.id.clearDayTv);
    }


    private void registerEvent()
    {
        clearDayTv.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Clear!", Toast.LENGTH_SHORT).show();
            chooseDays.setText("");
            dayOfMonthList.clear();
        });
        addTimeTableViewModel.getTypeRepeatLiveData().observe(getViewLifecycleOwner(), (typeRepeat) -> {
            repeatBtn.setText(typeRepeat.getName());
            idTypeRepeat = typeRepeat.getId();
            selectTypeRepeatEvent(idTypeRepeat);
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
                String date = chooseDays.getText().toString();
                if(isEdit) {
                    if (Validation.require(name, time_start, time_end)) {
                        timeTableTemp.updateTimeTable(idTimeTableEdit,name,time_start,time_end,repeat,date);
                        Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
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
                    contentValues.put("date",date);
                    if (Validation.require(name, time_start, time_end)) {
                        Model timetableModel = new TimeTable(getContext()).create(contentValues);
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Helper.loadFragment(R.id.nav_host_fragment_activity_main, new TimeTableListFragment(), getFragmentManager());
                    }
                    else {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        setTimeEvent(timeStartTaskTv, false);
        setTimeEvent(timeEndTaskTv, false);
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

    private void selectTypeRepeatEvent(int idTypeRepeat) {
        Log.d("Day", "selectTypeRepeatEvent");
        chooseDays.setText("");
        if(idTypeRepeat == 2) {
            chooseDays.setVisibility(View.VISIBLE);
            clearDayTv.setVisibility(View.INVISIBLE);
            setChooseDaysOfWeek();
        }
        else if(idTypeRepeat == 0) {
            chooseDays.setVisibility(View.VISIBLE);
            clearDayTv.setVisibility(View.INVISIBLE);
            chooseDays.setOnClickListener(v -> {
                showDateDialog(true);
            });
        }
        else if(idTypeRepeat == 3) {
            chooseDays.setVisibility(View.VISIBLE);
            clearDayTv.setVisibility(View.VISIBLE);
            chooseDays.setOnClickListener(v -> {
                showDateDialog(false);
            });
        }
        else {
            chooseDays.setVisibility(View.INVISIBLE);
            clearDayTv.setVisibility(View.INVISIBLE);
        }
    }
    private void showDateDialog(boolean isOnce) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                if(isOnce) {
                    chooseDays.setText(simpleDateFormat.format(calendar.getTime()));
                }
                else {
                    if(!dayOfMonthList.contains(dayOfMonth)) {
                        dayOfMonthList.add(dayOfMonth);
                    }
                    Collections.sort(dayOfMonthList);
                    dayOfMonthStr = "";
                    for (int i = 0; i < dayOfMonthList.size(); i++) {
                        String dayTmp;
                        if(dayOfMonthList.get(i) < 10) {
                            dayTmp = "0" + dayOfMonthList.get(i);
                        }
                        else {
                            dayTmp = dayOfMonthList.get(i) + "";
                        }
                        if(i < dayOfMonthList.size()-1) {
                            dayOfMonthStr += dayTmp +", ";
                        }
                        else {
                            dayOfMonthStr += dayTmp +"";
                        }
                    }
                    chooseDays.setText(dayOfMonthStr);
                }
            }
        };
        new DatePickerDialog(getContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setChooseDaysOfWeek() {
        chooseDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getContext()
                );
                builder.setTitle("Select Day");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(dayArray, selectedDay, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) {
                            //When checkbox selected add positon into dayList
                            dayList.add(which);
                            Collections.sort(dayList);
                        }
                        else {
                            //Remove day == which in dayList
                            for(int i=0; i< dayList.size(); i++){
                                if(dayList.get(i) == which){
                                    dayList.remove(i);
                                    break;
                                }
                            }
                        }
                        String dayTmp = "";
                        for (int day:dayList) {
                            dayTmp += day + " ";
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j=0; j<dayList.size(); j++){
                            stringBuilder.append(dayArray[dayList.get(j)]);
                            if(j != dayList.size()-1) {
                                stringBuilder.append(", ");

                            }
                        }
                        chooseDays.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j=0; j<selectedDay.length; j++) {
                            selectedDay[j] = false;
                            dayList.clear();
                            chooseDays.setText("");
                        }

                    }
                });
                builder.show();
            }
        });
    }
}
