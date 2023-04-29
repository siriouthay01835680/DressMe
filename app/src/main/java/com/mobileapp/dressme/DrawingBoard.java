package com.mobileapp.dressme;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//imported libraries

public class DrawingBoard extends Fragment {


    float x, y;
    float dx, dy;
    Button scrapBtn;
    String[] finalShirt;
    String[] finalPant;
    //create arrays to store all items sent to the drawing board
    DrawingBoardDirections.ActionDrawingBoardToScrapbook action;
    //set up the navigation from drawing board to the scrapbook
    public static DrawingBoard newInstance() {
        return new DrawingBoard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing_board,
                container, false);

        String[] shirts = DrawingBoardArgs.fromBundle(getArguments()).getShirts();
        String[] pants = DrawingBoardArgs.fromBundle(getArguments()).getPants();
        //adding the items taken from the closet to the corresponding
        //top and bottom arrays
        LinearLayout layout = view.findViewById(R.id.linearLayout);
        scrapBtn = view.findViewById(R.id.scrapButton);
        finalShirt = shirts;
        finalPant = pants;
        //setting up variables for later on
        if(shirts != null && pants != null){
            displayItems(shirts, pants, layout);
            //if there are items present in the array, navigate to the display function to display them
        }
        else{
            TextView noItems = view.findViewById(R.id.noItems);
            noItems.setVisibility(View.VISIBLE);
            scrapBtn.setEnabled(false);
            //if there are no items sent to the drawing board,
            //disable the send to scrap book button and let the user know it is empty
        }

        action = DrawingBoardDirections.actionDrawingBoardToScrapbook();
        if(finalPant.length == 1 && finalShirt.length == 1){
            //only send to scrapbook one item from top and one item from bottom
            action.setDbShirts(finalShirt);
            action.setDbPants(finalPant);
            //if there are only one item of each present in the drawing board
            //enable the send to scrapbook button
            scrapBtn.setEnabled(true);
        }

        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(action);
            }
            //when scrap button is pressed, navigate to the scrapbook
        });




