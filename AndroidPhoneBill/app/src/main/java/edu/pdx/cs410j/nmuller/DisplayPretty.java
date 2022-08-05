package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DisplayPretty extends AppCompatActivity {
    PhoneBill customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pretty);
        Intent intent = getIntent();
        customer = (PhoneBill) getIntent().getSerializableExtra("customer");

    }

}