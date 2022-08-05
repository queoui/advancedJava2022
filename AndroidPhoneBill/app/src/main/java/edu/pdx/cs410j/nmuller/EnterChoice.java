package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

public class EnterChoice extends AppCompatActivity implements Serializable {

    PhoneBill customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_choice);
        Intent intent = getIntent();
        customer = (PhoneBill) getIntent().getSerializableExtra("customer");

    }

    public void addPhoneCallToBill(View view){
        Intent intent = new Intent(this, AddPhoneCall.class);
        intent.putExtra("customer", customer);
        startActivity(intent);
    }

}