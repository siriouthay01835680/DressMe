package com.mobileapp.dressme;

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
import androidx.navigation.Navigation;

import java.io.File;
import java.util.ArrayList;

public class Closet extends Fragment {

    ClosetDirections.ActionClosetToDrawingBoard actionDB;
    ArrayList<String> allShirts = new ArrayList<>();
    ArrayList<String> allPants = new ArrayList<>();
    ArrayList<ImageView> clickedImgs = new ArrayList<>();
    View view;
    View popUpView;
    PopupWindow popupWindow;
    Button sendDB;
    ArrayList<String> resultShirts = new ArrayList<String>();
    ArrayList<String> resultPants = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //get connection to closet fragment and its linear layouts
        view = inflater.inflate(R.layout.fragment_closet,
                container, false);
        LinearLayout shirtsLL = view.findViewById(R.id.shirtsLL);
        LinearLayout pantsLL = view.findViewById(R.id.pantsLL);

        //get connection to the popup that will appear when clicking clothing items
        popUpView = inflater.inflate(R.layout.mainpopup,null);
        Button drawBtn = popUpView.findViewById(R.id.popUpDB);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);
        Button donateBtn = popUpView.findViewById(R.id.popUpDonate);

        //get connection to the drawing board button
        sendDB = view.findViewById(R.id.drawBrdBtn);

        //ensure that the array lists to hold shirt and pants results are clear.
        resultShirts.clear();
        resultPants.clear();

        //function to display all images in picture folders
        getCloset(shirtsLL, pantsLL);

        //initalizing the action for closet to drawing board
        actionDB = ClosetDirections.actionClosetToDrawingBoard();

        //on click listener for drawing board, when clicked it will
        //have the popup appear along with its different options for
        //the image
        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //all images have a tag of their file path
                ImageView img = popUpView.findViewById(R.id.popupImg);
                String tag = (String) img.getTag();
                //if item is a shirt and it is not already in results, save it.
                if(tag.contains("Top") && !resultShirts.contains(tag)){
                    resultShirts.add(tag);
                }
                //if item is a bottom and it is not already in results, save it.
                else if(tag.contains("Bottom") && !resultPants.contains(tag)){
                    resultPants.add(tag);
                }
                //if the results are not empty, save their contents to array and
                //send to drawing board.
                if(resultShirts.size() != 0 && resultPants.size() != 0){
                    String[] shirtArr = new String[resultShirts.size()];
                    shirtArr = resultShirts.toArray(shirtArr);
                    String[] pantArr = new String[resultPants.size()];
                    pantArr = resultPants.toArray(pantArr);
                    actionDB.setShirts(shirtArr);
                    actionDB.setPants(pantArr);
                }
                //prompt to ensure that the user sends a top and bottom to drawing board.
                else{
                    Toast.makeText(getContext(), "Make sure at least one top and bottom are sent to the Drawing Board", Toast.LENGTH_LONG).show();

                }
            }
        });

        //when going to drawing board, clear all arraylists and navigate.
        sendDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedImgs.clear();
                resultShirts.clear();
                resultPants.clear();
                Navigation.findNavController(view).navigate(actionDB);

            }
        });

        //on click listener for delete button in popup
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the image's path in its tag
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);
                //search for the file given the path
                File deleteFile = new File((String) popUpImg.getTag());
                //if file exists delete it and prompt user
                if(deleteFile.exists()){
                    if(deleteFile.delete()){
                        Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                            ImageView img = clickedImgs.get(clickedImgs.size()-1);
                            //make image invisible until app is reloaded to fully delete image.
                            img.setVisibility(View.GONE);
                            clickedImgs.clear();
                    }
                    //if the parent file of the deleted file is now empty, delete it as well.
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
                //dismiss the popup window after clicked.
                popupWindow.dismiss();
            }
        });

        //on click listener for donation button in popup
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the file path and send it over to the donation page.
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);
                String tag = (String) popUpImg.getTag();
                ClosetDirections.ActionClosetToDonate action = ClosetDirections.actionClosetToDonate();
                action.setResult(tag);
                Navigation.findNavController(view).navigate(action);

            }
        });
        return view;
    }

    //function to display all items in closet.
    private void getCloset(LinearLayout shirtsLL, LinearLayout pantsLL) {
        //get the main picture file and list all its contents
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/");
        String[] names = file.list();

        //get all subdirectories inside of main picture folder and organize them
        //according to if theyre a top or bottom
        for(String name : names) {
            if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name).isDirectory())
            {
                if(name.contains("Top")){
                    allShirts.add(name);
                }
                if(name.contains("Bottom")){
                    allPants.add(name);
                }
            }
        }
        //display the tops and bottoms
        displayShirts(shirtsLL, allShirts);
        displayPants(pantsLL, allPants);
    }

    //function to display all shirts
    private void displayShirts(LinearLayout linearLayout, ArrayList<String> allShirts) {
        //iterate through all shirt folders
        for (int i = 0; i < allShirts.size()-1; i++) {
            File aFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i));
            String[] files = aFolder.list();
            //for all images in the shirt folders, display them to imageviews.
            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                //giving each a random id.
                int id = 2000 + i;
                img.setId(id);
                //setting their size and margins
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);

                //setting img tag as its image path
                String imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i) + "/" + name;
                img.setTag(imgPath);

                //getting bitmap and setting it to the image to display the image file.
                Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
                img.setImageBitmap(myBitmap);

                //rotating the image to it is in the correct orientation.
                img.setRotation(90);

                //finally add the image to the linear layout.
                linearLayout.addView(img);

                //add an on click listener for each image for the popup
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPopUp(myBitmap, imgPath);
                        //keep track of the most recently clicked image.
                        clickedImgs.add((ImageView) v);
                    }
                });
            }

        }
    }

    //function to display all pants, same fashion as previous function
    private void displayPants(LinearLayout linearLayout, ArrayList<String> allPants) {
        //iterate through all pants folders and get their contents
        for (int i = 0; i < allPants.size()-1; i++) {
            File aFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i));
            String[] files = aFolder.list();
            for(String name : files){
                //save the image files within folders to an imageview.
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 3000 + i;
                img.setId(id);

                //setting the size, margins, and rotation to the images
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                img.setRotation(90);

                //setting the images tag
                String imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name;
                img.setTag(imgPath);

                //saving the image file to a bitmap and then displaying bitmap to imageview.
                Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
                img.setImageBitmap(myBitmap);

                //adding the imageview to the linear layout
                linearLayout.addView(img);

                //on click listener for all images for popup window
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPopUp(myBitmap, imgPath);
                        clickedImgs.add((ImageView) v);
                    }
                });
            }

        }
    }

    //function to display popups
    private void displayPopUp(Bitmap myBitmap, String imgPath){
        //set the params to wrap content
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        //creating a new popup window
        popupWindow = new PopupWindow(popUpView, width, height, focusable);

        //if "go back" text is clicked, make popup go away.
        TextView popUpTxt = popUpView.findViewById(R.id.goback);
        popUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //displaying the popup to the view
        view.post(new Runnable() {
            @Override
            public void run() {
                //displaying the clicked image to the popup
                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);
                popUpImg.setRotation(90);
                popUpImg.setTag(imgPath);
                popUpImg.setImageBitmap(myBitmap);
                //displaying popup in center of screen
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });
    }
}