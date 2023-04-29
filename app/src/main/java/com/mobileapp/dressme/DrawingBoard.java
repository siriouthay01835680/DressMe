package com.mobileapp.dressme;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DrawingBoard extends Fragment {

    private DrawingBoardViewModel mViewModel;

    float x, y;
    float dx, dy;
    Button scrapBtn;
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

        if(shirts != null && pants != null){
            displayItems(shirts, pants, layout);
        }
        else{
            TextView noItems = view.findViewById(R.id.noItems);
            noItems.setVisibility(View.VISIBLE);
            scrapBtn.setEnabled(false);
        }

        DrawingBoardDirections.ActionDrawingBoardToScrapbook action = DrawingBoardDirections.actionDrawingBoardToScrapbook();
        action.setDbShirts(shirts);
        action.setDbPants(pants);

        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void displayItems(String[] shirts, String[] pants, LinearLayout layout){
        //change to switch
        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(500,500);
        if(shirts.length != 0){
            for(int i = 0; i < shirts.length; i++){
//                System.out.println("here1");
                //dynamically create new imageview w/ clothing image
                ImageView img = new ImageView(layout.getContext());
                int id = 200 + i;
                img.setId(id);
                img.setLayoutParams(lp);
                Bitmap myBitmap = BitmapFactory.decodeFile(shirts[i]);
                img.setImageBitmap(myBitmap);
                img.setRotation(90);
                layout.addView(img);

                img.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
//                        System.out.println("touch");
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            x = event.getRawX();
                            y = event.getRawY();
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
                layout.addView(img);
                img.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        System.out.println("touch");
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            x = event.getRawX();
                            y = event.getRawY();
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
        if(shirts.length > 1 && pants.length > 1){
            Toast.makeText(getContext(), "You can only send one top and bottom to the Scrapbook.", Toast.LENGTH_LONG).show();
            scrapBtn.setEnabled(false);
        }
    }




}