// Helper function to check if two views are overlapping at a given location

        return view;
    }

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    public void displayItems(String[] shirts, String[] pants, LinearLayout layout){
        //change to switch

        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(500,500);
        if(shirts.length != 0){
            for(int i = 0; i < shirts.length; i++){
                //dynamically creating the image views for each item that is
                //sent here
//                System.out.println("here1");
//                dynamically create new imageview w/ clothing image
                ImageView img = new ImageView(layout.getContext());
                int id = 200 + i;
                //give the new created image view an ID and set the ID
                img.setId(id);
                img.setLayoutParams(lp);
                Bitmap myBitmap = BitmapFactory.decodeFile(shirts[i]);
                //Add the image view to a bitmap and set its rotation
                img.setImageBitmap(myBitmap);
                img.setRotation(90);
//                img.setTag("view_to_delete");
                img.setTag(shirts[i]);
                //set tage and add the image view for shirts to the layout
                layout.addView(img);

                //enable drag and drop feature
                img.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
//                        System.out.println("touch");
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            x = event.getRawX();
                            y = event.getRawY();
                            //testing to see if coordinates are updating properly
                            System.out.println(x);
                            System.out.println(y);
                            //setting up an on touch listener for the tshirt

                            return true;
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            x = event.getRawX();
                            y = event.getRawY();
                            //testing to see if coordinates are updating properly
                            System.out.println(x);
                            System.out.println(y);
                            //using coordinates for trash bin feature

                            if((x >= 1055 && x <= 1360) && (y >= 1500 && y <= 2240))
                            {
                                //if the shirt is anywhere near the trash bin, once the user releases
                                //the item on the trash can, it will delete.
                                finalShirt = shirts;
                                System.out.println(Arrays.toString(finalShirt));
                                String tag = (String) img.getTag();
                                if(tag.contains("Top")){
                                    //creates list to hold all the tops
                                    List<String> list = new ArrayList<String>(Arrays.asList(finalShirt));
                                    list.remove(tag);
                                    finalShirt = list.toArray(new String[0]);
//                                    action.setDbShirts(finalShirt);
                                    System.out.println(Arrays.toString(finalShirt));
                                    //add to array and test that it is working
                                    layout.removeView(img);
                                    //delete the clothing item if they touch the trash bin
                                    if(finalShirt.length == 0){
//                                        Toast.makeText(getContext(), "You can only send one top and bottom to the Scrapbook.", Toast.LENGTH_LONG).show();
                                        scrapBtn.setEnabled(false);
                                        //if there are no shirts displayed, disable the scrap book button
                                    }
                                    if(finalPant.length == 1 && finalShirt.length == 1){
                                        action.setDbShirts(finalShirt);
                                        action.setDbPants(finalPant);
                                        //if there are only one pair of pants and top, then enable the scrap book button
                                        scrapBtn.setEnabled(true);
                                    }

                                }

                            }
                            return true;
                        } //SAME PROCESS FROM ABOVE IS DONE BELOW FOR PANTS
                        //SAME STEPS ARE TAKEN TO DELETE AN ITEM/SAVE THE ITEM AND SEND TO SCRAPBOOK
                        if(event.getAction() == MotionEvent.ACTION_MOVE){
                            dx = event.getRawX() - x;
                            dy = event.getRawY() - y;

                            v.setX(v.getX() + dx);
                            v.setY(v.getY() + dy);

                            x = event.getRawX();
                            y = event.getRawY();

                            return true;
                        }
                        return false;
                    }
                });
            }
        }
        if(pants.length != 0){
            for(int i = 0; i < pants.length; i++){
                //dynamically create new imageview w/ clothing image
                ImageView img = new ImageView(layout.getContext());
                int id = 300 + i;
                img.setId(id);
                img.setLayoutParams(lp);
                Bitmap myBitmap = BitmapFactory.decodeFile(pants[i]);
                img.setImageBitmap(myBitmap);
                img.setRotation(90);
//                img.setTag("view_to_delete");
                img.setTag(pants[i]);
                layout.addView(img);
                img.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        System.out.println("touch");
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            x = event.getRawX();
                            y = event.getRawY();

                            System.out.println(x);
                            System.out.println(y);

                            return true;
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            x = event.getRawX();
                            y = event.getRawY();
                            System.out.println(x);
                            System.out.println(y);

                            if((x >= 1055 && x <= 1360) && (y >= 1500 && y <= 2240))
                            {
                                finalPant = pants;

                                System.out.println(Arrays.toString(finalPant));
                                String tag = (String) img.getTag();
                                if(tag.contains("Bottom")){
                                    List<String> list = new ArrayList<String>(Arrays.asList(finalPant));
                                    list.remove(tag);
                                    finalPant = list.toArray(new String[0]);
                                    action.setPants(finalPant);
                                    System.out.println(Arrays.toString(finalPant));
                                    layout.removeView(img);
                                }
                                layout.removeView(img);
                                if(finalPant.length == 0){
                                    scrapBtn.setEnabled(false);
                                }
                                if(finalPant.length == 1 && finalShirt.length == 1){
                                    action.setDbShirts(finalShirt);
                                    action.setDbPants(finalPant);
                                    scrapBtn.setEnabled(true);
                                }
//                                if(finalPant.length == 1 && finalShirt.length == 1){
//                                    action.setDbShirts(finalShirt);
//                                    action.setDbPants(finalPant);
//                                    scrapBtn.setEnabled(true);
//                                }
                            }

                        }
                        if(event.getAction() == MotionEvent.ACTION_MOVE){
                            dx = event.getRawX() - x;
                            dy = event.getRawY() - y;

                            v.setX(v.getX() + dx);
                            v.setY(v.getY() + dy);

                            x = event.getRawX();
                            y = event.getRawY();

                            return true;
                        }
                        return false;
                    }
                });
            }

        }//makes sure only one top and bottom are sent to the scrapbook and
        //notifies the user
        if(shirts.length > 1 && pants.length > 1){
            Toast.makeText(getContext(), "You can only send one top and bottom to the Scrapbook.", Toast.LENGTH_LONG).show();
            scrapBtn.setEnabled(false);
        }
    }




}
