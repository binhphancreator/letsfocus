package com.app.letsfocus.ui.fragment.add_timetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.letsfocus.model.TypeRepeat;
import java.util.ArrayList;
import java.util.List;

public class AddTimeTableViewModel extends ViewModel {
    private final MutableLiveData<TypeRepeat> typeRepeat;
    private List<TypeRepeat> typeRepeatList;
    private int currentTypeActive = 0;

    public AddTimeTableViewModel()
    {
        typeRepeat = new MutableLiveData<>();
        typeRepeatList = new ArrayList<>();
        typeRepeatList.add(new TypeRepeat(0, "Once", true));
        typeRepeatList.add(new TypeRepeat(1, "Daily", false));
        typeRepeatList.add(new TypeRepeat(2, "Weekly", false));
        typeRepeatList.add(new TypeRepeat(3, "Monthly", false));
        typeRepeat.setValue(typeRepeatList.get(0));
    }

    public List<TypeRepeat> getAllTypeRepeat()
    {
        return typeRepeatList;
    }

    public LiveData<TypeRepeat> getTypeRepeatLiveData() {
        return typeRepeat;
    }

    public void setTypeRepeatLiveData(int i)
    {
        typeRepeatList.get(currentTypeActive).setActive(false);
        typeRepeatList.get(i).setActive(true);
        currentTypeActive = i;
        typeRepeat.setValue(typeRepeatList.get(i));
    }
}
