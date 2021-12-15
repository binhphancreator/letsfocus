package com.app.letsfocus.ui.fragment.add_todo;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.core.RemiderBroadcast;
import com.app.letsfocus.core.Validation;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.activity.MainActivity;
import com.app.letsfocus.ui.fragment.home.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class AddTodoFragment extends Fragment {
    private EditText todoNameEt, todoDetailEt;
    private TextView todoTimeTv, todoDurationTv;
    private Button saveTodoBtn;
    private View view;
    private int hourTemp, minuteTemp;
    private boolean isEdit;
    private ToDo toDoTemp;
    private int idTodoEdit;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_todo, container, false);
        bindComponent();
        setTimeEvent(todoTimeTv,false);
        setTimeEvent(todoDurationTv, false);
        try {
            getDataById();
        } catch (Exception e) {
            e.printStackTrace();
        }
        registerEvent();
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
        if(isEdit) {
            saveTodoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = todoNameEt.getText().toString();
                    String time = todoTimeTv.getText().toString();
                    String duration = todoDurationTv.getText().toString();
                    String detail = todoDetailEt.getText().toString();
                    if (Validation.require(name, time, duration)) {
                        toDoTemp.updateTodo(idTodoEdit,name,time,duration,detail);
                        Helper.loadFragment(R.id.nav_host_fragment_activity_main, new HomeFragment(), getFragmentManager());
                        configAlert(time);
                    }
                    else {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            saveTodoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = todoNameEt.getText().toString();
                    String time = todoTimeTv.getText().toString();
                    String duration = todoDurationTv.getText().toString();
                    String detail = todoDetailEt.getText().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", name);
                    contentValues.put("time", time);
                    contentValues.put("duration", duration);
                    contentValues.put("detail", detail);
                    if (Validation.require(name, time, duration)) {
                        Model todoModel = new ToDo(getContext()).create(contentValues);
                        Helper.loadFragment(R.id.nav_host_fragment_activity_main, new HomeFragment(), getFragmentManager());
                        configAlert(time);
                    }
                    else {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void configAlert(String time){
            int hour, minute;
            String[] str = time.split(":");
            hour = Integer.parseInt(str[0]);
            minute = Integer.parseInt(str[1]);
            startAlert(hour,minute);
    }
    private void startAlert(int h , int m) {
        intent = new Intent(getActivity(), RemiderBroadcast.class);
        intent.setAction("un");
        pendingIntent = PendingIntent.getBroadcast(getActivity(),code(),intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND,0);

        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        if(alarmManager!= null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private int code(){
        int random = new Random().nextInt(34)+1;
        return random;
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
        String idTodo = bundle.getString("idTodo");
        idTodoEdit = Integer.parseInt(idTodo);
        toDoTemp = new ToDo(getContext());
        toDoTemp = toDoTemp.getTodoById(Integer.parseInt(idTodo));
        todoNameEt.setText(toDoTemp.get("name"));
        todoTimeTv.setText(toDoTemp.get("time"));
        todoDurationTv.setText(toDoTemp.get("duration"));
        todoDetailEt.setText(toDoTemp.get("detail"));
        isEdit=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
    }
}
