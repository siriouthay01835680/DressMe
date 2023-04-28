package com.mobileapp.dressme;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.DragEvent;
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


        ImageView trashBin = view.findViewById(R.id.trashbin);

        trashBin.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        System.out.println("in drag entered");
                        // Set the ImageView's background to indicate that it is a valid drop target
                        //v.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_drag_target));
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        // Reset the ImageView's background
                        System.out.println("in drag exited");
                        v.setBackground(null);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Retrieve the dragged image from the DragEvent
                        ImageView imageView = (ImageView) event.getLocalState();
                        System.out.println(imageView.getId());

                        // Remove the image from the view hierarchy
                        ViewGroup parent = (ViewGroup) imageView.getParent();
                        parent.removeView(imageView);

                        // Reset the ImageView's background
                        v.setBackground(null);
                        break;
                }
                return true;
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
        if(shirts.length == 0 && pants.length == 0){
            TextView txt = new TextView(layout.getContext());
            txt.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
            txt.setText("No clothing items, try heading to your closet to get started!");
            layout.addView(txt);
        }
    }




}
