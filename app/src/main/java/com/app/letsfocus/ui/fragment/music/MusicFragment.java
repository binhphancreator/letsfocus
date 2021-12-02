package com.app.letsfocus.ui.fragment.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.letsfocus.databinding.FragmentMusicBinding;

public class MusicFragment extends Fragment {
    private View view;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMusicBinding binding = FragmentMusicBinding.inflate(inflater);
        view = binding.getRoot();
        return view;
    }
}
