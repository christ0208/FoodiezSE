package com.example.index;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ConfirmPaymentActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    private TextView lblName, lblDate, lblTime, lblPeople, lblRestaurantName, lblPayment, lblPrice;
    private Button btnConfirmPayment;

    private Integer restaurantId, amount;
    private String datetime, paymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();
        restaurantId = extras.getInt("restaurantId");
        datetime = extras.getString("datetime");
        amount = extras.getInt("amount");
        paymentMethod = extras.getString("payment");

        lblName = findViewById(R.id.lbl_name);
        lblDate = findViewById(R.id.lbl_date);
        lblTime = findViewById(R.id.lbl_time);
        lblPeople = findViewById(R.id.lbl_amount);
        lblRestaurantName = findViewById(R.id.lbl_restaurant_name);
        lblPayment = findViewById(R.id.lbl_payment);
        lblPrice = findViewById(R.id.lbl_price);

        btnConfirmPayment = findViewById(R.id.btn_confirmpayment);
        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmPaymentActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                insertTransaction();
            }
        });

        fetchData();
    }

    private void insertTransaction() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", lblName.getText().toString());
        map.put("datetime", datetime);
        map.put("amount", amount);
        map.put("restaurant_name", lblRestaurantName.getText().toString());
        map.put("payment", paymentMethod);
        map.put("status", "Paid");
        firestore.collection("booking_transaction")
                .add(map)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ConfirmPaymentActivity.this, "Success Booking Place", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ConfirmPaymentActivity.this, PaymentDetailActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }

    private void fetchData() {
        fetchUser();
        fetchDateTime();
        lblPeople.setText(amount.toString());
        fetchRestaurant();
        lblPayment.setText(paymentMethod);
        fetchPrice();
    }

    private void fetchPrice() {
        int price = 30000 * amount;
        lblPrice.setText("Rp. " + price);
    }

    private void fetchRestaurant() {
        firestore.collection("restaurant")
                .whereEqualTo("id", restaurantId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:
                                 task.getResult()) {
                                Map m = document.getData();
                                lblRestaurantName.setText(m.get("name").toString());
                            }
                        }
                    }
                });
    }

    private void fetchDateTime() {
        String date = datetime.split(" ")[0];
        String time = datetime.split(" ")[1];
        lblDate.setText(date);
        lblTime.setText(time);
    }

    private void fetchUser() {
        firestore.collection("user")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Map m = task.getResult().getData();
                            lblName.setText(m.get("name").toString());
                        }
                    }
                });
    }
}
