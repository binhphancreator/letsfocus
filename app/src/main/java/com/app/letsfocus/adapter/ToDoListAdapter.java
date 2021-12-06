package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.app.letsfocus.R;
import com.app.letsfocus.core.Model;
import java.util.List;

public class ToDoListAdapter extends BaseAdapter {
    private List<Model> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ToDoListAdapter(Context context, List<Model> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Model getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(listData.get(i).get("id"));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.card_todo_list, viewGroup,false);
            Model toDoModel = getItem(i);
            TextView txtTodoName = view.findViewById(R.id.todoName);
            TextView txtTodoTime = (TextView) view.findViewById(R.id.todoTime);

            txtTodoName.setText(toDoModel.get("name"));
            txtTodoTime.setText(toDoModel.get("time"));
        }
        return view;
    }
}
