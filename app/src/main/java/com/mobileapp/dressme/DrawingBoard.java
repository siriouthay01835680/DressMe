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


public class DrawingBoard extends Fragment {


    float x, y;
    float dx, dy;
    Button scrapBtn;
    String[] finalShirt;
    String[] finalPant;
    DrawingBoardDirections.ActionDrawingBoardToScrapbook action;

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
        LinearLayout layout = view.findViewById(R.id.linearLayout);
        scrapBtn = view.findViewById(R.id.scrapButton);
        finalShirt = shirts;
        finalPant = pants;

        if(shirts != null && pants != null){
            displayItems(shirts, pants, layout);
        }
        else{
            TextView noItems = view.findViewById(R.id.noItems);
            noItems.setVisibility(View.VISIBLE);
            scrapBtn.setEnabled(false);
        }

        action = DrawingBoardDirections.actionDrawingBoardToScrapbook();
        if(finalPant.length == 1 && finalShirt.length == 1){
            action.setDbShirts(finalShirt);
            action.setDbPants(finalPant);
            scrapBtn.setEnabled(true);
        }

        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(action);
            }
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
//                System.out.println("here1");
//                dynamically create new imageview w/ clothing image
                ImageView img = new ImageView(layout.getContext());
                int id = 200 + i;
                img.setId(id);
                img.setLayoutParams(lp);
                Bitmap myBitmap = BitmapFactory.decodeFile(shirts[i]);
                img.setImageBitmap(myBitmap);
                img.setRotation(90);
//                img.setTag("view_to_delete");
                img.setTag(shirts[i]);
                layout.addView(img);


                img.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
//                        System.out.println("touch");
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
                                finalShirt = shirts;
                                System.out.println(Arrays.toString(finalShirt));
                                String tag = (String) img.getTag();
                                if(tag.contains("Top")){
                                    List<String> list = new ArrayList<String>(Arrays.asList(finalShirt));
                                    list.remove(tag);
                                    finalShirt = list.toArray(new String[0]);
//                                    action.setDbShirts(finalShirt);
                                    System.out.println(Arrays.toString(finalShirt));
                                    layout.removeView(img);
                                    if(finalShirt.length == 0){
//                                        Toast.makeText(getContext(), "You can only send one top and bottom to the Scrapbook.", Toast.LENGTH_LONG).show();
                                        scrapBtn.setEnabled(false);
                                    }
                                    if(finalPant.length == 1 && finalShirt.length == 1){
                                        action.setDbShirts(finalShirt);
                                        action.setDbPants(finalPant);
                                        scrapBtn.setEnabled(true);
                                    }

                                }

                            }
                            return true;
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

        }
        if(shirts.length > 1 && pants.length > 1){
            Toast.makeText(getContext(), "You can only send one top and bottom to the Scrapbook.", Toast.LENGTH_LONG).show();
            scrapBtn.setEnabled(false);
        }
    }




}
