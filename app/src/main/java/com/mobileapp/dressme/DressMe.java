package com.mobileapp.dressme;

//import static android.support.v4.media.session.MediaControllerCompatApi21.getPackageName;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ArrayList;
import java.util.Arrays;

public class DressMe extends Fragment {

    Boolean isGenerated = false;
    ArrayList<String> folderNames = new ArrayList<String>();
    ArrayList<String> resultItems = new ArrayList<String>();
    ArrayList<String> resultShirts = new ArrayList<String>();
    ArrayList<String> resultPants = new ArrayList<String>();
    String resultFile = "";
    ArrayList<String> shirtNames = new ArrayList<String>();
    ArrayList<String> pantNames = new ArrayList<String>();

    ArrayList<String> seasonShirts = new ArrayList<String>();
    ArrayList<String> seasonPants = new ArrayList<String>();
    //creating arrays to hold the shirts and season

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
        //getting access to all the buttons that were created
        //to disable scrapbook & regenerate button if outfit hasn't been generated.
        scrapbookBtn.setEnabled(false);
        regenerate.setEnabled(false);
        generate.setEnabled(false);

        //accessing the folders with the stored images
        File allFolders = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/");
        String[] folders = allFolders.list();
        //create array for folders
//        System.out.println(Arrays.toString(folders));
        seasonShirts.clear();
        seasonPants.clear();
        //make sure it is empty for regeneration

        for (int i = 0; i < folders.length; i++) {
            if(folders[i].contains("Top")){
                shirtNames.add(folders[i]);
                //folders that contain top should be added to shirt folder
            }
            else if(folders[i].contains("Bottom")){
                pantNames.add(folders[i]);
                //folders that contain bottom dhould be added ot bottoms folder
            }
        }




        RadioGroup seasonGroup = view.findViewById(R.id.radioGroup);
        seasonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //set listener to radio buttons to see what the user picks for season
                boolean canGenerate = false;
                seasonShirts.clear();
                seasonPants.clear();
                //make sure arrays are empty
                String radioText = "";
                switch(checkedId) {
                    case R.id.SpringButton:
                        radioText = "Spring";
                        //if user chooses spring, call the generate function to generate an outfit based on the season
                        canGenerate = canGenerate(radioText);
//                        System.out.println(canGenerate);
                        if(canGenerate) {
                            generate.setEnabled(true);
                            //if there are items present in the folders, the generation is possible
                        }
                        else{
                            //if no items are present in the folder, generate is disabled
                            generate.setEnabled(false);
                        }
//                        if()
//                        generate.setEnabled(true);
                        break;
                    case R.id.SummerButton:
                        //repeat same process done for spring for summer
                        radioText = "Summer";
                        canGenerate = canGenerate(radioText);
                        System.out.println(canGenerate);
                        if(canGenerate) {
                            generate.setEnabled(true);
                        }
                        else{
                            generate.setEnabled(false);
                        }
//                        generate.setEnabled(true);
                        break;
                    case R.id.FallButton:
                        //repeat same process done for spring for fall
                        radioText = "Fall";
                        canGenerate = canGenerate(radioText);
                        if(canGenerate) {
                            generate.setEnabled(true);
                        }
                        else{
                            generate.setEnabled(false);
                        }
//                        System.out.println(canGenerate);
                        break;
                    case R.id.WinterButton:
                        //repeat same process done for spring for winter
                        radioText = "Winter";
                        canGenerate = canGenerate(radioText);
                        if(canGenerate) {
                            generate.setEnabled(true);
                        }
                        else{
                            generate.setEnabled(false);
                        }
//                        System.out.println(canGenerate);
                        break;
                    default: //default case
                        radioText = "";
                        canGenerate = false;
                        break;
                }


            }

        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when generate is pressed , test that we are in this function
                System.out.println("here " + seasonShirts);
                System.out.println("here" + seasonPants);
//
//
//                shirt.setVisibility(view.VISIBLE);
//                pants.setVisibility(view.VISIBLE);

//                System.out.println(folderNames);
                //read from file to pick random shirt/pant
                //outline for randomizer
                //for now have array of test img ids
                //empty out layout
                layout.removeAllViewsInLayout();
                resultItems = outfitGeneration(layout, folderNames, shirtNames, pantNames);
                //call generate function and set scrapbook and regeneration to true so user can recreate or save the outfit
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
                resultItems = (outfitGeneration(layout, folderNames, shirtNames, pantNames));
                //repeat similar process as generate
//                System.out.println("res: " + resultItems);

            }
        });

        scrapbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //once scrapbook is called, navigate to the scrapbook fragment
                DressMeDirections.ActionDressMeToScrapbook action = DressMeDirections.actionDressMeToScrapbook();
