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
    ArrayList<ImageView> clickedImgs = new ArrayList<>();
    View view;
    View popUpView;
    PopupWindow popupWindow;
    Map<Integer, String> shirt_hm = new HashMap<Integer, String>();
    Map<Integer, String> pant_hm = new HashMap<Integer, String>();
    Integer shirtCount = 0;
    Integer pantCount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //get connection to donation fragment layout and its linear layouts
        view = inflater.inflate(R.layout.fragment_donate, container, false);
        LinearLayout shirtsLL = view.findViewById(R.id.shirtsLL);
        LinearLayout pantsLL = view.findViewById(R.id.pantsLL);

        //get connection to the donation popup layout and its delete buttons
        popUpView = inflater.inflate(R.layout.donatepopup,null);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);

        //initalizing shared preferences for the donation screen
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("donatePref", Context.MODE_PRIVATE);

        //check to see if shirt count is available
        if(sharedPreferences.contains("shirt_count")){
           shirtCount = sharedPreferences.getInt("shirt_count", -1);
        }
        //check to see if pants count is available
        if(sharedPreferences.contains("pant_count")){
            pantCount = sharedPreferences.getInt("pant_count", -1);
        }

        //iterate through all shirts in shared pref to save previous images
        //the plus 1 is to account for when there are let's say 2 shirts "shirt1" and "shirt2",
        //if shirt1 is deleted the +1 will make sure to check if there's anything after it
        //to display
        for(int i = 1; i <= shirtCount+1; i++){
            if(sharedPreferences.contains("shirt" + i)){
                //saving shared pref values into shirt hash map
                shirt_hm.put(i, sharedPreferences.getString("shirt" + i, " "));
                displayShirts(shirtsLL, shirt_hm.get(i));
            }
        }

        //iterate through all pants in shared pref to restore previous images
        for(int i = 1; i <= pantCount+1; i++){
            if(sharedPreferences.contains("pant" + i)){
                //saving shared pref values into pant hash map
                pant_hm.put(i, sharedPreferences.getString("pant" + i, " "));
                displayPants(pantsLL, pant_hm.get(i));
            }
        }

        //if the args from the closet exist
        if(!(DonateArgs.fromBundle(getArguments()).getResult() == null)){
            String item = DonateArgs.fromBundle(getArguments()).getResult();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //write them to shared pref according to if their a top or bottom
            if(item.contains("Top")){
                shirtCount++;
                editor.putInt("shirt_count", shirtCount);
                editor.commit();
                //save it to the hashmap
                shirt_hm.put(shirtCount, item);

                //iterate through the hash map and save it all to shared pref
                for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
                    String hashKey = "shirt" + entry.getKey();
                    editor.putString(hashKey, entry.getValue());
                }
                //save changes
                editor.commit();

                //display the new image
                displayShirts(shirtsLL, item);
            }
            //if the image is a bottom, follow the same scheme as above.
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

        //on click listener for delete button in popup
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);

                //get the file path from the tag
                File deleteFile = new File((String) popUpImg.getTag());

                //if its a top, remove it from the shirt hashmap
                if(((String) popUpImg.getTag()).contains("Top")){
                    int removeKey = 0;
                    for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
                        if(entry.getValue() == popUpImg.getTag()){
                            removeKey = entry.getKey();
                            break;
                        }
                    }

                    //update the changes to the shared pref by updating count and its hash map
                    shirt_hm.remove(removeKey);
                    shirtCount--;
                    editor.putInt("shirt_count", shirtCount);

                    for (Map.Entry<Integer, String> entry : shirt_hm.entrySet()) {
                        String hashKey = "shirt" + entry.getKey();
                        editor.putString(hashKey, entry.getValue());
                    }
                    editor.commit();
                }
                //same process as above with pants.
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
                //delete the clicked file
                if(deleteFile.exists()){
                    if(deleteFile.delete()){
                        Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        //delete the most recently clicked image
                        ImageView img = clickedImgs.get(clickedImgs.size()-1);
                        img.setVisibility(View.GONE);
                        clickedImgs.clear();

                    }
                    //delete parent folder if now empty
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
        return view;
    }

    //function to display the pants
    private void displayPants(LinearLayout linearLayout, String item) {
        //create image view, set layout params, id, and save it to image view
        ImageView img = new ImageView(linearLayout.getContext());
        int id = 3000;
        img.setId(id);
        img.setTag(item);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
        lp.setMargins(10, 10, 10, 10);
        img.setLayoutParams(lp);
        img.setRotation(90);

        String imgPath = item;
        Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
        img.setImageBitmap(myBitmap);

        linearLayout.addView(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopUp(myBitmap, imgPath);
                clickedImgs.add((ImageView) v);
            }
        });
    }

    //function to display popups
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


    //function to display shirts
    private void displayShirts(LinearLayout linearLayout, String item) {
        //display image, set id, tag, layout margins, save image to imageview.
        ImageView img = new ImageView(linearLayout.getContext());
        int id = 2000;
        img.setId(id);
        img.setTag(item);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
        lp.setMargins(10, 10, 10, 10);
        img.setLayoutParams(lp);
        String imgPath = item;
        Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
        img.setImageBitmap(myBitmap);
        img.setRotation(90);
        linearLayout.addView(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopUp(myBitmap, imgPath);
                clickedImgs.add((ImageView) v);
            }
        });
    }
}

