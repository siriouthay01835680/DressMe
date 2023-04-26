package com.mobileapp.dressme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Closet extends Fragment {

    private ClosetViewModel mViewModel;
    String[] shirts = {};
    String[] pants = {};
    ClosetDirections.ActionClosetToDrawingBoard actionDB;
    Boolean isShirtClicked = false;
    Boolean isPantClicked = false;
    ArrayList<String> allShirts = new ArrayList<>();
    ArrayList<String> allPants = new ArrayList<>();
    ArrayList<ImageView> clickedImgs = new ArrayList<>();
    View view;
    View popUpView;
    PopupWindow popupWindow;
    public static Closet newInstance() {
        return new Closet();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        if(savedInstanceState != null){
//            savedInstanceState.getStringArray("shirts");
//            savedInstanceState.getStringArray("pants");
//            shirts = shirts;
//            pants = pants;
//            System.out.println("restored");
//        }
        view = inflater.inflate(R.layout.fragment_closet,
                container, false);
//        ImageView testShirt1 = view.findViewById(R.id.shirt1);

        popUpView = inflater.inflate(R.layout.mainpopup,null);
//        View viewPopUp = inflater.inflate(R.layout.mainpopup,
//                container, false);
        Button drawBtn = popUpView.findViewById(R.id.popUpDB);
//        Button scrapBtn = popUpView.findViewById(R.id.popUpSB);
        Button deleteBtn = popUpView.findViewById(R.id.popUpDelete);
        Button donateBtn = popUpView.findViewById(R.id.popUpDonate);

        Button sendDB = view.findViewById(R.id.drawBrdBtn);
        Button refresh = view.findViewById(R.id.refreshBtn);

        LinearLayout shirtsLL = view.findViewById(R.id.shirtsLL);
        LinearLayout pantsLL = view.findViewById(R.id.pantsLL);

        //disable send to drawing board btn if no shirt & pant is not already sent
        sendDB.setEnabled(false);

        //implement refresh function to check folders and update accordingly,
        //should follow this following outline:

        //read from all files that contain tops


        //refresh closet
        
       getCloset(shirtsLL, pantsLL);
//        System.out.println(allShirts);
        //display each img
        //set on click listeners on imgs for pop ups
        //implement sending img to different fragments
        //implement delete funcitonality; check to see if delete will make folder empty

        //read from all files thar contain bottoms
        //repeat above

//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
////                transaction.add(R.id.closet, new Closet());
////                transaction.commit();
////
////                //final FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
////                Fragment current = requireActivity().getSupportFragmentManager().findFragmentById(R.id.closet);
////                transaction.detach(current).attach(current).commit();
////                requireActivity().getSupportFragmentManager().beginTransaction().detach(Closet.this).commit();
////
////                requireActivity().getSupportFragmentManager().beginTransaction().attach(Closet.this).commit();
////                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.closet, new Closet()).commit();
////                getChildFragmentManager()
////                        .beginTransaction()
////                        .detach(Closet.this)
////                        .attach(Closet.this)
////                        .addToBackStack(null)
////                        .commit();
//                HorizontalScrollView shirtView = view.findViewById(R.id.horizontalScrollView);
//                HorizontalScrollView pantView = view.findViewById(R.id.horizontalScrollView2);
//                FragmentManager fragmentManager = requireParentFragment().getParentFragmentManager();
//                final Fragment current = fragmentManager.findFragmentById(R.id.closet);
//                if(current == null || !(current instanceof Closet)) {
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.closet, Closet.newInstance())
//                            .commitAllowingStateLoss();
//
//
//                }
//            }
//        });

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDB = ClosetDirections.actionClosetToDrawingBoard(shirts, pants);
            }
        });
        sendDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(actionDB);

            }
        });
//        scrapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("click");
//                Navigation.findNavController(view).navigate(R.id.action_closet_to_scrapbook);
//            }
//        });
        //
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                testShirt1.setVisibility(View.GONE);
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
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                testShirt1.setVisibility(View.GONE);
            }
        });

//        testShirt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isShirtClicked = true;
//                if(isShirtClicked && isPantClicked){
//                    sendDB.setEnabled(true);
//                }
////                System.out.println("click");
////                testShirt1.setVisibility(View.GONE);
////                testShirt1.setImageDrawable(null);
//                String newArr[] = new String[shirts.length + 1];
//                for(int i = 0; i < shirts.length; i++){
//                    newArr[i] = shirts[i];
//                }
////                newArr[shirts.length] = testShirt1.getResources().getResourceEntryName(testShirt1.getId());
//                newArr[pants.length] = String.valueOf(testShirt1.getTag());
//                shirts = newArr;
////                System.out.println(Arrays.toString(shirts));
////                System.out.println(testShirt1.getResources().getResourceEntryName(testShirt1.getId()));
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
//        ImageView pant1 = view.findViewById(R.id.pant1);
//        pant1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isPantClicked = true;
//                if(isShirtClicked && isPantClicked){
//                    sendDB.setEnabled(true);
//                }
////                System.out.println("click");
////                testShirt1.setVisibility(View.GONE);
////                testShirt1.setImageDrawable(null);
//                String newArr[] = new String[pants.length + 1];
//                for(int i = 0; i < pants.length; i++){
//                    newArr[i] = pants[i];
//                }
////                newArr[pants.length] = pant1.getResources().getResourceEntryName(pant1.getId());
//                newArr[pants.length] = String.valueOf(pant1.getTag());
//                pants = newArr;
////                System.out.println(Arrays.toString(shirts));
////                System.out.println(testShirt1.getResources().getResourceEntryName(testShirt1.getId()));
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
                //img.setTag(id);
                System.out.println(img.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                String imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i) + "/" + name;


//
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
                Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allShirts.get(i) + "/" + name);
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
                    }
                });
            }

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
//                img.setTag(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name);
//                System.out.println(name);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(600, 600);
                lp.setMargins(10, 10, 10, 10);
                img.setLayoutParams(lp);
                img.setRotation(90);
//
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
                Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name);
                img.setImageBitmap(myBitmap);
                String imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + allPants.get(i) + "/" + name;
//        img.setImageDrawable(getResources().getDrawable(resId));
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
    }
    private void displayPopUp(Bitmap myBitmap, String imgPath){
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
                popUpImg.setTag(imgPath);
                popUpImg.setImageBitmap(myBitmap);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putStringArray("shirts", shirts);
//        outState.putStringArray("pants", pants);
//        System.out.println("saved");
//        super.onSaveInstanceState(outState);
//    }


}