//                String[] resArr = new String[resultItems.size()];
//                resArr = resultItems.toArray(resArr);
                String [] shirt;
                String [] pant;
                shirt = new String[]{resultItems.get(0)};
                pant = new String[]{resultItems.get(1)};

//               System.out.println("items " + Arrays.toString(shirt));

                action.setShirt(shirt);
                action.setPants(pant);
                //set the shirt and pant in the array and send it to be saved in scrapbook
                Navigation.findNavController(view).navigate(action);
                //send the action
            }
        });





        return view;
    }

    private boolean canGenerate(String radioText) {
        boolean shirtRes = false;
        boolean pantRes = false;
        //can only generate when there are items in the folder
//        System.out.println(shirtNames);
//        System.out.println(pantNames);

        //checks to see if the items are in the folder to return a true or false  if there are items present
        for(int i = 0; i < shirtNames.size(); i++){
//                    System.out.println(shirtNames.get(i));
            if(shirtNames.get(i).contains(radioText)){
                seasonShirts.add(shirtNames.get(i));
                shirtRes = true;
            }
        }
        for(int i = 0; i < pantNames.size(); i++){
//            System.out.println(pantNames.get(i));
            if(pantNames.get(i).contains(radioText)){
                seasonPants.add(pantNames.get(i));
//                System.out.println(seasonPants);
                pantRes = true;
            }
        }
        if(shirtRes && pantRes){
            return true;
        }

//            && pantNames.get(j).contains(radioText)
        return false;

    }

    public ArrayList<String> outfitGeneration(LinearLayout layout, ArrayList<String>folderNames,ArrayList<String>shirtNames, ArrayList<String>pantNames) {
        //these should be read from file or tags
//        String[] shirtIds = {"orangeshirt", "testshirt"};
//        String[] pantsIds = {"testpants"};
//        ArrayList<String> shirtNames = new ArrayList<String>();
//        ArrayList<String> pantNames = new ArrayList<String>();


//        System.out.println(shirtNames);
//        System.out.println(pantNames);

//        Random shirtRand = new Random();
//        Random pantRand = new Random();
//        String randShirt = shirtIds[1];
//        String randPants = pantsIds[0];
        //generate random shirt and random pants from the the array
        int shirtIndex = (int)(Math.random() * seasonShirts.size());
        int pantIndex = (int)(Math.random() * seasonPants.size());
//        System.out.println("index " + seasonShirts.get(shirtIndex));
//        System.out.println("index " + seasonPants.get(pantIndex));
//        System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonShirts.get(shirtIndex));
//        System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonPants.get(pantIndex));

        //get access to the files in the external storage
        File shirtFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonShirts.get(shirtIndex));
        File pantFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonPants.get(pantIndex));

//                if(shirtFile.exists()){
//                    System.out.println("file1 exists");
//                }
//        if(pantFile.exists()){
//            System.out.println("file2 exists");
//        }

        String[] names1 = shirtFile.list();
        String[] names2 = pantFile.list();
        System.out.println("arr " + Arrays.toString(names1));
        System.out.println("arr " + Arrays.toString(names2));
        //test to see if it works
        int randShirtIndex = (int)(Math.random() * names1.length);
        int randPantIndex = (int)(Math.random() * names2.length);
        //generate random shirt and pants from folder
//        System.out.println(randShirtIndex);
//        System.out.println(randPantIndex);

//        System.out.println(shirtIndex);
//        System.out.println(pantIndex);

////  dynamically create new imageview w/ clothing image
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
        Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonShirts.get(shirtIndex) + "/" + names1[randShirtIndex]);
        img.setImageBitmap(myBitmap);
//        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img);
        //add the image view to layout to display the outfit generated
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
        Bitmap myBitmap1 = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonPants.get(pantIndex) + "/" + names2[randPantIndex]);
        img1.setImageBitmap(myBitmap1);
//        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img1);
        //repeat the same process to diisplay the other outfit
//        ImageView img1 = new ImageView(layout.getContext());
//        id = 101;
//        img1.setId(id);
//        img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
////                img1.setImageDrawable(getResources().getDrawable(R.drawable.testpants));
//        resId = getResources().getIdentifier(randPants, "drawable", "com.mobileapp.dressme");
//        img1.setImageDrawable(getResources().getDrawable(resId));
//        layout.addView(img1);
        ArrayList<String> result = new ArrayList<String>();
        result.add(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonShirts.get(shirtIndex) + "/" + names1[randShirtIndex]);
        result.add(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + seasonPants.get(pantIndex) + "/" + names2[randPantIndex]);
//        String[] result = {randShirt, randPants};
        return result;
    }
}