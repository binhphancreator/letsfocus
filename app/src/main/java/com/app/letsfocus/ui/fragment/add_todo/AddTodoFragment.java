package com.app.letsfocus.ui.fragment.add_todo;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.app.letsfocus.R;
import com.app.letsfocus.app.Helper;
import com.app.letsfocus.app.Model;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.home.HomeFragment;

public class AddTodoFragment extends Fragment {
    private EditText todoNameEt, todoTimeEt, todoDurationEt, todoDetailEt;
    private Button saveTodoBtn;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_todo, container, false);
        bindComponent();
        registerEvent();
        return view;
    }

    private void bindComponent()
    {
        saveTodoBtn = view.findViewById(R.id.save_add_todo);
        todoNameEt = view.findViewById(R.id.todoName);
        todoTimeEt = view.findViewById(R.id.todoTime);
        todoDurationEt = view.findViewById(R.id.todoDuration);
        todoDetailEt = view.findViewById(R.id.todoDetail);
    }

    private  void registerEvent()
    {
        saveTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", todoNameEt.getText().toString());
                contentValues.put("time", todoTimeEt.getText().toString());
                contentValues.put("duration", todoDurationEt.getText().toString());
                contentValues.put("detail", todoDetailEt.getText().toString());
                Model todoModel = new ToDo(getContext()).create(contentValues);
                Helper.loadFragment(R.id.nav_host_fragment_activity_main, new HomeFragment(), getFragmentManager());
            }
        });
    }
}