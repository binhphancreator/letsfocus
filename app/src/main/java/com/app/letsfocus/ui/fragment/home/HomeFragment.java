package com.app.letsfocus.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.app.letsfocus.ui.activity.FocusActivity;
import com.app.letsfocus.R;
import com.app.letsfocus.adapter.ToDoListAdapter;
import com.app.letsfocus.app.Helper;
import com.app.letsfocus.app.Model;
import com.app.letsfocus.databinding.FragmentHomeBinding;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.add_todo.AddTodoFragment;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View root;
    private Button addToDoBtn;
    private ListView toDoList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindComponent(inflater, container);
        renderToDoList();
        registerEvent();
        return root;
    }

    private void bindComponent(@NonNull LayoutInflater inflater, ViewGroup container) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        addToDoBtn = (Button) root.findViewById(R.id.add_todo_btn);
    }

    private void renderToDoList() {
        toDoList = binding.toDoList;
        List<Model> listData = new ToDo(getContext()).all();
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

        toDoList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), FocusActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}