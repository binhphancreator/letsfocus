package com.app.letsfocus.ui.fragment.add_timetable;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app.letsfocus.R;
import com.app.letsfocus.ui.bottomsheet.TypeRepeatBottomSheetFragment;

public class AddTimeTableFragment extends Fragment {
    private Button repeatBtn;
    private AddTimeTableViewModel addTimeTableViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repeatBtn = view.findViewById(R.id.repeat_btn);
        addTimeTableViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(AddTimeTableViewModel.class);
        registerEvent();
    }

    private void registerEvent()
    {
        addTimeTableViewModel.getTypeRepeatLiveData().observe(getViewLifecycleOwner(), (typeRepeat) -> {
            repeatBtn.setText(typeRepeat.getName());
        });
        repeatBtn.setOnClickListener(view -> {
            TypeRepeatBottomSheetFragment bottomSheetFragment = new TypeRepeatBottomSheetFragment();
            bottomSheetFragment.show(getFragmentManager(), "Choose Type Repeat");
        });
    }
}
