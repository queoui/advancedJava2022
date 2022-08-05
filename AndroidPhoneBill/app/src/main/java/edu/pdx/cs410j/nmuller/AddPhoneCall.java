package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class AddPhoneCall extends AppCompatActivity {

    PhoneBill customer;
    String Caller;
    String Callee;
    String JustStartDate;
    String JustEndDate;
    String JustStartTime;
    String JustEndTime;
    String FullStartDate;
    String FullEndDate;
    boolean error;
    PhoneCall newCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
        Intent intent = getIntent();
        customer = (PhoneBill) getIntent().getSerializableExtra("customer");
    }

    public void addPhoneCallToBill(View view){

        EditText getCallerId = findViewById(R.id.CallerText);
        EditText getCalleeId = findViewById(R.id.CalleeText);
        EditText getStartDateId = findViewById(R.id.StartDateText);
        EditText getStartTimeId = findViewById(R.id.StartTimeText);
        EditText getEndDateId = findViewById(R.id.EndDateText);
        EditText getEndTimeId = findViewById(R.id.EndTimeText);
        Spinner getFirstAMPM = (Spinner) findViewById(R.id.spinner);
        Spinner getSecondAMPM = (Spinner) findViewById(R.id.spinner2);

        Caller = getCallerId.getText().toString().trim();
        Callee = getCalleeId.getText().toString().trim();
        JustStartDate = getStartDateId.getText().toString().trim();
        JustEndDate = getEndDateId.getText().toString().trim();
        JustStartTime = getStartTimeId.getText().toString().trim();
        JustEndTime = getEndTimeId.getText().toString().trim();

        FullStartDate = JustStartDate + " " + JustStartTime + getFirstAMPM.getSelectedItem().toString();
        FullEndDate = JustEndDate +" "+ JustEndTime + getSecondAMPM.getSelectedItem().toString();

        try{
            ErrorCheck.checkPhoneNumber(Caller);
            ErrorCheck.checkPhoneNumber(Callee);
            ErrorCheck.checkDate(getStartDateId.getText().toString().trim());
            ErrorCheck.checkTime(getStartTimeId.getText().toString().trim());
            ErrorCheck.checkDate(getEndDateId.getText().toString().trim());
            ErrorCheck.checkTime(getEndTimeId.getText().toString().trim());
            newCall = PhoneCall.createNewCall(Caller, Callee, FullStartDate, FullEndDate);
            customer.addPhoneCall(newCall);
        }catch(IOException e){
            error = true;
            toastException(e);
        }
        if(!error) {
            Toast.makeText(this, "Phone Call Successfully Added", Toast.LENGTH_LONG).show();
            Toast.makeText(this, newCall.toString(), Toast.LENGTH_LONG).show();
            Intent data = new Intent(this, EnterChoice.class);
            data.putExtra("customer", customer);
            startActivity(data);
        }
    }


    private void toastException(IOException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}