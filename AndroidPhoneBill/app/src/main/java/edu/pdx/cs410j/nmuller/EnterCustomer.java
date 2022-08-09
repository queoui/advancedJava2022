package edu.pdx.cs410j.nmuller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.pdx.cs410J.ParserException;

public class EnterCustomer extends AppCompatActivity {

    Map<String, PhoneBill> allBills = new HashMap<>();
    File dataDirectory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer);
        dataDirectory = (File) getIntent().getSerializableExtra("fileDir");
        if(dataDirectory != null) {
            String[] pathNames = dataDirectory.list();
            for (String singleFile : Objects.requireNonNull(pathNames)) {
                if (singleFile.endsWith(".txt")) {
                    try {
                        File newFilePath = new File(dataDirectory.toString() +"/"+ singleFile);
                        Reader textFile = new FileReader(newFilePath);
                        TextParser fileReader = new TextParser(textFile);
                        PhoneBill newBill = fileReader.Billparse();
                        allBills.put(newBill.getCustomer(), newBill);
                    } catch (FileNotFoundException | ParserException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public void goToChoice(View view){

        EditText getCustomerId = findViewById(R.id.CustomerNameText);
        String customer = getCustomerId.getText().toString().trim();
        Intent data = new Intent(this, EnterChoice.class);

        if("".equals(customer)){
            Toast.makeText(this, "Please Enter A Valid Name", Toast.LENGTH_LONG).show();
        }
        else {

            File newFilePath = new File(dataDirectory.toString() +"/"+ customer + ".txt");
            if(!newFilePath.exists()) {
                try (PrintWriter pw = new PrintWriter((new FileWriter(newFilePath)))) {
                    pw.println(customer);
                    pw.close();
                    PhoneBill newBill = new PhoneBill(customer);
                    data.putExtra("customer", newBill);
                    data.putExtra("filePath", newFilePath);
                    startActivity(data);
                } catch (IOException e) {
                }
            }else{

                data.putExtra("customer", allBills.get(customer));
                data.putExtra("filePath" ,  newFilePath);
                startActivity(data);
            }


        }
    }
}