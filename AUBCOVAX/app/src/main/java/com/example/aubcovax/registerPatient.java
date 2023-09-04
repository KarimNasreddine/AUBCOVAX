package com.example.aubcovax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerPatient extends AppCompatActivity {
    EditText fn, dob, ID, phn, count, email, med, user, pass;
    final int SEND_SMS_PERMISSION_REQUEST_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        Button RegisterBtn2 = (Button)findViewById(R.id.registerUser2);

        RegisterBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle registration
                try {
                    Register_inaction();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void Register_inaction() throws InterruptedException {
        fn= (EditText)findViewById(R.id.fullName);
        String fnn= fn.getText().toString();
        ID = (EditText)findViewById(R.id.idCardNumber);
        String idd= ID.getText().toString();
        phn=(EditText)findViewById(R.id.phoneNumber);
        String phnn= phn.getText().toString();
        count=(EditText)findViewById(R.id.City_Country);
        String countt= count.getText().toString();
        email= (EditText)findViewById(R.id.emailAddress);
        String emaill= email.getText().toString();
        med= (EditText)findViewById(R.id.medicalConditions);
        String medd= med.getText().toString();
        user= (EditText)findViewById(R.id.Username);
        String userr= user.getText().toString();
        pass= (EditText)findViewById(R.id.Password);
        String passs= pass.getText().toString();

        String str= "register,"+fnn+","+idd+","+phnn+","+emaill+","+countt+","+medd+","+userr+","+passs+",0,0";
        OneShotTask ost= new OneShotTask(str);
        Thread t = new Thread(ost);
        t.start();
        t.join();
        String ans= ost.get();

        if (!(ans.equals("Patient already registered!") || ans.equals("Username already taken!"))){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, ans.substring(0,28), duration);
            toast.show();
            Intent intent = new Intent(this, temp_activity.class);
            String dosenum="1";

            //Transmit info needed to send email to next page:
            intent.putExtra("idd", idd);
            intent.putExtra("dose_number", dosenum);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, ans, duration);
            toast.show();

        }




    }

}