package com.example.index;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class RedeemVoucherActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_voucher);

        Bundle extras = getIntent().getExtras();
        String textQr = extras.getString("voucherId");

        image = findViewById(R.id.qr);

        MultiFormatWriter formatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = formatWriter.encode(textQr, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }
}
