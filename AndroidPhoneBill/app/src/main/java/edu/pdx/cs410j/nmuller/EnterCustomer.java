package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        data.putExtra("customer", customer);
        startActivity(data);
    }
}