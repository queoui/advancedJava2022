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

        Intent intent = new Intent(this, DisplaySearch.class);
        intent.putExtra("customer", customer);
        intent.putExtra("filePath", filePath);
        intent.putExtra("startDate", FullStartDate);
        intent.putExtra("endDate", FullEndDate);
        startActivity(intent);
    }

}
