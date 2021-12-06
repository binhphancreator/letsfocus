package com.app.letsfocus.ui.bottomsheet;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.app.letsfocus.R;
import com.app.letsfocus.adapter.RepeatTypeAdapter;
import com.app.letsfocus.model.TypeRepeat;
import com.app.letsfocus.ui.fragment.add_timetable.AddTimeTableViewModel;

import java.util.ArrayList;
import java.util.List;

public class TypeRepeatBottomSheetFragment extends BottomSheetDialogFragment {
    private AddTimeTableViewModel addTimeTableViewModel;
    private RepeatTypeAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_type_repeat, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView typeRepeatListView = view.findViewById(R.id.typeRepeatListView);
        typeRepeatListView.setAdapter(adapter);
        typeRepeatListView.setOnItemClickListener(((adapterView, viewSelected, i, l) -> {
            addTimeTableViewModel.setTypeRepeatLiveData(i);
            adapter.notifyDataSetChanged();
            dismiss();
        }));
    }

    private void init()
    {
        addTimeTableViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(AddTimeTableViewModel.class);
        adapter = new RepeatTypeAdapter(getContext(), addTimeTableViewModel.getAllTypeRepeat());
    }
}
