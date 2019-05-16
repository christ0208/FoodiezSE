package com.example.index;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.time.Year;
import java.util.Calendar;

public class Booking_form extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

Button btn_pick;
TextView Result_timedate;
int day,month,year,hour,minute;
int day_x,month_x,year_x,hour_x,minute_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        btn_pick=findViewById(R.id.picktimedate);
        Result_timedate=findViewById(R.id.timeandate);
        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Booking_form.this,
                        Booking_form.this,year,month,day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int il, int i2) {
        year_x=i;
        month_x=il+i;
        day_x=i2;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Booking_form.this,
                Booking_form.this,hour,minute, DateFormat.HOUR_OF_DAY1_FIELD(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int il) {
        hour_x=i;
        minute_x=il;
        Result_timedate.setText("year::"+year_x+"\n"+
                "month::"+month_x+"\n"+
                "day::"+day_x+"\n"+
                "hour::"+hour_x+"\n"+
                "minute::"+minute_x+"\n"

        );
    }
}
