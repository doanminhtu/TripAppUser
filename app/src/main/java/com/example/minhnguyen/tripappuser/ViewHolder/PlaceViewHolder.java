package com.example.minhnguyen.tripappuser.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhnguyen.tripappuser.Interface.ItemClickListener;
import com.example.minhnguyen.tripappuser.R;

public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView place_name;
    public ImageView place_image;

    private ItemClickListener itemClickListener;


    public PlaceViewHolder(View itemView) {
        super(itemView);

        place_name = (TextView)itemView.findViewById(R.id.place_name);
        place_image = (ImageView)itemView.findViewById(R.id.place_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
