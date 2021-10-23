package com.app.letsfocus.ui.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.app.letsfocus.R;
import java.util.ArrayList;
import java.util.List;
public class ProfileFragment extends Fragment {

    private String[] profile = {"Permission", "Permitted apps", "Feedback",
            "Sound and Vibration", "Notification", "Sign out"};
    private ListView lv1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ArrayList<title> content = new ArrayList<title>() {
            {
                for (String s : profile){
                    add(new title(s));
                }
            }

        };

        lv1 = rootView.findViewById(R.id.profile_list);

        ProfileAdapter newone = new ProfileAdapter(getActivity(), content);
        lv1.setAdapter(newone);
        return rootView;
    }

}
