package com.mobileapp.dressme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeScreen extends Fragment {


    public static HomeScreen newInstance() {
        return new HomeScreen();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen,
                container, false);
        //creating the animation for the dress me button
        Animation pulseAnimation = new ScaleAnimation(
                1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
                //setting up the animation to pulse with these defined scales
        );
        pulseAnimation.setDuration(1000);
        //setting the duration to 1000
        pulseAnimation.setRepeatMode(Animation.REVERSE);
        pulseAnimation.setRepeatCount(Animation.INFINITE);
        //setting the animation to repeat infinitely as long as the app is running

        Button dressBtn = view.findViewById(R.id.dressMeButton);
        dressBtn.startAnimation(pulseAnimation);
        //getting access to the dressme buttin and beginning the animation process
        System.out.println("here");
        dressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_dressMe2);
                //adding navigation to the dress me button to go to the dress me fragment
            }
        });
        /*
        Button uploadBtn = view.findViewById(R.id.uploadButton);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_upload2);
            }
        });*/

        return view;
    }
    }

