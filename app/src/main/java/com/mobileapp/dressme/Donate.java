package com.mobileapp.dressme;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Donate extends Fragment {
    
    Boolean isShirtClicked = false;
    Boolean isPantClicked = false;
    ArrayList<String> allShirts = new ArrayList<>();
    ArrayList<String> allPants = new ArrayList<>();
    ArrayList<ImageView> clickedImgs = new ArrayList<>();
    View view;
    View popUpView;
    PopupWindow popupWindow;
    Button sendDB;
    ArrayList<String> resultShirts = new ArrayList<String>();
    ArrayList<String> resultPants = new ArrayList<String>();
    String result = "";
    Map<Integer, String> shirt_hm = new HashMap<Integer, String>();
    Map<Integer, String> pant_hm = new HashMap<Integer, String>();
    Integer shirtCount = 0;
    Integer pantCount = 0;

    public static Donate newInstance() {
        return new Donate();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donate,
                container, false);
//        ImageView testShirt1 = view.findViewById(R.id.shirt1);
        popUpView = inflater.inflate(R.layout.donatepopup,null);
//        View viewPopUp = inflater.inflate(R.layout.donatepopup,container, false);
//        Button drawBtn = popUpView.findViewById(R.id.popUpDB);
//        Button closetBtn = popUpView.findViewById(R.id.popUpC);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);

        LinearLayout shirtsLL = view.findViewById(R.id.shirtsLL);
        LinearLayout pantsLL = view.findViewById(R.id.pantsLL);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("donatePref", Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().commit();
        if(sharedPreferences.contains("shirt_count")){
           shirtCount = sharedPreferences.getInt("shirt_count", -1);
        }
        if(sharedPreferences.contains("pant_count")){
            pantCount = sharedPreferences.getInt("pant_count", -1);
        }

//        the plus 1 is to account for when there are let's say 2 shirts "shirt1" and "shirt2",
//        if shirt1 is deleted the +1 will make sure to check if there's anything after it
//        to display
        for(int i = 1; i <= shirtCount+1; i++){
            if(sharedPreferences.contains("shirt" + i)){
                shirt_hm.put(i, sharedPreferences.getString("shirt" + i, " "));
                displayShirts(shirtsLL, shirt_hm.get(i));
            }
//            else{
//                shirt_hm.put(i, " ");
//            }
        }

        for(int i = 1; i <= pantCount+1; i++){
            if(sharedPreferences.contains("pant" + i)){
                pant_hm.put(i, sharedPreferences.getString("pant" + i, " "));
                displayPants(pantsLL, pant_hm.get(i));
            }
//            else{
//                pant_hm.put(i, " ");
//            }
        }

//        for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
////            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
//
//        }




//        System.out.println("donate");

        if(!(DonateArgs.fromBundle(getArguments()).getResult() == null)){
//            System.out.println("has arg");
            String item = DonateArgs.fromBundle(getArguments()).getResult();
            SharedPreferences.Editor editor = sharedPreferences.edit();
//            System.out.println(item);
            if(item.contains("Top")){
                shirtCount++;
                editor.putInt("shirt_count", shirtCount);
                editor.commit();

                shirt_hm.put(shirtCount, item);

//                editor = sharedPreferences.edit();
                for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
                    String hashKey = "shirt" + entry.getKey();
                    editor.putString(hashKey, entry.getValue());
                }
                editor.commit();

                displayShirts(shirtsLL, item);
            }
            else if (item.contains("Bottom")){
                pantCount++;
                pant_hm.put(pantCount, item);

                editor.putInt("pant_count", pantCount);
                editor.commit();

                for (Map.Entry<Integer, String> entry : pant_hm.entrySet()) {
                    String hashKey = "pant" + entry.getKey();
                    editor.putString(hashKey, entry.getValue());
                }
                editor.commit();

                displayPants(pantsLL, item);
            }
        }



//        getCloset(shirtsLL, pantsLL);
//        closetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                System.out.println("click");
//                ImageView img = clickedImgs.get(clickedImgs.size()-1);
//                result = (String) img.getTag();
//                img.setVisibility(View.GONE);
//                DonateDirections.ActionDonateToCloset action = DonateDirections.actionDonateToCloset();
//                action.setImageStr(result);
//                Navigation.findNavController(view).navigate(action);
//            }
//        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);

//                System.out.println(popUpImg.getTag());
                //need to set tag of imgs with their filepath or some sort of identifier so i can delete it
                File deleteFile = new File((String) popUpImg.getTag());
                if(((String) popUpImg.getTag()).contains("Top")){
                    int removeKey = 0;
                    for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
                        if(entry.getValue() == popUpImg.getTag()){
                            removeKey = entry.getKey();
                            break;
                        }
                    }
                    shirt_hm.remove(removeKey);
                    shirtCount--;
                    editor.putInt("shirt_count", shirtCount);

                    for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
                        String hashKey = "shirt" + entry.getKey();
                        editor.putString(hashKey, entry.getValue());
                    }
                    editor.commit();
                }
                else if(((String) popUpImg.getTag()).contains("Bottom")){
                    int removeKey = 0;
                    for (Map.Entry<Integer, String> entry : pant_hm.entrySet()) {
                        if(entry.getValue() == popUpImg.getTag()){
                            removeKey = entry.getKey();
                            break;
                        }
                    }
                    pant_hm.remove(removeKey);
                    pantCount--;
                    editor.putInt("shirt_count", pantCount);

                    for (Map.Entry<Integer, String> entry : pant_hm.entrySet()) {
                        String hashKey = "pant" + entry.getKey();
                        editor.putString(hashKey, entry.getValue());
                    }
                    editor.commit();
                }
                if(deleteFile.exists()){
                    if(deleteFile.delete()){
                        Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
//                        getCloset(shirtsLL, pantsLL);
                        ImageView img = clickedImgs.get(clickedImgs.size()-1);
                        img.setVisibility(View.GONE);
                        clickedImgs.clear();

                    }
                    if(deleteFile.getParentFile().exists()){
                        File parentFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + deleteFile.getParentFile().getName());
                        File[] allFiles = parentFile.listFiles();
                        if(allFiles.length == 0){
                            if(parentFile.delete()){
                                System.out.println("deleted folder");
                            }

                        }
//
                    }
                }
                popupWindow.dismiss();
            }
        });

