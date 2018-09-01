package com.example.minhnguyen.tripappuser.ViewHolder;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.minhnguyen.tripappuser.Interface.ItemClickListener;
import com.example.minhnguyen.tripappuser.Model.Order;
import com.example.minhnguyen.tripappuser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_card_name, txt_address;

    private ItemClickListener itemClickListener;

    public void setTxt_card_name(TextView txt_card_name) {
        this.txt_card_name = txt_card_name;
    }

    public CardViewHolder(View itemView) {
        super(itemView);
        txt_card_name = (TextView)itemView.findViewById(R.id.card_item_name);
        txt_address = (TextView)itemView.findViewById(R.id.card_item_address);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CardAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.card_layout,parent,false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Locale locale = new Locale("en", "US");
        holder.txt_address.setText(listData.get(position).getAddress());
        holder.txt_card_name.setText(listData.get(position).getPlaceName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
