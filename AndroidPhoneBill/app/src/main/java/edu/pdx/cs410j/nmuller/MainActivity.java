package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void enterCustomer(View view) {
        Intent intent = new Intent(this, EnterCustomer.class);
        startActivity(intent);

    }

    public void goToREADME(View view) {
        Intent intent = new Intent(this, README.class);
        startActivity(intent);
    }
}