package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SearchCalls extends AppCompatActivity {
    PhoneBill customer;
    File filePath;
    String JustStartDate;
    String JustEndDate;
    String JustStartTime;
    String JustEndTime;
    String FullStartDate;
    String FullEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_calls);
        Intent intent = getIntent();
        customer = (PhoneBill) getIntent().getSerializableExtra("customer");
        filePath = (File) getIntent().getSerializableExtra("filePath");

    }

    public void searchAndPrint(View view) throws IOException {
        EditText getStartDateId = findViewById(R.id.startDateText2);
        EditText getStartTimeId = findViewById(R.id.startTimeText);
        EditText getEndDateId = findViewById(R.id.endDateText);
        EditText getEndTimeId = findViewById(R.id.endTimeText);
        Spinner getFirstAMPM = (Spinner) findViewById(R.id.spinner3);
        Spinner getSecondAMPM = (Spinner) findViewById(R.id.spinner4);
        JustStartDate = getStartDateId.getText().toString().trim();
        JustEndDate = getEndDateId.getText().toString().trim();
        JustStartTime = getStartTimeId.getText().toString().trim();
        JustEndTime = getEndTimeId.getText().toString().trim();
        FullStartDate = JustStartDate + " " + JustStartTime + getFirstAMPM.getSelectedItem().toString();
        FullEndDate = JustEndDate +" "+ JustEndTime + getSecondAMPM.getSelectedItem().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy hh:mma", Locale.US);
        Date begin = new Date();
        Date end = new Date();
        try {
            begin = formatter.parse(FullStartDate);
            end = formatter.parse(FullEndDate);
        }catch(Exception errParse){
            System.err.println("Unknown Date Format " + errParse);
        }

        if(!ErrorCheck.checkTimeTravel(Objects.requireNonNull(begin), end))
            throw new IOException("Time travel has been detected, please input accurate date and time");

        ((TextView) findViewById(R.id.textView11)).setText(customer.getCustomer());
        ((TextView) findViewById(R.id.textView11)).append("\n\n");

        for (PhoneCall singleCall: customer.billOfCalls) {
            Duration duration = Duration.ofMinutes(singleCall.getEndTime().getTime() - singleCall.getBeginTime().getTime());
            if(duration.toMinutes() / 60000 < 0){
                try {
                    throw new IOException("Time travel has been detected. enter accurate time.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if((singleCall.getBeginTime().compareTo(begin) >= 0) && (singleCall.getEndTime().compareTo(end) <= 0)) {
                String printCall = "Phone call duration of " + (duration.toMinutes() / 60000) + " minutes" + " between " + singleCall.getCaller() + " and " + singleCall.getCallee()
                        + " beginning at " + singleCall.getBeginTimeString() + " and ending at " + singleCall.getEndTimeString() + "\n\n";
                ((TextView) findViewById(R.id.textView11)).append(printCall);
            }
        }
        Intent intent = new Intent(this, DisplaySearch.class);
        startActivity(intent);
    }


    }
}