package com.mobileapp.dressme;

import static android.view.View.GONE;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class scrapbookAdapter extends RecyclerView.Adapter<scrapbookAdapter.ViewHolder> {
    //private final Context context;
    private ArrayList<scrapbookModel> scrapbookModelArrayList;

    public scrapbookAdapter( ArrayList<scrapbookModel> scrapbookModelArrayList){
        //this.context = context;
//        this.image1 = image1;
//        this.image2 = image2;
//        this.image3 = image3;
//        this.image4 = image4;
        this.scrapbookModelArrayList = scrapbookModelArrayList;
    }

    @NonNull
    @Override
    public scrapbookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull scrapbookAdapter.ViewHolder holder, int position) {
        scrapbookModel data = scrapbookModelArrayList.get(position);
        Bitmap myBitmap = BitmapFactory.decodeFile(data.getImage1());
        if(myBitmap != null){
            holder.image1.setImageBitmap(myBitmap);
            holder.image1.setRotation(90);

        }
        else{
            holder.image1.setVisibility(GONE);
        }
        myBitmap = BitmapFactory.decodeFile(data.getImage2());
        if(myBitmap != null){
            holder.image2.setImageBitmap(myBitmap);

        }
        else{
            holder.image2.setVisibility(GONE);
        }
        myBitmap = BitmapFactory.decodeFile(data.getImage3());
        if(myBitmap != null){
            holder.image3.setImageBitmap(myBitmap);

        }
        else{
            holder.image3.setVisibility(GONE);
        }
        myBitmap = BitmapFactory.decodeFile(data.getImage4());
        if(myBitmap != null){
            holder.image4.setImageBitmap(myBitmap);

        }
        else{
            holder.image4.setVisibility(GONE);
        }
    }

    @Override
    public int getItemCount() {
        return scrapbookModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image1;
        public ImageView image2;
        public ImageView image3;
        public ImageView image4;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            image2 = itemView.findViewById(R.id.image2);
            image3 = itemView.findViewById(R.id.image3);
            image4 = itemView.findViewById(R.id.image4);
        }
    }
}