//        testShirt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                System.out.println("click");
////                testShirt1.setVisibility(View.GONE);
////                testShirt1.setImageDrawable(null);
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

//    private void getCloset(LinearLayout shirtsLL, LinearLayout pantsLL) {
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/");
//
//        String[] names = file.list();
//        for(String name : names)
//        {
//            if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name).isDirectory())
//            {
//                if(name.contains("top")){
//                    allShirts.add(name);
////                    if(name.contains(radioText)){
////                        folderNames.add(name);
////                    }
//                }
//                if(name.contains("bottom")){
//                    allPants.add(name);
////                    if(name.contains(radioText)){
////                        folderNames.add(name);
////                    }
//                }
//            }
//        }
//        displayShirts(shirtsLL, allShirts);
//        displayPants(pantsLL, allPants);
//        if(!(DonateArgs.fromBundle(getArguments()).getResult() == null)){
//            ImageView iv = shirtsLL.findViewWithTag(DonateArgs.fromBundle(getArguments()).getResult());
//            iv.setVisibility(View.VISIBLE);
//        }
//    }

    private void displayPants(LinearLayout linearLayout, String item) {
//        for (int i = 0; i < allPants.size()-1; i++) {
            File aFolder = new File(item);
            String[] files = aFolder.list();

//            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 3000;
                img.setId(id);
                img.setTag(item);
//                System.out.println(name);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                img.setRotation(90);
//
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
                String imgPath = item;

                Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
                img.setImageBitmap(myBitmap);
//        img.setImageDrawable(getResources().getDrawable(resId));
                linearLayout.addView(img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPopUp(myBitmap, imgPath);
                        isPantClicked = true;
                        resultPants.add(imgPath);
                        clickedImgs.add((ImageView) v);
//                        if(isShirtClicked && isPantClicked){
//                            sendDB.setEnabled(true);
//                        }
                    }
                });
            }

//        }
//    }

    private void displayPopUp(Bitmap myBitmap, String imgPath) {
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);
                popUpImg.setRotation(90);
                popUpImg.setTag(imgPath);
                popUpImg.setImageBitmap(myBitmap);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });
    }


    private void displayShirts(LinearLayout linearLayout, String item) {
//        for (int i = 0; i < allShirts.size()-1; i++) {
//        System.out.println(item);
//        /data/media/0/Pictures/TopSummer/1682705965737.jpg
//        /data/media/0/Pictures/TopSummer/1682705965737.jpg
//            File aFolder = new File(item);
//            String[] files = aFolder.list();
//            System.out.println(Arrays.toString(files));
//            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 2000;
                img.setId(id);
                img.setTag(item);
                //System.out.println(img.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                String imgPath = item;
                Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
//                System.out.println(allShirts.get(i) + "/" + name);
                img.setImageBitmap(myBitmap);
//        img.setImageDrawable(getResources().getDrawable(resId));
                img.setRotation(90);
                linearLayout.addView(img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPopUp(myBitmap, imgPath);
                        clickedImgs.add((ImageView) v);
//                        isShirtClicked = true;
//                        resultShirts.add(imgPath);
//                        if(isShirtClicked && isPantClicked){
//                            sendDB.setEnabled(true);
//                        }
                    }
                });
//            }
    }
    }
//    }
