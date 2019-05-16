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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.index.Objects.Favorite;
import com.example.index.R;

import java.io.InputStream;
import java.util.ArrayList;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private ArrayList<Favorite> favorites = new ArrayList<>();
    private Context context;

    public FavoriteRecyclerViewAdapter(ArrayList<Favorite> favorites, Context context) {
        this.favorites = favorites;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_rv_view_item, viewGroup, false);
        FavoriteViewHolder holder = new FavoriteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        Favorite f = favorites.get(i);
        favoriteViewHolder.restaurantName.setText(f.getRestaurantName());
        favoriteViewHolder.restaurantAddress.setText(f.getRestaurantAddress());
        new DownloadImageFromInternet(favoriteViewHolder.imageView).execute(f.getRestaurantUrl());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
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

class FavoriteViewHolder extends RecyclerView.ViewHolder{
    RelativeLayout layout;
    ImageView imageView;
    TextView restaurantName, restaurantAddress;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.layout_click);
        imageView = itemView.findViewById(R.id.imageView);
        restaurantName = itemView.findViewById(R.id.menu_name);
        restaurantAddress = itemView.findViewById(R.id.restaurant_address);
    }
}
