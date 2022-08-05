package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class EnterCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer);
    }

    public void goToChoice(View view){

        EditText getCustomerId = findViewById(R.id.CustomerNameText);
        String customer = getCustomerId.getText().toString().trim();
        Intent data = new Intent(this, EnterChoice.class);

        if("".equals(customer)){
            Toast.makeText(this, "Please Enter A Valid Name", Toast.LENGTH_LONG).show();
        }
        else {
            PhoneBill newBill = new PhoneBill(customer);
            data.putExtra("customer", newBill);
            startActivity(data);
        }
    }
}