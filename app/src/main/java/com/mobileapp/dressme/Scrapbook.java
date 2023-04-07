package com.mobileapp.dressme;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Scrapbook extends Fragment {

    private ScrapbookViewModel mViewModel;

    public static Scrapbook newInstance() {
        return new Scrapbook();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrapbook, container, false);

        String shirt = ScrapbookArgs.fromBundle(getArguments()).getShirt();
        String pants = ScrapbookArgs.fromBundle(getArguments()).getPants();
        RelativeLayout layout = view.findViewById(R.id.relativeLayout1);

        ImageView img = new ImageView(layout.getContext());
        int id = 110;
        img.setId(id);
        img.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
        int resId = getResources().getIdentifier(shirt, "drawable", "com.mobileapp.dressme");

        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img);

        ImageView img1 = new ImageView(layout.getContext());
        id = 111;
        img1.setId(id);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(250, 250);
        img1.setLayoutParams(lp);

        resId = getResources().getIdentifier(pants, "drawable", "com.mobileapp.dressme");

        img1.setImageDrawable(getResources().getDrawable(resId));
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.addView(img1);

        return view;
    }


}