package com.example.gbloodbank12;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//class for recent bloodcamp activity
public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(View itemView){
        super(itemView);
        mView = itemView;
    }

    //set detail to recycler view
    public void setDetails(Context context,String image,String name,String date,String place){
        TextView textViewName = mView.findViewById(R.id.rtextViewName);
        TextView textViewPlace = mView.findViewById(R.id.rtextViewPlace);
        TextView textViewDate = mView.findViewById(R.id.rtextViewDate);
        ImageView imageView = mView.findViewById(R.id.rimageView);

        //set Data to Views
        textViewName.setText("Organiser Name : " + name);
        textViewDate.setText("BloodCamp Date : " + date);
        textViewPlace.setText("BloodCamp Place: " + place);
        Picasso.get().load(image).into(imageView);
    }
}