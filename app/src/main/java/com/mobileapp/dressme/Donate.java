package com.mobileapp.dressme;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import java.io.File;
import java.util.ArrayList;

public class Donate extends Fragment {

    private DonateViewModel mViewModel;
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
        Button closetBtn = popUpView.findViewById(R.id.popUpC);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);

//        drawBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("click");
//                Navigation.findNavController(view).navigate(R.id.action_closet_to_drawingBoard);
//            }
//        });
//        scrapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("click");
//                Navigation.findNavController(view).navigate(R.id.action_closet_to_scrapbook);
//            }
//        });

        LinearLayout shirtsLL = view.findViewById(R.id.shirtsLL);
        LinearLayout pantsLL = view.findViewById(R.id.pantsLL);
        getCloset(shirtsLL, pantsLL);
        closetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("click");
                ImageView img = clickedImgs.get(0);
                result = (String) img.getTag();
                img.setVisibility(View.GONE);
                DonateDirections.ActionDonateToCloset action = DonateDirections.actionDonateToCloset();
                action.setImageStr(result);
                Navigation.findNavController(view).navigate(action);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView popUpImg = popUpView.findViewById(R.id.popupImg);

//                System.out.println(popUpImg.getTag());
                //need to set tag of imgs with their filepath or some sort of identifier so i can delete it
                File deleteFile = new File((String) popUpImg.getTag());
                if(deleteFile.exists()){
                    if(deleteFile.delete()){
                        Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
//                        getCloset(shirtsLL, pantsLL);
                        ImageView img = clickedImgs.get(0);
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

    private void getCloset(LinearLayout shirtsLL, LinearLayout pantsLL) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/");

        String[] names = file.list();
        for(String name : names)
        {
            if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name).isDirectory())
            {
                if(name.contains("top")){
                    allShirts.add(name);
//                    if(name.contains(radioText)){
//                        folderNames.add(name);
//                    }
                }
                if(name.contains("bottom")){
                    allPants.add(name);
//                    if(name.contains(radioText)){
//                        folderNames.add(name);
//                    }
                }
            }
        }
        displayShirts(shirtsLL, allShirts);
        displayPants(pantsLL, allPants);
        if(!(DonateArgs.fromBundle(getArguments()).getResult() == null)){
            ImageView iv = shirtsLL.findViewWithTag(DonateArgs.fromBundle(getArguments()).getResult());
            iv.setVisibility(View.VISIBLE);
        }
    }

    private void displayPants(LinearLayout linearLayout, ArrayList<String> allPants) {
        for (int i = 0; i < allPants.size()-1; i++) {
            File aFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i));
            String[] files = aFolder.list();

            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 3000 + i;
                img.setId(id);
                img.setTag(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name);
//                System.out.println(name);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                img.setRotation(90);
//
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
                String imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name;

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
                        if(isShirtClicked && isPantClicked){
                            sendDB.setEnabled(true);
                        }
                    }
                });
            }

        }
    }

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


    private void displayShirts(LinearLayout linearLayout, ArrayList<String> allShirts) {
        for (int i = 0; i < allShirts.size()-1; i++) {
            File aFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i));
            String[] files = aFolder.list();
            //System.out.println(Arrays.toString(files));
            for(String name : files){
                ImageView img = new ImageView(linearLayout.getContext());
                int id = 2000 + i;
                img.setId(id);
                img.setTag(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i) + "/" + name);
                //System.out.println(img.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                String imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i) + "/" + name;
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
                        isShirtClicked = true;
                        resultShirts.add(imgPath);
                        if(isShirtClicked && isPantClicked){
                            sendDB.setEnabled(true);
                        }
                    }
                });
            }
    }
    }
    }
