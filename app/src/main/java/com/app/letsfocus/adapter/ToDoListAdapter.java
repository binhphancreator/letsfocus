package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.letsfocus.R;

import java.util.List;

public class ToDoListAdapter extends BaseAdapter {
    private List<Object> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ToDoListAdapter(Context context, List<Object> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = layoutInflater.from(context).inflate(R.layout.card_todo_list, viewGroup,false);
            Object currentItem = getItem(i);
        }
        return view;
    }
}
