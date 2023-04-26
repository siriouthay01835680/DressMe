package com.mobileapp.dressme;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scrapbook extends Fragment {

    private ScrapbookViewModel mViewModel;
    private RecyclerView recyclerView;
    private scrapbookAdapter adapter;
    private ArrayList<scrapbookModel> arrayList = new ArrayList<>();
    Map<Integer, String> hm = new HashMap<Integer, String>();
    CardView cardview;
//    RelativeLayout rl;

    public static Scrapbook newInstance() {
        return new Scrapbook();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrapbook, container, false);
//        recyclerView = view.findViewById(R.id.scrapbookRV);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new scrapbookAdapter(arrayList);
//            System.out.println(ScrapbookArgs.fromBundle(getArguments()).getShirt().isEmpty());
        if(!(ScrapbookArgs.fromBundle(getArguments()).getShirt() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getPants() == null)){
            String[] shirt = ScrapbookArgs.fromBundle(getArguments()).getShirt();
            String[] pants = ScrapbookArgs.fromBundle(getArguments()).getPants();

            //save map to shared pref to keep imgs & their grids

            //display shirts and bottom to a cardview
            GridLayout gridLayout = view.findViewById(R.id.gridLayout);
            CardView cardView = new CardView(gridLayout.getContext());
            CardView.LayoutParams lp = new CardView.LayoutParams(700, 700);
            cardView.setCardBackgroundColor(Color.YELLOW);
            cardView.setLayoutParams(lp);
            gridLayout.addView(cardView);
            RelativeLayout relativeLayout = new RelativeLayout(cardView.getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(params);
            cardView.addView(relativeLayout);

            int totalShirt = shirt.length;

            switch(totalShirt){
                case 4:
                        ImageView img = new ImageView(relativeLayout.getContext());
                        LinearLayout.LayoutParams imgLp = new LinearLayout.LayoutParams(300, 300);
                        img.setLayoutParams(imgLp);
                        int id = 500;
                        img.setId(id);
                        //img.setLayoutParams(relParams);
                        img.setRotation(90);
                        Bitmap myBitmap = BitmapFactory.decodeFile(shirt[0]);
                        img.setImageBitmap(myBitmap);
                        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        rl.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_START);
                        img.setLayoutParams(rl);
                        relativeLayout.addView(img);

                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 501;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[1]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_END);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);

                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 502;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[2]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM | RelativeLayout.ALIGN_PARENT_START);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);

                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 503;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[1]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM | RelativeLayout.ALIGN_PARENT_END);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);
                    break;

                case 3:
                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 500;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[0]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_START);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);

                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 501;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[1]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_END);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);

                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 502;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[2]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM | RelativeLayout.ALIGN_PARENT_START);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);
                    break;

                case 2:
                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 500;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[0]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_START);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);

                    img = new ImageView(relativeLayout.getContext());
                    imgLp = new LinearLayout.LayoutParams(300, 300);
                    img.setLayoutParams(imgLp);
                    id = 501;
                    img.setId(id);
                    //img.setLayoutParams(relParams);
                    img.setRotation(90);
                    myBitmap = BitmapFactory.decodeFile(shirt[1]);
                    img.setImageBitmap(myBitmap);
                    rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_END);
                    img.setLayoutParams(rl);
                    relativeLayout.addView(img);
                    break;

                case 1:
                    ImageView img4 = new ImageView(relativeLayout.getContext());
                    int id4 = 600;
                    img4.setId(id4);
                    //img.setLayoutParams(relParams);
                    LinearLayout.LayoutParams imgLp4 = new LinearLayout.LayoutParams(50, 50);
                    img4.setLayoutParams(imgLp4);
                    img4.setRotation(90);
                    Bitmap myBitmap4 = BitmapFactory.decodeFile(shirt[0]);
                    img4.setImageBitmap(myBitmap4);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    img4.setLayoutParams(params);
                    params.addRule(RelativeLayout.ALIGN_PARENT_START);
                    img4.setLayoutParams(params);
                    relativeLayout.addView(img4);
                    break;
                default:
                    break;

            }
