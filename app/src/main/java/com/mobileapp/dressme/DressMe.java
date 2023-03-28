package com.mobileapp.dressme;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class DressMe extends Fragment {

    private DressMeViewModel mViewModel;

    public static DressMe newInstance() {
        return new DressMe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress_me,
                container, false);
        Button generate = view.findViewById(R.id.generateBtn);
        ImageView shirt = view.findViewById(R.id.genShirt);
        ImageView pants = view.findViewById(R.id.genPant);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shirt.setVisibility(view.VISIBLE);
                pants.setVisibility(view.VISIBLE);
            }
        });
        Button scrapbookBtn = view.findViewById(R.id.scrapbookBtn);
        scrapbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_dressMe_to_scrapbook);
            }
        });

        return view;
    }

}