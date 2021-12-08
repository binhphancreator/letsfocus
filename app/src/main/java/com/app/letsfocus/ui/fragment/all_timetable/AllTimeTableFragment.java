package com.app.letsfocus.ui.fragment.all_timetable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.TimeTableAdapter;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.model.TimeTable;
import com.app.letsfocus.ui.fragment.add_timetable.AddTimeTableFragment;

import java.util.List;

public class AllTimeTableFragment extends Fragment {
    View view;
    Button addBtn;
    ListView allTaskLv;
    private List<Model> listData;
    private int isSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable_alltask, container, false);
        allTaskLv = view.findViewById(R.id.allTaskLv);
        addBtn = view.findViewById(R.id.add_btn);
        renderTimeTableList();
        registerEvent();
        return view;
    }

    private void renderTimeTableList() {
        listData = new TimeTable(getContext()).all();
        TimeTableAdapter adapter = new TimeTableAdapter(getActivity(),listData);
        allTaskLv.setAdapter(adapter);
    }

    private void registerEvent()
    {
        addBtn.setOnClickListener(v -> {
            Helper.loadFragment(R.id.nav_host_fragment_activity_main, new AddTimeTableFragment(), getFragmentManager());
        });
        allTaskLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                isSelected = position;
                return false;
            }
        });
        registerForContextMenu(allTaskLv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getActivity().getMenuInflater().inflate(R.menu.todo_context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete) {
            TimeTable timeTable = new TimeTable(getContext());
            timeTable.delete(Integer.parseInt(listData.get(isSelected).get("id")));
            renderTimeTableList();
        }
        else if(item.getItemId() == R.id.update) {
            Bundle bundle = new Bundle();
            bundle.putString("idTimeTable",listData.get(isSelected).get("id"));
            AddTimeTableFragment addTimeTableFragment = new AddTimeTableFragment();
            addTimeTableFragment.setArguments(bundle);
            Helper.loadFragment(R.id.nav_host_fragment_activity_main, addTimeTableFragment, getFragmentManager());
        }
        return super.onContextItemSelected(item);
    }
}
