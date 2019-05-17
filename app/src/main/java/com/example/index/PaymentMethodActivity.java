package com.example.index;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class PaymentMethodActivity extends AppCompatActivity {

    private RadioGroup paymentGroup;
    private Button btnChoosePayment;

    private int restaurantId, amount;
    private String datetime;
    private String chosenPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Bundle extras = getIntent().getExtras();
        restaurantId = extras.getInt("restaurantId");
        datetime = extras.getString("datetime");
        amount = extras.getInt("amount");

        btnChoosePayment = findViewById(R.id.btn_choosepayment);

        paymentGroup = findViewById(R.id.payment_group);
        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.transfer_radio:
                        chosenPayment = "Transfer";
                        break;
                    case R.id.mbank_radio:
                        chosenPayment = "Mobile Banking";
                        break;
                    case R.id.virtual_radio:
                        chosenPayment = "Virtual Account";
                        break;
                    case R.id.credit_radio:
                        chosenPayment = "Credit Card";
                        break;
                }
            }
        });

        btnChoosePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentMethodActivity.this, ConfirmPaymentActivity.class);
                i.putExtra("restaurantId", restaurantId);
                i.putExtra("datetime", datetime);
                i.putExtra("amount", amount);
                i.putExtra("payment", chosenPayment);
                startActivity(i);
            }
        });
    }
}
