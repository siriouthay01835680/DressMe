package com.mobileapp.dressme;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

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

        View popUpView = inflater.inflate(R.layout.mainpopup,null);
//        View viewPopUp = inflater.inflate(R.layout.mainpopup,
//                container, false);
        Button drawBtn = popUpView.findViewById(R.id.popUpDB);
        Button scrapBtn = popUpView.findViewById(R.id.popUpSB);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);
        Button donateBtn = popUpView.findViewById(R.id.popUpDonate);
        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                Navigation.findNavController(view).navigate(R.id.action_closet_to_drawingBoard);
            }
        });
        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                Navigation.findNavController(view).navigate(R.id.action_closet_to_scrapbook);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testShirt1.setVisibility(View.GONE);
            }
        });
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testShirt1.setVisibility(View.GONE);
            }
        });

        testShirt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("click");
//                testShirt1.setVisibility(View.GONE);
//                testShirt1.setImageDrawable(null);
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
                view.post(new Runnable() {

                    @Override
                    public void run() {
                        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                    }
                });

            }
        });
//        Button drawBrdBtn = view.findViewById(R.id.drawBrdBtn);
//        drawBrdBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_closet_to_drawingBoard);
//            }
//        });
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

    private void createPopUp() {
    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ImageView testShirt1 = view.findViewById(R.id.shirt1);
//        testShirt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                System.out.println("click");
////                testShirt1.setVisibility(View.GONE);
////                testShirt1.setImageDrawable(null);
//
//
//            }
//        });
//    }
}