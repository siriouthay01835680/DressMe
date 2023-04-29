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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class DrawingBoard extends Fragment {

    private DrawingBoardViewModel mViewModel;

    float x, y;
    float dx, dy;
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
        displayItems(shirts, pants, layout);

        DrawingBoardDirections.ActionDrawingBoardToScrapbook action = DrawingBoardDirections.actionDrawingBoardToScrapbook();
        action.setDbShirts(shirts);
        action.setDbPants(pants);

        Button scrapBtn = view.findViewById(R.id.scrapButton);
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
        ImageView trashbin = getView().findViewById(R.id.trashbin);
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
                img.setTag("view_to_delete");
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
                                trashbin.setImageResource(R.drawable.trashbin);
                                layout.removeView(img);
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
                img.setTag("view_to_delete");
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
                                layout.removeView(img);
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
        if(shirts.length == 0 && pants.length == 0){
            TextView txt = new TextView(layout.getContext());
            txt.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
            txt.setText("No clothing items, try heading to your closet to get started!");
            layout.addView(txt);
        }
    }




}
