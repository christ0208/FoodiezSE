package com.example.index.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.index.Objects.History;
import com.example.index.R;

import java.util.ArrayList;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private Context context;
    private ArrayList<History> histories;
    public HistoryRecyclerViewAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_rv_item, viewGroup, false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        History h = histories.get(i);
        historyViewHolder.imageView.setImageResource(R.mipmap.kintan);
        historyViewHolder.lblRestaurantName.setText(h.getRestaurant_name());
        historyViewHolder.lblDate.setText(h.getCreated_at());
        historyViewHolder.ratingBar.setRating(h.getRating());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}

class HistoryViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView lblRestaurantName;
    TextView lblDate;
    RatingBar ratingBar;
    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        lblRestaurantName = itemView.findViewById(R.id.restaurant_name);
        lblDate = itemView.findViewById(R.id.date);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
