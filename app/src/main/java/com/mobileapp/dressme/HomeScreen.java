package com.mobileapp.dressme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeScreen extends Fragment {

    private HomeScreenViewModel mViewModel;

    public static HomeScreen newInstance() {
        return new HomeScreen();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen,
                container, false);
        Button dressBtn = view.findViewById(R.id.dressMeButton);
        System.out.println("here");
        dressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_dressMe2);
            }
        });
        /*
        Button uploadBtn = view.findViewById(R.id.uploadButton);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_upload2);
            }
        }); */

        return view;
    }
    }

