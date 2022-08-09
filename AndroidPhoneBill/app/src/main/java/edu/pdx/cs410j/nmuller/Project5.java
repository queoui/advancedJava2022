package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.Map;

public class Project5 extends AppCompatActivity {

    File dataDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDirectory = this.getDataDir();
    }

    public void enterCustomer(View view) {
        Intent intent = new Intent(this, EnterCustomer.class);
        intent.putExtra("fileDir", dataDirectory);
        startActivity(intent);
    }

    public void goToREADME(View view) {
        Intent intent = new Intent(this, README.class);
        startActivity(intent);
    }
}