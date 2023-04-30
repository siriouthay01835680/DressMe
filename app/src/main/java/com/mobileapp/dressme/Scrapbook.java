package com.mobileapp.dressme;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Scrapbook extends Fragment {

    Map<Integer, String> hm = new HashMap<Integer, String>();
    String[] shirt;
    String[] pants;
    Map<Integer, RelativeLayout> allLayouts = new HashMap<>();
    View popUpView;
    PopupWindow popupWindow;
    View view;
    ArrayList<RelativeLayout> clickedView = new ArrayList<RelativeLayout>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //getting connection to layout and the relative layouts
        //all ids to relative layouts are in array list
        //their keys correspond to their layout #
        //these keys will also correspond to the hashmap.
        view = inflater.inflate(R.layout.fragment_scrapbook, container, false);
        RelativeLayout relativeLayout1 = view.findViewById(R.id.relativeLayout1);
        allLayouts.put(1, relativeLayout1);
        RelativeLayout relativeLayout2 = view.findViewById(R.id.relativeLayout2);
        allLayouts.put(2, relativeLayout2);
        RelativeLayout relativeLayout3 = view.findViewById(R.id.relativeLayout3);
        allLayouts.put(3, relativeLayout3);
        RelativeLayout relativeLayout4 = view.findViewById(R.id.relativeLayout4);
        allLayouts.put(4, relativeLayout4);
        RelativeLayout relativeLayout5 = view.findViewById(R.id.relativeLayout5);
        allLayouts.put(5, relativeLayout5);
        RelativeLayout relativeLayout6 = view.findViewById(R.id.relativeLayout6);
        allLayouts.put(6, relativeLayout6);
        RelativeLayout relativeLayout7 = view.findViewById(R.id.relativeLayout7);
        allLayouts.put(7, relativeLayout7);
        RelativeLayout relativeLayout8 = view.findViewById(R.id.relativeLayout8);
        allLayouts.put(8, relativeLayout8);
        RelativeLayout relativeLayout9 = view.findViewById(R.id.relativeLayout9);
        allLayouts.put(9, relativeLayout9);
        RelativeLayout relativeLayout10 = view.findViewById(R.id.relativeLayout10);
        allLayouts.put(10, relativeLayout10);

        //for displaying popup window on click
        popUpView = inflater.inflate(R.layout.donatepopup,null);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);

        //initalizing shared preferences for scrapbook
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("scrapbookPref", Context.MODE_PRIVATE);

        //delete button listener for popup
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete the most recently clicked view
                RelativeLayout clearRl = clickedView.get(clickedView.size()-1);
                //remove all its imageview children
                clearRl.removeAllViews();
                //update hashmap to clear the deleted outfit and save to shared pref
                for(int i = 1; i <= 10; i++){
                    if(allLayouts.get(i) == clearRl){
                        hm.put(i, " ");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        for (Map.Entry<Integer, String> entry : hm.entrySet()) {
                            String hashKey = String.valueOf(entry.getKey());
                            editor.putString(hashKey, entry.getValue());
                        }
                        //save changes
                        editor.commit();
                        break;
                    }
                }
            }
        });

        //get all of the values already in shared pref and save them to hashmap
        for(int i = 1; i <= 10; i++){
           if (sharedPreferences.contains(String.valueOf(i))){
               hm.put(i, sharedPreferences.getString(String.valueOf(i), ""));
               //display entries in hm that are not currently empty
               if(!Objects.equals(hm.get(i), " ")){
                   displayScrapbook(i);
               }
           }
           else{
               hm.put(i, " ");
           }
       }

        // add on click listener for all relative layouts for popup
       for(int i = 1; i < allLayouts.size(); i++){
           int finalI = i;
           allLayouts.get(i).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   clickedView.add(allLayouts.get(finalI));
                   displayPopup();
               }
           });
       }

        String totalStr = "";
        int foundKey = 0;
        //if args from DB are sent
        if (!(ScrapbookArgs.fromBundle(getArguments()).getDbShirts() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getDbPants() == null)) {
            shirt = ScrapbookArgs.fromBundle(getArguments()).getDbShirts();
            pants = ScrapbookArgs.fromBundle(getArguments()).getDbPants();

            // look for first encounter of an empty grid
            for (Map.Entry<Integer, String> entry : hm.entrySet()) {
                if (Objects.equals(entry.getValue(), " ")) {
                    foundKey = entry.getKey();
                    break;
                }
            }

            //if an empty layout is found, save the paths to the corresponding hashmap and display
            if(foundKey != 0){
                totalStr = shirt[0] + " " + pants[0];
                hm.put(foundKey, totalStr);
                displayScrapbook(foundKey);
            }
            //if there are no more empty grids, prompt user.
            else{
                Toast.makeText(getContext(), "Your Scrapbook is full, try deleting some outfits to clear up space.", Toast.LENGTH_SHORT).show();
            }

        }

        totalStr = "";
        foundKey = 0;
        //if args from dress me screen are sent over
        if (!(ScrapbookArgs.fromBundle(getArguments()).getShirt() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getPants() == null)) {
            shirt = ScrapbookArgs.fromBundle(getArguments()).getShirt();
            pants = ScrapbookArgs.fromBundle(getArguments()).getPants();

            // look for first encounter of an empty grid
            for (Map.Entry<Integer, String> entry : hm.entrySet()) {
                if (Objects.equals(entry.getValue(), " ")) {
                    foundKey = entry.getKey();
                    break;
                }
            }
            //save found empty grid and image paths to hm
            if(foundKey != 0){
                totalStr = shirt[0] + " " + pants[0];
                hm.put(foundKey, totalStr);
                displayScrapbook(foundKey);
            }
            //prompt user
            else{
                Toast.makeText(getContext(), "Your Scrapbook is full, try deleting some outfits to clear up space.", Toast.LENGTH_LONG).show();
            }
        }

        //save all changes of hm to shared pref
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<Integer, String> entry : hm.entrySet()) {
            String hashKey = String.valueOf(entry.getKey());
            editor.putString(hashKey, entry.getValue());
        }
        //save changes
        editor.commit();

        return view;
    }

    //function to display popups
    private void displayPopup() {
        int width = 1000;
        int height = 1000;
        boolean focusable = true;

        popupWindow = new PopupWindow(popUpView, width, height, focusable);
        TextView popUpTxt = popUpView.findViewById(R.id.goback);
        popUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        view.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });
    }

    //function to display to scrapbook
    private void displayScrapbook(int foundKey) {
        //get the layout of the empty view
        RelativeLayout relativeLayout = allLayouts.get(foundKey);

        //separate items into array by delimiter
        String[] items = hm.get(foundKey).split(" ");

        //display the first shirt image from path
        ImageView img = new ImageView(relativeLayout.getContext());
        int id = 700 + foundKey;
        img.setId(id);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(300, 300);
        img.setLayoutParams(lp);
        Bitmap myBitmap = BitmapFactory.decodeFile(items[0]);
        img.setImageBitmap(myBitmap);
        img.setRotation(90);
        relativeLayout.addView(img);

        //display the pant image from path
        ImageView img1 = new ImageView(relativeLayout.getContext());
        id = 750 + foundKey;
        img1.setId(id);
        img1.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        lp = new RelativeLayout.LayoutParams(300, 300);

        //align to right so it will not overlap with the first image
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        img1.setLayoutParams(lp);
        Bitmap myBitmap2 = BitmapFactory.decodeFile(items[1]);
        img1.setImageBitmap(myBitmap2);
        img1.setRotation(90);
        relativeLayout.addView(img1);
    }
}
