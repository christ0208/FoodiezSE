package com.example.index.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.index.HistoryDetailActivity;
import com.example.index.Objects.History;
import com.example.index.R;

import java.io.InputStream;
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
    public void onBindViewHolder(@NonNull final HistoryViewHolder historyViewHolder, int i) {
        final History h = histories.get(i);
        new DownloadImageFromInternet(historyViewHolder.imageView).execute(h.getRestaurant_url());
        historyViewHolder.lblRestaurantName.setText(h.getRestaurant_name());
        historyViewHolder.lblDate.setText(h.getCreated_at());
        historyViewHolder.ratingBar.setRating(h.getRating());
        historyViewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                historyViewHolder.ratingBar.setRating(h.getRating());
            }
        });

        historyViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HistoryDetailActivity.class);
                i.putExtra("name", h.getRestaurant_name());
                i.putExtra("datetime", h.getCreated_at());
                i.putExtra("amount", h.getAmount());
                i.putExtra("payment", h.getPaymentMethod());
                i.putExtra("rating", h.getRating());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
//            Toast.makeText(context, "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);
                bimage.setWidth(50);
                bimage.setHeight(25);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}

class HistoryViewHolder extends RecyclerView.ViewHolder{
    LinearLayout layout;

    ImageView imageView;
    TextView lblRestaurantName;
    TextView lblDate;
    RatingBar ratingBar;
    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.layout_history);
        imageView = itemView.findViewById(R.id.imageView);
        lblRestaurantName = itemView.findViewById(R.id.menu_name);
        lblDate = itemView.findViewById(R.id.date);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
