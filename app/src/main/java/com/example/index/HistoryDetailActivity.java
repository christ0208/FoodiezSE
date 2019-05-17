package com.example.index;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class HistoryDetailActivity extends AppCompatActivity {
    private TextView lblRestaurantName, lblDate, lblTime, lblAmount, lblPayment, lblTotal;
    private RatingBar ratingBar;
    private String name, datetime, payment;
    private Integer amount;
    private Float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        Bundle extras = getIntent().getExtras();

        name = extras.getString("name");
        datetime = extras.getString("datetime");
        amount = extras.getInt("amount");
        payment = extras.getString("payment");
        rating = extras.getFloat("rating");

        lblRestaurantName = findViewById(R.id.lbl_restaurant_name);
        lblDate = findViewById(R.id.lbl_dateorder_booking);
        lblTime = findViewById(R.id.lbl_timeorder_booking);
        lblAmount = findViewById(R.id.lbl_amount);
        lblPayment = findViewById(R.id.lbl_payment);
        lblTotal = findViewById(R.id.lbl_total);
        ratingBar = findViewById(R.id.rating_bar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(HistoryDetailActivity.this.rating);
            }
        });

        setData();
    }

    private void setData() {
        lblRestaurantName.setText(name);
        lblDate.setText(datetime.split(", ")[0]);
        lblTime.setText(datetime.split(", ")[1]);
        lblAmount.setText(amount.toString());
        lblPayment.setText(payment);
        Integer calc = 30000 * amount;
        lblTotal.setText(calc.toString());
        ratingBar.setRating(rating);
    }


}
