package com.mobileapp.dressme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Closet extends Fragment {

    private ClosetViewModel mViewModel;
    String[] shirts = {};
    String[] pants = {};
    ClosetDirections.ActionClosetToDrawingBoard actionDB;
    Boolean isShirtClicked = false;
    Boolean isPantClicked = false;
    ArrayList<String> allShirts = new ArrayList<>();
    ArrayList<String> allPants = new ArrayList<>();
    View view;
    View popUpView;
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
        view = inflater.inflate(R.layout.fragment_closet,
                container, false);
//        ImageView testShirt1 = view.findViewById(R.id.shirt1);

        popUpView = inflater.inflate(R.layout.mainpopup,null);
//        View viewPopUp = inflater.inflate(R.layout.mainpopup,
//                container, false);
        Button drawBtn = popUpView.findViewById(R.id.popUpDB);
//        Button scrapBtn = popUpView.findViewById(R.id.popUpSB);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);
        Button donateBtn = popUpView.findViewById(R.id.popUpDonate);

        Button sendDB = view.findViewById(R.id.drawBrdBtn);

        LinearLayout shirtsLL = view.findViewById(R.id.shirtsLL);
        LinearLayout pantsLL = view.findViewById(R.id.pantsLL);

        //disable send to drawing board btn if no shirt & pant is not already sent
        sendDB.setEnabled(false);

        //implement refresh function to check folders and update accordingly,
        //should follow this following outline:

        //read from all files that contain tops
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/");

        String[] names = file.list();
        for(String name : names)
        {
            if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name).isDirectory())
            {
                if(name.contains("top")){
                    allShirts.add(name);
//                    if(name.contains(radioText)){
//                        folderNames.add(name);
//                    }
                }
                if(name.contains("bottom")){
                    allPants.add(name);
//                    if(name.contains(radioText)){
//                        folderNames.add(name);
//                    }
                }
            }
        }
        displayShirts(shirtsLL, allShirts);
        displayPants(pantsLL, allPants);
//        System.out.println(allShirts);
        //display each img
        //set on click listeners on imgs for pop ups
        //implement sending img to different fragments
        //implement delete funcitonality; check to see if delete will make folder empty

        //read from all files thar contain bottoms
        //repeat above

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
//                testShirt1.setVisibility(View.GONE);
//                System.out.println(v.getTag(0));
                //need to set tag of imgs with their filepath or some sort of identifier so i can delete it
            }
        });
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                testShirt1.setVisibility(View.GONE);
            }
        });

//        testShirt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isShirtClicked = true;
//                if(isShirtClicked && isPantClicked){
//                    sendDB.setEnabled(true);
//                }
////                System.out.println("click");
////                testShirt1.setVisibility(View.GONE);
////                testShirt1.setImageDrawable(null);
//                String newArr[] = new String[shirts.length + 1];
//                for(int i = 0; i < shirts.length; i++){
//                    newArr[i] = shirts[i];
//                }
////                newArr[shirts.length] = testShirt1.getResources().getResourceEntryName(testShirt1.getId());
//                newArr[pants.length] = String.valueOf(testShirt1.getTag());
//                shirts = newArr;
////                System.out.println(Arrays.toString(shirts));
////                System.out.println(testShirt1.getResources().getResourceEntryName(testShirt1.getId()));
//                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true;
//
//                PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
//                view.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
//                    }
//                });
//
//            }
//        });
//        ImageView pant1 = view.findViewById(R.id.pant1);
//        pant1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isPantClicked = true;
//                if(isShirtClicked && isPantClicked){
//                    sendDB.setEnabled(true);
//                }
////                System.out.println("click");
////                testShirt1.setVisibility(View.GONE);
////                testShirt1.setImageDrawable(null);
//                String newArr[] = new String[pants.length + 1];
//                for(int i = 0; i < pants.length; i++){
//                    newArr[i] = pants[i];
//                }
////                newArr[pants.length] = pant1.getResources().getResourceEntryName(pant1.getId());
//                newArr[pants.length] = String.valueOf(pant1.getTag());
//                pants = newArr;
////                System.out.println(Arrays.toString(shirts));
////                System.out.println(testShirt1.getResources().getResourceEntryName(testShirt1.getId()));
//                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true;
//
//                PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
//                view.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
//                    }
//                });
//
//            }
//        });
        return view;
    }



    private void displayShirts(LinearLayout linearLayout, ArrayList<String> allShirts) {
        for (int i = 0; i < allShirts.size(); i++) {
            File aFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i));
            String[] files = aFolder.list();
            //System.out.println(Arrays.toString(files));
            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 2000 + i;
                img.setId(id);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(25, 25, 25, 25);
                img.setLayoutParams(lp);
//
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
                Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i) + "/" + name);
                img.setImageBitmap(myBitmap);
//        img.setImageDrawable(getResources().getDrawable(resId));
                linearLayout.addView(img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPopUp(myBitmap);
                    }
                });
            }

        }
    }
    private void displayPants(LinearLayout linearLayout, ArrayList<String> allPants) {
        for (int i = 0; i < allPants.size(); i++) {
            File aFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i));
            String[] files = aFolder.list();

            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 3000 + i;
                img.setId(id);
//                img.setTag(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name);
                System.out.println(name);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(25, 25, 25, 25);
                img.setLayoutParams(lp);
//
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
                Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name);
                img.setImageBitmap(myBitmap);
//        img.setImageDrawable(getResources().getDrawable(resId));
                linearLayout.addView(img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPopUp(myBitmap);
                    }
                });
            }

        }
    }
    private void displayPopUp(Bitmap myBitmap){
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        view.post(new Runnable() {
            @Override
            public void run() {
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);
                popUpImg.setImageBitmap(myBitmap);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putStringArray("shirts", shirts);
//        outState.putStringArray("pants", pants);
//        System.out.println("saved");
//        super.onSaveInstanceState(outState);
//    }


}