package com.mobileapp.dressme;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Scrapbook extends Fragment {

    private ScrapbookViewModel mViewModel;
    private RecyclerView recyclerView;
    private scrapbookAdapter adapter;
    private ArrayList<scrapbookModel> arrayList = new ArrayList<>();
    Map<Integer, String> hm = new HashMap<Integer, String>();
    CardView cardview;
//    RelativeLayout rl;
    String[] shirt;
    String[] pants;
    Map<Integer, RelativeLayout> allLayouts = new HashMap<>();

    public static Scrapbook newInstance() {
        return new Scrapbook();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrapbook, container, false);
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
        //saved instance
//        recyclerView = view.findViewById(R.id.scrapbookRV);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new scrapbookAdapter(arrayList);
//            System.out.println(ScrapbookArgs.fromBundle(getArguments()).getShirt().isEmpty());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("scrapbookPref", Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().commit();
       for(int i = 1; i <= 10; i++){
           if (sharedPreferences.contains(String.valueOf(i))){
               hm.put(i, sharedPreferences.getString(String.valueOf(i), ""));
               if(!Objects.equals(hm.get(i), " ")){
                   displayScrapbook(i);
               }
           }
           else{
               hm.put(i, " ");
           }
       }


        // initalizing map
//        for (int i = 1; i <= 10; i++) {

//            hm.put(i, " ");
//        }

//        ArrayList<RelativeLayout> allLayouts = new ArrayList<>();
//        GridLayout parentLayout = view.findViewById(R.id.gridLayout); // assuming parentLayout is the parent RelativeLayout view
//        System.out.println("children: " + parentLayout.getChildCount());
//        for (int i = 0; i < parentLayout.getChildCount(); i++) {
//            View childView = parentLayout.getChildAt(i);
//            if (childView instanceof CardView) {
//                RelativeLayout childLayout = (RelativeLayout) childView;
//                allLayouts.add(childLayout);
//            }
//        }
//        System.out.println(allLayouts.size());



//        int foundKey = 0;
//        // look for first encounter of an empty grid
//        for (Map.Entry<Integer, String[]> entry : hm.entrySet()) {
//            if (entry.getValue() == null) {
////                System.out.println("Encountered empty at key: " + entry.getKey());
//                foundKey = entry.getKey();
//                break;
//            }
//        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            hm.forEach((key, value) -> {
////                System.out.println("Key=" + key + ", Value=" + value);
//                if(Objects.equals(value, "empty")){
//                    System.out.println(key);
//                    return;
//                }
//            });
//        }

        String totalStr = "";
        int foundKey = 0;
        if (!(ScrapbookArgs.fromBundle(getArguments()).getDbShirts() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getDbPants() == null)) {
            shirt = ScrapbookArgs.fromBundle(getArguments()).getDbShirts();
            pants = ScrapbookArgs.fromBundle(getArguments()).getDbPants();
            GridLayout gridLayout = view.findViewById(R.id.gridLayout);
//            for (int i = 0; i < gridLayout.getChildCount(); i++) {
//                View childView = gridLayout.getChildAt(i);
//                if (childView instanceof CardView) {
//                    CardView cardView = (CardView) childView;
////                    cardView.setCardBackgroundColor(Color.YELLOW);
//                    iterateRelativeLayouts(cardView);
//                }
//                }

            // look for first encounter of an empty grid
            for (Map.Entry<Integer, String> entry : hm.entrySet()) {
                if (Objects.equals(entry.getValue(), " ")) {
                System.out.println("Encountered  empty  at key: " + entry.getKey());
                    foundKey = entry.getKey();
                    break;
                }
            }
            totalStr = shirt[0] + " " + pants[0];
            hm.put(foundKey, totalStr);
            displayScrapbook(foundKey);
        }

        totalStr = "";
        foundKey = 0;
        if (!(ScrapbookArgs.fromBundle(getArguments()).getShirt() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getPants() == null)) {
            shirt = ScrapbookArgs.fromBundle(getArguments()).getShirt();
            pants = ScrapbookArgs.fromBundle(getArguments()).getPants();
//                GridLayout gridLayout = view.findViewById(R.id.gridLayout);

            // look for first encounter of an empty grid
            for (Map.Entry<Integer, String> entry : hm.entrySet()) {
                if (Objects.equals(entry.getValue(), " ")) {
                System.out.println("Encountered empty at key: " + entry.getKey());
                    foundKey = entry.getKey();
                    break;
                }
            }
            totalStr = shirt[0] + " " + pants[0];
            hm.put(foundKey, totalStr);
            displayScrapbook(foundKey);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<Integer, String> entry : hm.entrySet()) {
            String hashKey = String.valueOf(entry.getKey());
            editor.putString(hashKey, entry.getValue());
        }
        editor.commit();

        //save map to shared pref to keep imgs & their grids

        //display shirts and bottom to a cardview


        return view;
    }

    private void displayScrapbook(int foundKey) {
        RelativeLayout relativeLayout = allLayouts.get(foundKey);
        String[] items = hm.get(foundKey).split(" ");

        ImageView img = new ImageView(relativeLayout.getContext());
        int id = 700 + foundKey;
        img.setId(id);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
        img.setLayoutParams(lp);
        Bitmap myBitmap = BitmapFactory.decodeFile(items[0]);
        img.setImageBitmap(myBitmap);
        img.setRotation(90);
        relativeLayout.addView(img);

        ImageView img1 = new ImageView(relativeLayout.getContext());
        id = 750 + foundKey;
        img1.setId(id);
        img1.setLayoutParams(new LinearLayout.LayoutParams(250, 250));
        lp = new RelativeLayout.LayoutParams(250, 250);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        img1.setLayoutParams(lp);
        Bitmap myBitmap2 = BitmapFactory.decodeFile(items[1]);
        img1.setImageBitmap(myBitmap2);
        img1.setRotation(90);
        relativeLayout.addView(img1);


    }

    private void iterateRelativeLayouts (ViewGroup parent){
            for (int i = 0; i < parent.getChildCount(); i++) {
                View childView = parent.getChildAt(i);
                if (childView instanceof RelativeLayout) {
                    RelativeLayout relativeLayout = (RelativeLayout) childView;
                    // Do something with the RelativeLayout
                    // For example, you can iterate through its child views using a nested loop
                    String tag = (String) relativeLayout.getTag();
                    Boolean found = false;
//                if (layout2.getChildCount() == 0) {
//////            System.out.println("no children");
//////        }
                    //tag.contains("empty")
                    if (relativeLayout.getChildCount() == 0) {
//                    for (int j = 0; j < shirt.length; j++) {
                        found = true;
//                        System.out.println("here in rel");
                        ImageView img = new ImageView(relativeLayout.getContext());
                        int id = 700 + i;
                        img.setId(id);
//                        img.setLayoutParams(new LinearLayout.LayoutParams(250, 250));
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
                        img.setLayoutParams(lp);
                        //System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + shirt[j]);
                        Bitmap myBitmap = BitmapFactory.decodeFile(shirt[0]);
                        img.setImageBitmap(myBitmap);
                        img.setRotation(90);
                        relativeLayout.addView(img);
//                        relativeLayout.setTag("full");

                        ImageView img1 = new ImageView(relativeLayout.getContext());
                        id = 800 + i;
                        img1.setId(id);
                        img1.setLayoutParams(new LinearLayout.LayoutParams(250, 250));
                        lp = new RelativeLayout.LayoutParams(250, 250);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        img1.setLayoutParams(lp);
                        Bitmap myBitmap2 = BitmapFactory.decodeFile(pants[0]);
                        img1.setImageBitmap(myBitmap2);
                        img1.setRotation(90);
                        relativeLayout.addView(img1);
                        relativeLayout.setTag("full");
                        break;
//                        break;
//                        i = 20;
//                        System.out.println((String)relativeLayout.getTag());
//                        return;
//                    }
                    } else {
                        System.out.println("here, full");
                    }
                    if (found) {
                        break;
                    } else if (childView instanceof ViewGroup) {
//                    /System.out
                        iterateRelativeLayouts((ViewGroup) childView);
                    }
                }
            }
        }
}
