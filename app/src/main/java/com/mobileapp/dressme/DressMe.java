package com.mobileapp.dressme;

//import static android.support.v4.media.session.MediaControllerCompatApi21.getPackageName;
import static android.view.View.VISIBLE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DressMe extends Fragment {

    private DressMeViewModel mViewModel;
    Boolean isGenerated = false;
    String radioText = "";
    ArrayList<String> folderNames = new ArrayList<String>();
    ArrayList<String> resultItems = new ArrayList<String>();



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
//        final String[][] resultItems = new String[1][1];
        Button scrapbookBtn = view.findViewById(R.id.scrapbookBtn);

        //to disable scrapbook & regenerate button if outfit hasn't been generated.
        scrapbookBtn.setEnabled(false);
        regenerate.setEnabled(false);
        generate.setEnabled(false);

        //to disable button if no radio button is checked
        RadioGroup seasonGroup = view.findViewById(R.id.radioGroup);
//        int id = radioGroup.getCheckedRadioButtonId();
        seasonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.SpringButton:
                        radioText = "Spring";
                        System.out.println("season");
                        generate.setEnabled(true);
                        break;
                    case R.id.SummerButton:
                        radioText = "Summer";
                        generate.setEnabled(true);
                        break;
                    case R.id.FallButton:
                        radioText = "Fall";
                        generate.setEnabled(true);
                        break;
                    case R.id.WinterButton:
                        radioText = "Winter";
                        generate.setEnabled(true);
                        break;
                    default:
                       radioText = "";
                        break;
                }

            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shirt.setVisibility(view.VISIBLE);
//                pants.setVisibility(view.VISIBLE);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/");
//                if(file.exists()){
////                    System.out.println("file exists");
//                }
//                System.out.println(radioText);

                String[] names = file.list();
//                System.out.println(Arrays.toString(file.list()));
                for(String name : names)
                {
                    if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name).isDirectory())
                    {
                        if(name.contains("top") || name.contains("bottom")){
                            if(name.contains(radioText)){
                                folderNames.add(name);
                            }
                        }
                    }
                }
//                System.out.println(folderNames);
                //read from file to pick random shirt/pant
                //outline for randomizer
                //for now have array of test img ids

                resultItems= outfitGeneration(layout, folderNames);
                isGenerated = true;
                scrapbookBtn.setEnabled(true);
                regenerate.setEnabled(true);
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
                resultItems = (outfitGeneration(layout, folderNames));

            }
        });
        scrapbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DressMeDirections.ActionDressMeToScrapbook action = DressMeDirections.actionDressMeToScrapbook();
                action.setShirt(resultItems.get(0));
                action.setPants(resultItems.get(1));
                Navigation.findNavController(view).navigate(action);
            }
        });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    generate.setEnabled(true);
//            }
//        });




        return view;
    }
    public ArrayList<String> outfitGeneration(LinearLayout layout, ArrayList<String>folderNames){
        //these should be read from file or tags
//        String[] shirtIds = {"orangeshirt", "testshirt"};
//        String[] pantsIds = {"testpants"};
        ArrayList<String> shirtNames = new ArrayList<String>();
        ArrayList<String> pantNames = new ArrayList<String>();

        for (int i = 0; i < folderNames.size(); i++) {
            if(folderNames.get(i).contains("top")){
                shirtNames.add(folderNames.get(i));
            }
            else{
                pantNames.add(folderNames.get(i));
            }
        }
//        System.out.println(shirtNames);
//        System.out.println(pantNames);

//        Random shirtRand = new Random();
//        Random pantRand = new Random();
//        String randShirt = shirtIds[1];
//        String randPants = pantsIds[0];
        int shirtIndex = (int)(Math.random() * shirtNames.size());
        int pantIndex = (int)(Math.random() * pantNames.size());
//        System.out.println(shirtNames.get(shirtIndex));
//        System.out.println(pantNames.get(pantIndex));

        File shirtFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + shirtNames.get(shirtIndex));
        File pantFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + pantNames.get(pantIndex));

//                if(shirtFile.exists()){
//                    System.out.println("file1 exists");
//                }
//        if(pantFile.exists()){
//            System.out.println("file2 exists");
//        }

        String[] names1 = shirtFile.list();
        String[] names2 = pantFile.list();
//        System.out.println(Arrays.toString(names1));
//        System.out.println(Arrays.toString(names2));
        int randShirtIndex = (int)(Math.random() * names1.length);
        int randPantIndex = (int)(Math.random() * names2.length);
//        System.out.println(randShirtIndex);
//        System.out.println(randPantIndex);

//        System.out.println(shirtIndex);
//        System.out.println(pantIndex);

//        dynamically create new imageview w/ clothing image
        ImageView img = new ImageView(layout.getContext());
        int id = 100;
        img.setId(id);
        img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
//                img.setImageDrawable(getResources().getDrawable(R.drawable.testshirt));
//        int resId = getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme");
//                System.out.println("res" + resId);
//                System.out.println(shirtIds[0]);
//                img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme")));
//    System.out.println(names1[shirtIndex]);
        Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + shirtNames.get(shirtIndex) + "/" + names1[randShirtIndex]);
        img.setImageBitmap(myBitmap);
//        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img);
        ImageView img1 = new ImageView(layout.getContext());
        id = 101;
        img1.setId(id);
        img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
//                img.setImageDrawable(getResources().getDrawable(R.drawable.testshirt));
//        int resId = getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme");
//                System.out.println("res" + resId);
//                System.out.println(shirtIds[0]);
//                img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme")));
//    System.out.println(names1[shirtIndex]);
        Bitmap myBitmap1 = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + pantNames.get(pantIndex) + "/" + names2[randPantIndex]);
        img1.setImageBitmap(myBitmap1);
//        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img1);

//        ImageView img1 = new ImageView(layout.getContext());
//        id = 101;
//        img1.setId(id);
//        img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
////                img1.setImageDrawable(getResources().getDrawable(R.drawable.testpants));
//        resId = getResources().getIdentifier(randPants, "drawable", "com.mobileapp.dressme");
//        img1.setImageDrawable(getResources().getDrawable(resId));
//        layout.addView(img1);
        ArrayList<String> result = new ArrayList<String>();
        result.add(shirtNames.get(shirtIndex) + "/" + names1[randShirtIndex]);
        result.add(pantNames.get(pantIndex) + "/" + names2[randPantIndex]);
//        String[] result = {randShirt, randPants};
        return result;
    }
}