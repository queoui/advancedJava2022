package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class DisplaySearch extends AppCompatActivity {

    String FullStartDate;
    String FullEndDate;
    PhoneBill customer;
    File filePath;
    Date begin;
    Date end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        Intent intent = getIntent();

        customer = (PhoneBill) getIntent().getSerializableExtra("customer");
        filePath = (File) getIntent().getSerializableExtra("filePath");
        FullStartDate =  getIntent().getStringExtra("startDate");
        FullEndDate =  getIntent().getStringExtra("endDate");


        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US);

        begin = new Date();
        end = new Date();
        try {
            begin = formatter.parse(FullStartDate);
            end = formatter.parse(FullEndDate);

        } catch (Exception errParse) {
            System.err.println("Unknown Date Format " + errParse);
        }

        if (!ErrorCheck.checkTimeTravel(Objects.requireNonNull(begin), end))
            try {
                throw new IOException("Time travel has been detected, please input accurate date and time");
            } catch (IOException e) {
                e.printStackTrace();
            }

        ((TextView) findViewById(R.id.textView11)).setText(customer.getCustomer());
        ((TextView) findViewById(R.id.textView11)).append("\n\n");

        for (PhoneCall singleCall : customer.billOfCalls) {

            Duration duration = Duration.ofMinutes(singleCall.getEndTime().getTime() - singleCall.getBeginTime().getTime());
            if (duration.toMinutes() / 60000 < 0) {
                try {
                    throw new IOException("Time travel has been detected. enter accurate time.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if ((!singleCall.getBeginTime().before(begin)) && (singleCall.getBeginTime().before(end))) {
                String printCall = "Phone call duration of " + (duration.toMinutes() / 60000) + " minutes" + " between " + singleCall.getCaller() + " and " + singleCall.getCallee()
                        + " beginning at " + singleCall.getBeginTimeString() + " and ending at " + singleCall.getEndTimeString() + "\n\n";
                ((TextView) findViewById(R.id.textView11)).append(printCall);
            }
        }
    }
}