package com.app.letsfocus.ui.add_todo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.letsfocus.Database;
import com.app.letsfocus.R;
import com.app.letsfocus.ui.home.HomeFragment;

public class AddTodoFragment extends Fragment {
    EditText todoNameEt, todoTimeEt, todoDurationEt, todoDetailEt;
    TodoModel todo;
    String todoName, todoTime, todoDuration, todoDetail;
    Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_todo, container, false);
        Button saveTodoBtn = (Button) view.findViewById(R.id.save_add_todo);
        EditText todoNameEt = (EditText) view.findViewById(R.id.todoName);
        EditText todoTimeEt = (EditText) view.findViewById(R.id.todoTime);
        EditText todoDurationEt = (EditText) view.findViewById(R.id.todoDuration);
        EditText todoDetailEt = (EditText) view.findViewById(R.id.todoDetail);

        // create database
        database = new Database(getContext(),"todo.sqlite",null,1);
        // create table
        database.QueryData("CREATE TABLE IF NOT EXISTS Todo(Id INTEGER PRIMARY KEY AUTOINCREMENT, todoName varchar(200), todoTime varchar(200), todoDuration varchar(200), todoDetail varchar(200) )");

        saveTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
                todoName = todoNameEt.getText().toString();
                todoTime = todoTimeEt.getText().toString();
                todoDuration = todoDurationEt.getText().toString();
                todoDetail = todoDetailEt.getText().toString();
                if(checkTodo()) {
                    database.QueryData(SqlAddTodo(todoName,todoTime,todoDuration,todoDetail));
                    fr.commit();
                }
            }
        });
        return view;
    }

    public String SqlAddTodo(String todoName, String todoTime, String todoDuration, String todoDetail) {
        todoDetail = todoDetail.isEmpty() ? "Trống" : todoDetail;
        return "INSERT INTO Todo VALUES("
                + null + ", "
                + "'" + todoName + "', "
                + "'" + todoTime + "', "
                + "'" + todoDuration + "', "
                + "'" + todoDetail + "' )";
    }

    public boolean checkTodo() {
        if(todoName.isEmpty() || todoTime.isEmpty() || todoDuration.isEmpty()) {
            Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(todoName.length() >= 200 || todoDetail.length() >= 200) {
            Toast.makeText(getContext(), "Độ dài tên và mô tả tối đa 255 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!checkTime()) {
            return false;
        }
        if(Integer.parseInt(todoDuration) > 1000) {
            Toast.makeText(getContext(), "Khoảng thời gian tối đa là 1000", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    public boolean checkTime() {
        String[] timeTemp;
        int hours, minutes;
        if(todoTime.contains(":")) {
            timeTemp = todoTime.split(":");
            hours = Integer.parseInt(timeTemp[0]);
            minutes = Integer.parseInt(timeTemp[1]);
        }
        else {
            Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(todoTime.length() > 5) {
            Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(hours > 23 || minutes > 59) {
            Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}