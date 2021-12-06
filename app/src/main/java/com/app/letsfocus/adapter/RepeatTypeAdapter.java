package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.letsfocus.R;
import com.app.letsfocus.model.TypeRepeat;
import java.util.List;

public class RepeatTypeAdapter extends BaseAdapter {
    private List<TypeRepeat> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public RepeatTypeAdapter(Context context, List<TypeRepeat> listData)
    {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount()
    {
        return listData.size();
    }

    @Override
    public Object getItem(int i)
    {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.card_type_repeat, viewGroup,false);
            TextView typeRepeatTextView = view.findViewById(R.id.typeRepeatTextView);
            ImageView checkedIcon = view.findViewById(R.id.checkedIcon);
            TypeRepeat type = listData.get(i);
            typeRepeatTextView.setText(type.getName());
            if(type.isActive()) {
                checkedIcon.setImageResource(R.drawable.ic_check_lg);
                view.setBackgroundResource(R.color.blue_100);
            }
        }
        return view;
    }
}
