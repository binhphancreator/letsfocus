package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.ui.add_todo.TodoModel;

import java.util.List;

public class ToDoListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TodoModel> todoModelList;

    public ToDoListAdapter(Context context, int layout, List<TodoModel> todoModelList) {
        this.context = context;
        this.layout = layout;
        this.todoModelList = todoModelList;
    }

    @Override
    public int getCount() {
        return todoModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        TextView txtTodoName = (TextView) convertView.findViewById(R.id.todoName);
        TextView txtTodoTime = (TextView) convertView.findViewById(R.id.todoTime);

        TodoModel todo = todoModelList.get(position);

        txtTodoName.setText(todo.getTodoName());
        txtTodoTime.setText(todo.getTodoTime());

        return convertView;
    }
}
