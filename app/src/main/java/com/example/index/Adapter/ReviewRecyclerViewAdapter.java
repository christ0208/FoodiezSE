package com.example.index.Adapter;

import android.content.Context;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.index.Objects.Review;
import com.example.index.R;

import java.io.InputStream;
import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private Context context;
    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewRecyclerViewAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_rv_item, viewGroup, false);
        ReviewViewHolder holder = new ReviewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        Review r = reviews.get(i);
        new DownloadImageFromInternet(reviewViewHolder.imageRestaurant).execute(r.getRestaurantUrl());
        reviewViewHolder.lblRestaurantName.setText(r.getRestaurantName());
        reviewViewHolder.lblRestaurantReview.setText("Review:\n " + r.getDescription());
        reviewViewHolder.ratingBar.setRating(r.getRating());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
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

class ReviewViewHolder extends RecyclerView.ViewHolder{
    ImageView imageRestaurant;
    TextView lblRestaurantName, lblRestaurantReview;
    RatingBar ratingBar;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        imageRestaurant = itemView.findViewById(R.id.listpic);
        lblRestaurantName = itemView.findViewById(R.id.restaurant_name);
        lblRestaurantReview = itemView.findViewById(R.id.restaurant_review);
        ratingBar = itemView.findViewById(R.id.rating_bar);
    }
}
