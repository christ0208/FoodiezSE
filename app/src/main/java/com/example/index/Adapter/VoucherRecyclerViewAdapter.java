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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.index.Objects.Voucher;
import com.example.index.R;
import com.example.index.VoucherDetailActivity;

import java.io.InputStream;
import java.util.ArrayList;

public class VoucherRecyclerViewAdapter extends RecyclerView.Adapter<VoucherViewHolder> {
    private Context context;
    private ArrayList<Voucher> vouchers = new ArrayList<>();

    public VoucherRecyclerViewAdapter(Context context, ArrayList<Voucher> vouchers) {
        this.context = context;
        this.vouchers = vouchers;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.voucher_rv_view_item, viewGroup, false);
        VoucherViewHolder vvh = new VoucherViewHolder(v);
        return vvh;
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder voucherViewHolder, int i) {
        final Voucher v = vouchers.get(i);
        new DownloadImageFromInternet(voucherViewHolder.imageView).execute(v.getUrl());
        voucherViewHolder.minTransaction.setText("Minimal Transaksi: Rp. " + Integer.toString(v.getMin_transaction()));
        voucherViewHolder.date.setText("Berlaku Hingga: " + v.getEnd_date());

        voucherViewHolder.layoutVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VoucherDetailActivity.class);
                i.putExtra("voucherId", v.getVoucherId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
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

class VoucherViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout layoutVoucher;
    ImageView imageView;
    TextView minTransaction, date;

    public VoucherViewHolder(@NonNull View itemView) {
        super(itemView);
        layoutVoucher = itemView.findViewById(R.id.layout_voucher);
        imageView = itemView.findViewById(R.id.imageView);
        minTransaction = itemView.findViewById(R.id.min_transaction);
        date = itemView.findViewById(R.id.date);
    }
}
