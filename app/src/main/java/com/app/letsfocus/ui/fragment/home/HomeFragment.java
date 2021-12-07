package com.app.letsfocus.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.app.letsfocus.ui.activity.FocusActivity;
import com.app.letsfocus.R;
import com.app.letsfocus.adapter.ToDoListAdapter;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.databinding.FragmentHomeBinding;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.add_todo.AddTodoFragment;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View root;
    private Button addToDoBtn;
    private ListView toDoList;
    private int isSelected;
    private List<Model> listData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindComponent(inflater, container);
        renderToDoList();
        registerEvent();
        return root;
    }

    private void bindComponent(@NonNull LayoutInflater inflater, ViewGroup container) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        addToDoBtn = root.findViewById(R.id.add_todo_btn);
    }

    private void renderToDoList() {
        toDoList = binding.toDoList;
        listData = new ToDo(getContext()).all();
        ToDoListAdapter adapter = new ToDoListAdapter(getActivity(), listData);
        toDoList.setAdapter(adapter);
    }

    private void registerEvent()
    {
        addToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.loadFragment(R.id.nav_host_fragment_activity_main, new AddTodoFragment(), getFragmentManager());
            }
        });

        toDoList.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(getActivity(), FocusActivity.class);
            intent.putExtra("todoId", String.valueOf(id));
            startActivity(intent);
        });

        toDoList.setOnItemLongClickListener((adapterView, view, position, id) -> {
            isSelected = position;
            return false;
        });

        registerForContextMenu(toDoList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getActivity().getMenuInflater().inflate(R.menu.todo_context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete) {
            ToDo toDo = new ToDo(getContext());
            toDo.delete(Integer.parseInt(listData.get(isSelected).get("id")));
            renderToDoList();
        }
        else if(item.getItemId() == R.id.update) {
            Bundle bundle = new Bundle();
            bundle.putString("idTodo",listData.get(isSelected).get("id"));
            AddTodoFragment addTodoFragment = new AddTodoFragment();
            addTodoFragment.setArguments(bundle);
            Helper.loadFragment(R.id.nav_host_fragment_activity_main, addTodoFragment, getFragmentManager());
        }
        return super.onContextItemSelected(item);
    }
}