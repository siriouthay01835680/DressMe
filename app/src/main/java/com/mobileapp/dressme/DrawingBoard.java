package com.mobileapp.dressme;

import androidx.lifecycle.ViewModelProvider;

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

public class DrawingBoard extends Fragment {

    private DrawingBoardViewModel mViewModel;

    public static DrawingBoard newInstance() {
        return new DrawingBoard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing_board,
                container, false);
        Button scrapBtn = view.findViewById(R.id.scrapButton);
        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_drawingBoard_to_scrapbook);
            }
        });
        return view;
    }

}