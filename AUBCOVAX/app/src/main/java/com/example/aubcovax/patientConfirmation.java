package com.example.aubcovax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class patientConfirmation extends AppCompatActivity {

    EditText usernn, nc;
    EditText fnc, idc, nbc,emailc,countc,medc,dosec;
    RecyclerView rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_confirmation);
        nc= (EditText) findViewById(R.id.username_Constant);
        Intent j= getIntent();
        String name= j.getStringExtra("username");
        nc.setText(name);


        Button getinfo = (Button)findViewById(R.id.getinfo);
        getinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernn= (EditText)findViewById(R.id.username_Constant);
                String username= usernn.getText().toString();

                //Sending sign in information to server to check patient's credentials
                String str2= "patientinfo,"+username;
                OneShotTask ost2= new OneShotTask(str2);
                Thread t2 = new Thread(ost2);
                t2.start();
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String ans2= ost2.get();

                //Split ans2 into different fields:
                String[] parts = ans2.split("!");
                String name= parts[0];
                String id= parts[1];
                String number= parts[2];
                String email= parts[3];
                String countr= parts[4];
                String med_cond= parts[5];
                String di1= parts[6];
                String di2= parts[7];
                String di3= parts[8];
                String di= di1+"\n"+di2+"\n"+di3;

                fnc= (EditText)findViewById(R.id.fullName_Constant);
                fnc.setText(name);
                idc= (EditText)findViewById(R.id.idCardNumber_Constant);
                idc.setText(id);
                nbc= (EditText)findViewById(R.id.phoneNumber_Constant);
                nbc.setText(number);
                emailc= (EditText)findViewById(R.id.emailAddress_Constant);
                emailc.setText(email);
                countc= (EditText)findViewById(R.id.City_Country_Constant);
                countc.setText(countr);
                medc= (EditText)findViewById(R.id.medicalConditions_Constant);
                medc.setText(med_cond);
                dosec= (EditText) findViewById(R.id.doseinfo);
                dosec.setText(di);




            }
        });


    }

    public void getinfo(){

    }


}