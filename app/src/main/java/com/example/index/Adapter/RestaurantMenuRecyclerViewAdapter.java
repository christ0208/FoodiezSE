package com.example.index.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.index.Objects.RestaurantMenu;
import com.example.index.R;

import java.util.ArrayList;

public class RestaurantMenuRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantMenuViewHolder> {
    private Context context;
    private ArrayList<RestaurantMenu> menus;

    public RestaurantMenuRecyclerViewAdapter(Context context, ArrayList<RestaurantMenu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_rv_item, viewGroup, false);
        RestaurantMenuViewHolder holder = new RestaurantMenuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuViewHolder restaurantMenuViewHolder, int i) {
        RestaurantMenu menu = menus.get(i);
        restaurantMenuViewHolder.lblMenuPrice.setText(menu.getPrice().toString());
        restaurantMenuViewHolder.lblMenuName.setText(menu.getName());
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
}

class RestaurantMenuViewHolder extends RecyclerView.ViewHolder{
    TextView lblMenuName, lblMenuPrice;

    public RestaurantMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        lblMenuName = itemView.findViewById(R.id.menu_name);
        lblMenuPrice = itemView.findViewById(R.id.menu_price);
    }
}
