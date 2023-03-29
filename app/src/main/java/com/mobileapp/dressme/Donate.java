package com.mobileapp.dressme;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class Donate extends Fragment {

    private DonateViewModel mViewModel;

    public static Donate newInstance() {
        return new Donate();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate,
                container, false);
        ImageView testShirt1 = view.findViewById(R.id.shirt1);
        View popUpView = inflater.inflate(R.layout.donatepopup,null);
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
        closetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                Navigation.findNavController(view).navigate(R.id.action_donate_to_closet);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testShirt1.setVisibility(View.GONE);
            }
        });

        testShirt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("click");
//                testShirt1.setVisibility(View.GONE);
//                testShirt1.setImageDrawable(null);
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
                view.post(new Runnable() {

                    @Override
                    public void run() {
                        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DonateViewModel.class);
        // TODO: Use the ViewModel
    }

}