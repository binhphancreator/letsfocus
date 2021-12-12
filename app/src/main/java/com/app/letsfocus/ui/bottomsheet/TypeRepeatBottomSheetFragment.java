package com.app.letsfocus.ui.bottomsheet;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.app.letsfocus.R;
import com.app.letsfocus.adapter.RepeatTypeAdapter;
import com.app.letsfocus.ui.fragment.add_timetable.AddTimeTableViewModel;

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
