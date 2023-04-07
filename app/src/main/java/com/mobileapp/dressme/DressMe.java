package com.mobileapp.dressme;

//import static android.support.v4.media.session.MediaControllerCompatApi21.getPackageName;
import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.Random;

public class DressMe extends Fragment {

    private DressMeViewModel mViewModel;

    public static DressMe newInstance() {
        return new DressMe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress_me,
                container, false);
        Button generate = view.findViewById(R.id.generateBtn);
//        ImageView shirt = view.findViewById(R.id.genShirt);
//        ImageView pants = view.findViewById(R.id.genPant);

        LinearLayout layout = view.findViewById(R.id.imgLayout);
        Button regenerate = view.findViewById(R.id.regenerateBtn);
        final String[][] resultItems = new String[1][1];
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shirt.setVisibility(view.VISIBLE);
//                pants.setVisibility(view.VISIBLE);
                //read from file to pick random shirt/pant
                //outline for randomizer
                //for now have array of test img ids
                resultItems[0] = outfitGeneration(layout);
//                System.out.println(resultItems[0][1]);

            }
        });
        regenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shirt.setVisibility(view.VISIBLE);
//                pants.setVisibility(view.VISIBLE);
                //read from file to pick random shirt/pant
                //outline for randomizer
                //for now have array of test img ids
                layout.removeAllViewsInLayout();
                resultItems[0] = outfitGeneration(layout);

            }
        });
        Button scrapbookBtn = view.findViewById(R.id.scrapbookBtn);
        scrapbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DressMeDirections.ActionDressMeToScrapbook action = DressMeDirections.actionDressMeToScrapbook(resultItems[0][0], resultItems[0][1]);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }
    public String[] outfitGeneration(LinearLayout layout){
        String[] shirtIds = {"orangeshirt", "testshirt"};
        String[] pantsIds = {"testpants"};
        Random rand = new Random();
        String randShirt = shirtIds[1];
        String randPants = pantsIds[0];
//                for(int i = 0; i < itemIDs.length; i++){
//
//                }
        //dynamically create new imageview w/ clothing image
        ImageView img = new ImageView(layout.getContext());
        int id = 100;
        img.setId(id);
        img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
//                img.setImageDrawable(getResources().getDrawable(R.drawable.testshirt));
        int resId = getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme");
//                System.out.println("res" + resId);
//                System.out.println(shirtIds[0]);
//                img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme")));

        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img);

        ImageView img1 = new ImageView(layout.getContext());
        id = 101;
        img1.setId(id);
        img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
//                img1.setImageDrawable(getResources().getDrawable(R.drawable.testpants));
        resId = getResources().getIdentifier(randPants, "drawable", "com.mobileapp.dressme");
        img1.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img1);
        String[] result = {randShirt, randPants};
        return result;
    }
}