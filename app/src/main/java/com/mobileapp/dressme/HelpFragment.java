package com.mobileapp.dressme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //NOTHING IS DONE IN THIS FRAGMENT
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}