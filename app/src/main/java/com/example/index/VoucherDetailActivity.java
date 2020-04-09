package com.example.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.Map;

public class VoucherDetailActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ImageView imageView;
    TextView lblVoucherDetail, lblEndDate, lblMinTransaction, lblTermsConditions;
    Button btnRedeem;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_detail);
        firestore = FirebaseFirestore.getInstance();

        String voucherId = getIntent().getExtras().get("voucherId").toString();

        registerVariables();
        registerListeners();

        fetchVoucher(voucherId);
    }

    private void fetchVoucher(final String voucherId) {
        firestore.collection("voucher")
                .document(voucherId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Map map = task.getResult().getData();
                            id = task.getResult().getId();
                            new DownloadImageFromInternet(imageView).execute(map.get("url").toString());
                            lblVoucherDetail.setText(map.get("voucher_detail").toString());
                            lblEndDate.setText(map.get("end_date").toString());
                            lblMinTransaction.setText(map.get("min_transaction").toString());
                            lblTermsConditions.setText(map.get("terms_conditions").toString());
                        }
                    }
                });
    }

    private void registerListeners() {
        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VoucherDetailActivity.this, RedeemVoucherActivity.class);
                i.putExtra("voucherId", id);
                startActivity(i);
            }
        });
    }

    private void registerVariables() {
        imageView = findViewById(R.id.voucher);
        lblVoucherDetail = findViewById(R.id.voucherdetail);
        lblEndDate = findViewById(R.id.lbl_end_date);
        lblMinTransaction = findViewById(R.id.lbl_min_transaction);
        lblTermsConditions = findViewById(R.id.lbl_terms_conditions);
        btnRedeem = findViewById(R.id.btn_redeem);
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
