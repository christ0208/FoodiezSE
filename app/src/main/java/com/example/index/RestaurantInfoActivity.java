package com.example.index;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.index.Objects.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class RestaurantInfoActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private TextView lblTitle, lblAddress, lblEatery, lblOpening;
    private Button btnMenu, btnListReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        firestore = FirebaseFirestore.getInstance();
        lblTitle = findViewById(R.id.lbl_title);
        lblAddress = findViewById(R.id.lbl_address);
        lblEatery = findViewById(R.id.lbl_eatery);
        lblOpening = findViewById(R.id.lbl_opening);
        btnMenu = findViewById(R.id.menu);
        btnListReview = findViewById(R.id.listreview);

        Bundle extras = getIntent().getExtras();
        final int restaurantId = extras.getInt("restaurantId");

        fetchRestaurant(restaurantId);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantInfoActivity.this, RestaurantMenuActivity.class);
                i.putExtra("restaurantId", restaurantId);
                startActivity(i);
            }
        });

        btnListReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantInfoActivity.this, RestaurantInfoReviewListActivity.class);
                i.putExtra("restaurantId", restaurantId);
                startActivity(i);
            }
        });
    }

    private void fetchRestaurant(int restaurantId) {
        firestore.collection("restaurant")
                .whereEqualTo("id", restaurantId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Map map = document.getData();
                                Restaurant r = new Restaurant(Integer.parseInt(map.get("id").toString()),
                                        map.get("name").toString(), map.get("url").toString(), map.get("address").toString(),
                                        map.get("eatery_type").toString(), map.get("opening_day").toString(), map.get("opening_hours").toString(),
                                        Float.parseFloat(map.get("lat").toString()), Float.parseFloat(map.get("long").toString()));
                                setData(r);
                            }
                        }
                    }
                });
    }

    private void setData(Restaurant r) {
        lblTitle.setText(r.getName());
        lblAddress.setText(r.getAddress());
        lblEatery.setText(r.getEateryType());
        lblOpening.setText(r.getOpeningDay() + " " + r.getOpeningHours());
    }
}
