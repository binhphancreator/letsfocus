package com.app.letsfocus.ui.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.app.letsfocus.R;
import com.app.letsfocus.adapter.RepeatTypeAdapter;
import com.app.letsfocus.model.TypeRepeat;
import java.util.ArrayList;
import java.util.List;

public class TypeRepeatBottomSheetFragment extends BottomSheetDialogFragment {
    private List<TypeRepeat> typeRepeatList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_type_repeat, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeRepeatList.add(new TypeRepeat(0, "Once", true));
        typeRepeatList.add(new TypeRepeat(1, "Daily", false));
        typeRepeatList.add(new TypeRepeat(2, "Weekly", false));
        typeRepeatList.add(new TypeRepeat(3, "Monthly", false));
        ListView typeRepeatListView = view.findViewById(R.id.typeRepeatListView);
        typeRepeatListView.setAdapter(new RepeatTypeAdapter(getContext(), typeRepeatList));
    }
}
