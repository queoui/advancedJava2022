package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.time.Duration;

public class DisplayPretty extends AppCompatActivity {
    PhoneBill customer;
    File filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pretty);
        Intent intent = getIntent();
        customer = (PhoneBill) getIntent().getSerializableExtra("customer");
        filePath = (File) getIntent().getSerializableExtra("filePath");

        ((TextView) findViewById(R.id.textView6)).setText(customer.getCustomer());
        ((TextView) findViewById(R.id.textView6)).append("\n\n");
        for (PhoneCall singleCall: customer.billOfCalls) {
            Duration duration = Duration.ofMinutes(singleCall.getEndTime().getTime() - singleCall.getBeginTime().getTime());
            if(duration.toMinutes() / 60000 < 0){
                try {
                    throw new IOException("Time travel has been detected. enter accurate time.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String printCall = "Phone call duration of " + (duration.toMinutes() / 60000) +" minutes" +  " between " + singleCall.getCaller() + " and " + singleCall.getCallee()
                    + " beginning at "+ singleCall.getBeginTimeString()+ " and ending at "+ singleCall.getEndTimeString() +"\n\n";
            ((TextView) findViewById(R.id.textView6)).append(printCall);
        }
    }
}