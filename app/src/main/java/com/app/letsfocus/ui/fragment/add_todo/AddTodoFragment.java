package com.app.letsfocus.ui.fragment.add_todo;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.app.Helper;
import com.app.letsfocus.app.Model;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.activity.MainActivity;
import com.app.letsfocus.ui.fragment.home.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTodoFragment extends Fragment {
    private EditText todoNameEt, todoDetailEt;
    private TextView todoTimeTv, todoDurationTv;
    private Button saveTodoBtn;
    private View view;
    private int timeStartHour, timeStartMinute, timeDurationHour, timeDurationMinute;
    private int hourTemp, minuteTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_todo, container, false);
        bindComponent();
        registerEvent();
        setTimeEvent(todoTimeTv,true);
        setTimeEvent(todoDurationTv, false);
        return view;
    }

    private void bindComponent()
    {
        saveTodoBtn = view.findViewById(R.id.save_add_todo);
        todoNameEt = view.findViewById(R.id.todoName);
        todoTimeTv = view.findViewById(R.id.todoTime);
        todoDurationTv = view.findViewById(R.id.todoDuration);
        todoDetailEt = view.findViewById(R.id.todoDetail);
    }

    private  void registerEvent()
    {
        saveTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", todoNameEt.getText().toString());
                contentValues.put("time", todoTimeTv.getText().toString());
                contentValues.put("duration", todoDurationTv.getText().toString());
                contentValues.put("detail", todoDetailEt.getText().toString());
                Model todoModel = new ToDo(getContext()).create(contentValues);
                Helper.loadFragment(R.id.nav_host_fragment_activity_main, new HomeFragment(), getFragmentManager());
            }
        });
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
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "hh:mm"
                                );
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
//                                    try {
//                                        Date date = f24Hours.parse(time);
//                                        Toast.makeText(getContext(), f24Hours.format(date), Toast.LENGTH_SHORT).show();
//                                        String timeTemp = f24Hours.format(date);
//                                        textView.setText(timeTemp);
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    textView.setText(time);
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
}