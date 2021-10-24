package com.app.letsfocus.ui.report1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.letsfocus.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Report1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Report1Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report1, container, false);
    }
}