//
//            for(int i = 0; i < shirt.length; i++){
//                cardview = new CardView(gridLayout.getContext());
//                CardView.LayoutParams lp = new CardView.LayoutParams(700, 700);
//                cardview.setLayoutParams(lp);
//                cardview.setCardElevation(0);
////                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
////                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
////                cardview.setLayoutParams(layoutParams);
//                cardview.setRadius(15);
//                cardview.setCardBackgroundColor(Color.YELLOW);
//                gridLayout.addView(cardview);
//
//
//                LinearLayout rl = new LinearLayout(cardview.getContext());
//                LinearLayout.LayoutParams relParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                rl.setLayoutParams(relParams);
//                rl.setGravity(Gravity.TOP | Gravity.START);
//
//                cardview.addView(rl);
//
//                ImageView img = new ImageView(rl.getContext());
//                LinearLayout.LayoutParams imgLp = new LinearLayout.LayoutParams(300, 300);
//                img.setLayoutParams(imgLp);
//                //img.setLayoutParams(relParams);
//                img.setRotation(90);
//                Bitmap myBitmap = BitmapFactory.decodeFile(shirt[i]);
//                img.setImageBitmap(myBitmap);
//
//                rl.addView(img);
//
//
//
//            }
//
//            for(int i = 0; i < pants.length; i++){
//                LinearLayout rl = new LinearLayout(cardview.getContext());
//                LinearLayout.LayoutParams relParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                rl.setLayoutParams(relParams);
//                rl.setGravity(Gravity.TOP | Gravity.END);
//                cardview.addView(rl);
//
//                ImageView img = new ImageView(rl.getContext());
//                LinearLayout.LayoutParams imgLp = new LinearLayout.LayoutParams(300, 300);
//                img.setLayoutParams(imgLp);
//                //img.setLayoutParams(relParams);
//                img.setRotation(90);
//                Bitmap myBitmap = BitmapFactory.decodeFile(pants[i]);
//                img.setImageBitmap(myBitmap);
//                rl.addView(img);
//
//
//
//
//
//
//            }





//            RelativeLayout layout = view.findViewById(R.id.relativeLayout1);
//
//            ImageView img = new ImageView(layout.getContext());
//            int id = 110;
//            img.setId(id);
//            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
////            int resId = getResources().getIdentifier(shirt, "drawable", "com.mobileapp.dressme");
////
////
//            Bitmap myBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + shirt);
//            img.setImageBitmap(myBitmap);
//            layout.addView(img);
//
//            ImageView img1 = new ImageView(layout.getContext());
//            id = 111;
//            img1.setId(id);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
//            img1.setLayoutParams(lp);
////
////            resId = getResources().getIdentifier(pants, "drawable", "com.mobileapp.dressme");
////
////            img1.setImageDrawable(getResources().getDrawable(resId));
//            Bitmap myBitmap1 = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + pants);
//            img1.setImageBitmap(myBitmap1);
////            layout.addView(img1);
//            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            layout.addView(img1);
//        }
//
//
////        way to see which grids are empty for sending form drawing board to scrapbook
////        RelativeLayout layout2 = view.findViewById(R.id.relativeLayout2);
////        if (layout2.getChildCount() == 0) {
////            System.out.println("no children");
////        }
//        if(!(ScrapbookArgs.fromBundle(getArguments()).getDbShirts() == null) && !(ScrapbookArgs.fromBundle(getArguments()).getDbPants() == null)){
//            String[] shirts = ScrapbookArgs.fromBundle(getArguments()).getDbShirts();
//            String[] pants = ScrapbookArgs.fromBundle(getArguments()).getDbPants();
//            RelativeLayout layout = view.findViewById(R.id.relativeLayout2);
//            if(shirts.length != 0){
//                for(int i = 0; i < shirts.length; i++){
//                    ImageView img = new ImageView(layout.getContext());
//                    int id = 400 + i;
//                    img.setId(id);
//                    img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
//                    int resId = getResources().getIdentifier(shirts[i], "drawable", "com.mobileapp.dressme");
//                    img.setImageDrawable(getResources().getDrawable(resId));
//                    layout.addView(img);
//                }
//            }
//            if(pants.length != 0){
//                for(int i = 0; i < pants.length; i++){
//                    ImageView img1 = new ImageView(layout.getContext());
//                    int id = 500 + i;
//                    img1.setId(id);
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
//                    img1.setLayoutParams(lp);
//                    int resId = getResources().getIdentifier(pants[i], "drawable", "com.mobileapp.dressme");
//                    img1.setImageDrawable(getResources().getDrawable(resId));
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    layout.addView(img1);
//                }
//            }
//            ImageView img = new ImageView(layout.getContext());
//            int id = 110;
//            img.setId(id);
//            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
//            int resId = getResources().getIdentifier(shirt, "drawable", "com.mobileapp.dressme");
//
//            img.setImageDrawable(getResources().getDrawable(resId));
//            layout.addView(img);
//
//            ImageView img1 = new ImageView(layout.getContext());
//            id = 111;
//            img1.setId(id);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
//            img1.setLayoutParams(lp);
//
//            resId = getResources().getIdentifier(pants, "drawable", "com.mobileapp.dressme");
//
//            img1.setImageDrawable(getResources().getDrawable(resId));
//            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            layout.addView(img1);
        }

        return view;
    }


}