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
        Button report1_btn = (Button) view.findViewById(R.id.save_add_todo);
        EditText todoNameEt = (EditText) view.findViewById(R.id.todoName);
        EditText todoTimeEt = (EditText) view.findViewById(R.id.todoTime);
        EditText todoDurationEt = (EditText) view.findViewById(R.id.todoDuration);
        EditText todoDetailEt = (EditText) view.findViewById(R.id.todoDetail);

        // create database
        database = new Database(getContext(),"todo.sqlite",null,1);
        // create table
        database.QueryData("CREATE TABLE IF NOT EXISTS Todo(Id INTEGER PRIMARY KEY AUTOINCREMENT, todoName varchar(200), todoTime varchar(200), todoDuration varchar(200), todoDetail varchar(200) )");

        report1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
                todoName = todoNameEt.getText().toString();
                todoTime = todoTimeEt.getText().toString();
                todoDuration = todoDurationEt.getText().toString();
                todoDetail = todoDetailEt.getText().toString();
                if(!todoName.isEmpty() && !todoTime.isEmpty() && !todoDuration.isEmpty()) {
                    String sqltmp = SqlAddTodo(todoName,todoTime,todoDuration,todoDetail);
                    database.QueryData(sqltmp);
                    fr.commit();
                }
                else {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }
                getTodoData();
            }
        });
        return view;
    }
    public void saveTodo() {

    }

    public String SqlAddTodo(String todoName, String todoTime, String todoDuration, String todoDetail) {
        todoDetail = todoDetail.isEmpty() ? "Trống" : todoDetail;
        return "INSERT INTO Todo VALUES("
                + null + ", "
                + "'" + todoName + "', "
                + "'" + todoTime + "', "
                + "'" + todoDuration + "', "
                + "'" + todoDuration + "' )";
    }
    public  void getTodoData() {
        Cursor dataTest = database.GetData("SELECT * FROM Todo");
        while (dataTest.moveToNext()) {
            Integer isWorkInt = dataTest.getInt(3);
            Boolean isWork = false;
            if(isWorkInt == 1) {
                isWork = true;
            }
            todoName = dataTest.getString(1);
            todoTime = dataTest.getString(2);
            todoDuration = dataTest.getString(3);
            todoDetail = dataTest.getString(4);
            todo = new TodoModel(todoName, todoTime, todoDuration, todoDetail);
            Toast.makeText(getContext(), todo.getTodoName() + "\n"
                                        + todo.getTodoTime() + "\n"
                                        + todo.getTodoDuration() + "\n"
                                        + todo.getTodoDetail(), Toast.LENGTH_SHORT).show();
            //workArrayList.add(new Work(dataTest.getInt(0),dataTest.getString(1),dataTest.getString(2),isWork));

        }
    }
}