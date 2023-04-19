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

import java.util.Arrays;

public class Closet extends Fragment {

    private ClosetViewModel mViewModel;
    String[] shirts = {};
    String[] pants = {};
    ClosetDirections.ActionClosetToDrawingBoard actionDB;
    Boolean isShirtClicked = false;
    Boolean isPantClicked = false;
    public static Closet newInstance() {
        return new Closet();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        if(savedInstanceState != null){
//            savedInstanceState.getStringArray("shirts");
//            savedInstanceState.getStringArray("pants");
//            shirts = shirts;
//            pants = pants;
//            System.out.println("restored");
//        }
        View view = inflater.inflate(R.layout.fragment_closet,
                container, false);
        ImageView testShirt1 = view.findViewById(R.id.shirt1);

        View popUpView = inflater.inflate(R.layout.mainpopup,null);
//        View viewPopUp = inflater.inflate(R.layout.mainpopup,
//                container, false);
        Button drawBtn = popUpView.findViewById(R.id.popUpDB);
//        Button scrapBtn = popUpView.findViewById(R.id.popUpSB);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);
        Button donateBtn = popUpView.findViewById(R.id.popUpDonate);

        Button sendDB = view.findViewById(R.id.drawBrdBtn);

        //disable send to drawing board btn if no shirt & pant is not already sent
        sendDB.setEnabled(false);

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDB = ClosetDirections.actionClosetToDrawingBoard(shirts, pants);
            }
        });
        sendDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(actionDB);

            }
        });
//        scrapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("click");
//                Navigation.findNavController(view).navigate(R.id.action_closet_to_scrapbook);
//            }
//        });
        //
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
                isShirtClicked = true;
                if(isShirtClicked && isPantClicked){
                    sendDB.setEnabled(true);
                }
//                System.out.println("click");
//                testShirt1.setVisibility(View.GONE);
//                testShirt1.setImageDrawable(null);
                String newArr[] = new String[shirts.length + 1];
                for(int i = 0; i < shirts.length; i++){
                    newArr[i] = shirts[i];
                }
//                newArr[shirts.length] = testShirt1.getResources().getResourceEntryName(testShirt1.getId());
                newArr[pants.length] = String.valueOf(testShirt1.getTag());
                shirts = newArr;
//                System.out.println(Arrays.toString(shirts));
//                System.out.println(testShirt1.getResources().getResourceEntryName(testShirt1.getId()));
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
        ImageView pant1 = view.findViewById(R.id.pant1);
        pant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPantClicked = true;
                if(isShirtClicked && isPantClicked){
                    sendDB.setEnabled(true);
                }
//                System.out.println("click");
//                testShirt1.setVisibility(View.GONE);
//                testShirt1.setImageDrawable(null);
                String newArr[] = new String[pants.length + 1];
                for(int i = 0; i < pants.length; i++){
                    newArr[i] = pants[i];
                }
//                newArr[pants.length] = pant1.getResources().getResourceEntryName(pant1.getId());
                newArr[pants.length] = String.valueOf(pant1.getTag());
                pants = newArr;
//                System.out.println(Arrays.toString(shirts));
//                System.out.println(testShirt1.getResources().getResourceEntryName(testShirt1.getId()));
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
        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putStringArray("shirts", shirts);
//        outState.putStringArray("pants", pants);
//        System.out.println("saved");
//        super.onSaveInstanceState(outState);
//    }


}