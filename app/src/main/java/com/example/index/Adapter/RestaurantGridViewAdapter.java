package com.example.index.Adapter;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.index.Objects.Restaurant;
import com.example.index.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RestaurantGridViewAdapter extends RecyclerView.Adapter<RestaurantGridViewAdapter.RecordHolder> {
    Context context;
    ArrayList<Restaurant> data = new ArrayList<>();

    public RestaurantGridViewAdapter(Context context, ArrayList<Restaurant> objects) {
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_grid_view_item, viewGroup, false);
        RecordHolder holder = new RecordHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder recordHolder, int i) {
        Restaurant r = data.get(i);
        new DownloadImageFromInternet(recordHolder.restaurantImage).execute(r.getUrl());
        recordHolder.restaurantName.setText(r.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class RecordHolder extends RecyclerView.ViewHolder{
        ImageView restaurantImage;
        TextView restaurantName;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurant_image);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
        }
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
