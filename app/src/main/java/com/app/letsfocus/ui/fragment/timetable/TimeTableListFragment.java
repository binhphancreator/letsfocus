package com.app.letsfocus.ui.fragment.timetable;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.TimeTableAdapter;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.model.TimeTable;
import com.app.letsfocus.ui.fragment.add_timetable.AddTimeTableFragment;
import com.app.letsfocus.ui.fragment.all_timetable.AllTimeTableFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeTableListFragment extends Fragment {
    private View view;
    private Button addTimetableBtn;
    private TextView todayTimeTextView, seeAllTv;
    private ImageView calendar;
    private ListView timetableLv;
    private List<Model> listData;
    private int isSelected;
    TimeTableAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable_list, container, false);
        bindComponent();
        renderView();
        registerEvent();
        return view;
    }

    private void bindComponent()
    {
        addTimetableBtn = view.findViewById(R.id.add_timetable_btn);
        todayTimeTextView = view.findViewById(R.id.timeTaskItemTv);
        calendar = view.findViewById(R.id.calendarBtn);
        timetableLv = view.findViewById(R.id.timetableLv);
        seeAllTv = view.findViewById(R.id.seeAllTimeTableTv);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void renderView()
    {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        todayTimeTextView.setText(new SimpleDateFormat("EEEE, MM, yyyy").format(currentTime));
        renderTimeTableList(dateFormat.format(currentTime));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void renderTimeTableList(String date) {
        listData = new TimeTable(getContext()).getTimeTableByDate(date);
        adapter = new TimeTableAdapter(getActivity(),listData);
        timetableLv.setAdapter(adapter);
    }

    private void registerEvent()
    {
        seeAllTv.setOnClickListener(v -> {
            Helper.loadFragment(R.id.nav_host_fragment_activity_main, new AllTimeTableFragment(), getFragmentManager());
        });
        addTimetableBtn.setOnClickListener(v -> {
            Helper.loadFragment(R.id.nav_host_fragment_activity_main, new AddTimeTableFragment(), getFragmentManager());
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        timetableLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                isSelected = position;
                return false;
            }
        });
        registerForContextMenu(timetableLv);
    }

    private void showDateDialog() {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                todayTimeTextView.setText(new SimpleDateFormat("EEEE, MM, yyyy").format(calendar.getTime()));
                renderTimeTableList(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(getContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getActivity().getMenuInflater().inflate(R.menu.todo_context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete) {
            TimeTable timeTable = new TimeTable(getContext());
            timeTable.delete(Integer.parseInt(listData.get(isSelected).get("id")));
            listData.remove(isSelected);
            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
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
