package com.mobileapp.dressme;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Closet extends Fragment {

    private ClosetViewModel mViewModel;

    public static Closet newInstance() {
        return new Closet();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet,
                container, false);
        ImageView testShirt1 = view.findViewById(R.id.shirt1);
        testShirt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("click");
                testShirt1.setVisibility(View.GONE);
//                testShirt1.setImageDrawable(null);
            }
        });
        Button drawBrdBtn = view.findViewById(R.id.drawBrdBtn);
        drawBrdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_closet_to_drawingBoard);
            }
        });
//        Button dressBtn = view.findViewById(R.id.dressMeButton);
//        System.out.println("here");
//        dressBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_dressMe2);
//            }
//        });
//        Button uploadBtn = view.findViewById(R.id.uploadButton);
//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_upload2);
//            }
//        });

        return view;
    }

}