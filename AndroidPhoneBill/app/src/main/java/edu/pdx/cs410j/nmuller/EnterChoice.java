package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.Serializable;

public class EnterChoice extends AppCompatActivity implements Serializable {

    PhoneBill customer;
    File filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_choice);
        Intent intent = getIntent();
        customer = (PhoneBill) getIntent().getSerializableExtra("customer");
        filePath = (File) getIntent().getSerializableExtra("filePath");

    }

    public void addPhoneCallToBill(View view){
        Intent intent = new Intent(this, AddPhoneCall.class);
        intent.putExtra("customer", customer);
        intent.putExtra("filePath", filePath);
        startActivity(intent);
    }

    public void searchPhoneCalls(View view) {
    }

    public void DisplayAllCallsInBill(View view){
        Intent intent = new Intent(this, DisplayPretty.class);
        intent.putExtra("customer", customer);
        intent.putExtra("filePath", filePath);
        startActivity(intent);

    }


}