package com.example.index;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.util.Calendar;

public class BookingFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private Button pick_timedate, btnPayment;
    private TextView pick_result;
    private EditText txtPeople;
    private String datetime;
    private int day,month,year,hour,minute;
    private int day_x,month_x,year_x,hour_x,minute_x;
    private int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        Bundle extras = getIntent().getExtras();
        restaurantId = extras.getInt("restaurantId");

        pick_timedate=(Button) findViewById(R.id.pick_timedate);
        pick_result=(TextView) findViewById(R.id.pick_result);
        txtPeople = findViewById(R.id.txt_people);
        btnPayment = findViewById(R.id.btn_payment);

        pick_timedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingFormActivity.this,
                        BookingFormActivity.this,year,month,day);
                datePickerDialog.show();
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookingFormActivity.this, PaymentMethodActivity.class);
                i.putExtra("restaurantId", restaurantId);
                i.putExtra("datetime", datetime);
                i.putExtra("amount", Integer.parseInt(txtPeople.getText().toString()));
                startActivity(i);
            }
        });
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int il, int i2) {
        year_x=i;
        month_x=il+1;
        day_x=i2;
//        Toast.makeText(this, Integer.toString(il), Toast.LENGTH_SHORT).show();
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(BookingFormActivity.this, BookingFormActivity.this,hour,minute,
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int il) {
        hour_x=i;
        minute_x=il;
        pick_result.setText("year::"+year_x+"\n"+
                "month::"+month_x+"\n"+
                "day::"+day_x+"\n"+
                "hour::"+hour_x+"\n"+
                "minute::"+minute_x+"\n"

        );
        datetime = day_x + "-" + month_x + "-" + year_x + " " + hour_x + ":" + minute_x;
    }
}