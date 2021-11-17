package com.app.letsfocus.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.letsfocus.Database;
import com.app.letsfocus.R;
import com.app.letsfocus.adapter.ToDoListAdapter;
import com.app.letsfocus.databinding.FragmentHomeBinding;
import com.app.letsfocus.ui.add_todo.AddTodoFragment;
import com.app.letsfocus.ui.add_todo.TodoModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ArrayList<TodoModel> todoModelArrayList;
    ToDoListAdapter adapter;
    ListView lv;
    Database database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lv = (ListView) root.findViewById(R.id.toDoList) ;
        todoModelArrayList = new ArrayList<>();
        database = new Database(getContext(),"todo.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Todo(Id INTEGER PRIMARY KEY AUTOINCREMENT, todoName varchar(200), todoTime varchar(200), todoDuration varchar(200), todoDetail varchar(200) )");

        getTodoData();
        adapter = new ToDoListAdapter(getContext(),R.layout.card_todo_list,todoModelArrayList);
        lv.setAdapter(adapter);

        Button report1_btn = (Button) root.findViewById(R.id.add_todo_btn);
        report1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new AddTodoFragment());
                fr.commit();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                database.QueryData(SqlDeleteWork(todoModelArrayList.get(position).getTodoId()));
                todoModelArrayList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public  void getTodoData() {
        Cursor dataTest = database.GetData("SELECT * FROM Todo");
        while (dataTest.moveToNext()) {
            Integer isWorkInt = dataTest.getInt(3);
            Boolean isWork = false;
            if(isWorkInt == 1) {
                isWork = true;
            }
            todoModelArrayList.add(new TodoModel(dataTest.getInt(0),dataTest.getString(1),dataTest.getString(2),dataTest.getString(3),dataTest.getString(4)));
        }
    }
    public String SqlDeleteWork(Integer id) {
        return "DELETE FROM Todo WHERE ID = " + id;
    }
}