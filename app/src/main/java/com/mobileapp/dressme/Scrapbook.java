package com.mobileapp.dressme;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Scrapbook extends Fragment {

    private ScrapbookViewModel mViewModel;

    public static Scrapbook newInstance() {
        return new Scrapbook();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrapbook, container, false);
//            System.out.println(ScrapbookArgs.fromBundle(getArguments()).getShirt().isEmpty());
        if(!(ScrapbookArgs.fromBundle(getArguments()).getShirt().isEmpty()) && !(ScrapbookArgs.fromBundle(getArguments()).getPants().isEmpty())){
            String shirt = ScrapbookArgs.fromBundle(getArguments()).getShirt();
            String pants = ScrapbookArgs.fromBundle(getArguments()).getPants();
            RelativeLayout layout = view.findViewById(R.id.relativeLayout1);

            ImageView img = new ImageView(layout.getContext());
            int id = 110;
            img.setId(id);
            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
//            int resId = getResources().getIdentifier(shirt, "drawable", "com.mobileapp.dressme");
//
//
            Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + shirt);
            img.setImageBitmap(myBitmap);
            layout.addView(img);

            ImageView img1 = new ImageView(layout.getContext());
            id = 111;
            img1.setId(id);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
            img1.setLayoutParams(lp);
//
//            resId = getResources().getIdentifier(pants, "drawable", "com.mobileapp.dressme");
//
//            img1.setImageDrawable(getResources().getDrawable(resId));
            Bitmap myBitmap1 = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + pants);
            img1.setImageBitmap(myBitmap1);
//            layout.addView(img1);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layout.addView(img1);
        }


//        way to see which grids are empty for sending form drawing board to scrapbook
//        RelativeLayout layout2 = view.findViewById(R.id.relativeLayout2);
//        if (layout2.getChildCount() == 0) {
//            System.out.println("no children");
//        }
        if(!(ScrapbookArgs.fromBundle(getArguments()).getDbShirts() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getDbPants() == null)){
            String[] shirts = ScrapbookArgs.fromBundle(getArguments()).getDbShirts();
            String[] pants = ScrapbookArgs.fromBundle(getArguments()).getDbPants();
            RelativeLayout layout = view.findViewById(R.id.relativeLayout2);
            if(shirts.length != 0){
                for(int i = 0; i < shirts.length; i++){
                    ImageView img = new ImageView(layout.getContext());
                    int id = 400 + i;
                    img.setId(id);
                    img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
                    int resId = getResources().getIdentifier(shirts[i], "drawable", "com.mobileapp.dressme");
                    img.setImageDrawable(getResources().getDrawable(resId));
                    layout.addView(img);
                }
            }
            if(pants.length != 0){
                for(int i = 0; i < pants.length; i++){
                    ImageView img1 = new ImageView(layout.getContext());
                    int id = 500 + i;
                    img1.setId(id);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
                    img1.setLayoutParams(lp);
                    int resId = getResources().getIdentifier(pants[i], "drawable", "com.mobileapp.dressme");
                    img1.setImageDrawable(getResources().getDrawable(resId));
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layout.addView(img1);
                }
            }
//            ImageView img = new ImageView(layout.getContext());
//            int id = 110;
//            img.setId(id);
//            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
//            int resId = getResources().getIdentifier(shirt, "drawable", "com.mobileapp.dressme");
//
//            img.setImageDrawable(getResources().getDrawable(resId));
//            layout.addView(img);
//
//            ImageView img1 = new ImageView(layout.getContext());
//            id = 111;
//            img1.setId(id);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
//            img1.setLayoutParams(lp);
//
//            resId = getResources().getIdentifier(pants, "drawable", "com.mobileapp.dressme");
//
//            img1.setImageDrawable(getResources().getDrawable(resId));
//            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            layout.addView(img1);
        }

        return view;
    }


}