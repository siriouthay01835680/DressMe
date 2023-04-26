package com.mobileapp.dressme;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Scrapbook newInstance() {
        return new Scrapbook();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrapbook, container, false);
        //saved instance
//        recyclerView = view.findViewById(R.id.scrapbookRV);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new scrapbookAdapter(arrayList);
//            System.out.println(ScrapbookArgs.fromBundle(getArguments()).getShirt().isEmpty());


        if (!(ScrapbookArgs.fromBundle(getArguments()).getDbShirts() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getDbPants() == null)) {
            shirt = ScrapbookArgs.fromBundle(getArguments()).getDbShirts();
            pants = ScrapbookArgs.fromBundle(getArguments()).getDbPants();
            GridLayout gridLayout = view.findViewById(R.id.gridLayout);
            for(int i = 0; i < gridLayout.getChildCount(); i++){
                View childView = gridLayout.getChildAt(i);
                if (childView instanceof CardView) {
                    CardView cardView = (CardView) childView;
                    cardView.setCardBackgroundColor(Color.YELLOW);
                    iterateRelativeLayouts(cardView);
                }
            }




//            RelativeLayout relativeLayout1 = view.findViewById(R.id.relativeLayout1);
//            relativeLayout1.removeAllViewsInLayout();
//            RelativeLayout relativeLayout2 = view.findViewById(R.id.relativeLayout2);
//            relativeLayout2.removeAllViewsInLayout();
//            RelativeLayout relativeLayout3 = view.findViewById(R.id.relativeLayout3);
//            relativeLayout3.removeAllViewsInLayout();
//            RelativeLayout relativeLayout4 = view.findViewById(R.id.relativeLayout4);
//            relativeLayout4.removeAllViewsInLayout();
//            RelativeLayout relativeLayout5 = view.findViewById(R.id.relativeLayout5);
//            relativeLayout5.removeAllViewsInLayout();
//            RelativeLayout relativeLayout6 = view.findViewById(R.id.relativeLayout6);
//            relativeLayout6.removeAllViewsInLayout();
//            RelativeLayout relativeLayout7 = view.findViewById(R.id.relativeLayout7);
//            relativeLayout7.removeAllViewsInLayout();
//            RelativeLayout relativeLayout8 = view.findViewById(R.id.relativeLayout8);
//            relativeLayout8.removeAllViewsInLayout();
//            RelativeLayout relativeLayout9 = view.findViewById(R.id.relativeLayout9);
//            relativeLayout9.removeAllViewsInLayout();
//            RelativeLayout relativeLayout10 = view.findViewById(R.id.relativeLayout10);
//            relativeLayout10.removeAllViewsInLayout();

//            relativeLayout1.

            //save map to shared pref to keep imgs & their grids

            //display shirts and bottom to a cardview


        }
        return view;
    }

    private void iterateRelativeLayouts(ViewGroup parent) {
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
//                        break;
//                        i = 20;
//                        System.out.println((String)relativeLayout.getTag());
//                        return;
//                    }
                }
                else{
                    System.out.println("here, full");
                }
                if(found){
                    break;
                }
                else if (childView instanceof ViewGroup) {
//                    /System.out
                    iterateRelativeLayouts((ViewGroup) childView);
                }
            }
        }
    }
}