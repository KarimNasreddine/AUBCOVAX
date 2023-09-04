package com.example.aubcovax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class adminPage extends AppCompatActivity {
    EditText ad;
    EditText phone4;
    EditText dosehist;
    EditText nameconst, Idconst, phoneconst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);


        //Display all records related to medical personnel
        String str2= "getmedicalinfo";
        OneShotTask ost2= new OneShotTask(str2);
        Thread t2 = new Thread(ost2);
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ans2= ost2.get();

        ad= (EditText) findViewById(R.id.adminmedicalinfo);
        ad.setText(ans2);

        t2.interrupt();


        //Button to search for patient's dose information by phone number
        Button SearchBtnn = (Button)findViewById(R.id.searchBtn5);

        SearchBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone4= (EditText)findViewById(R.id.phoneNumber4);
                String ph_number4= phone4.getText().toString();


                //Sending phonenumber information to server to check dose info of patient with that phone number
                String str1= "patientinfobyphone,"+ph_number4;
                OneShotTask ost1= new OneShotTask(str1);
                Thread t1 = new Thread(ost1);
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String ans= ost1.get();
                System.out.println("HAHAHAHHA"+ans);

                if (ans.equals("none")) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "This phone number doesn't belong to any registered patient!", duration);
                    toast.show();

                }
                else {
                    Context context = getApplicationContext();
                    dosehist= (EditText) findViewById(R.id.doseinfoconstant);

                    //Split ans into different fields to get the name, ID and dose history and info of the patient:
                    String[] parts = ans.split("!");
                    String name= parts[0];
                    nameconst= (EditText) findViewById(R.id.fullName_Constant);
                    nameconst.setText(name);
                    String id= parts[1];
                    Idconst= (EditText) findViewById(R.id.idCardNumber_Constant);
                    Idconst.setText(id);
                    phoneconst= (EditText) findViewById(R.id.phoneNumber_Constant);
                    phoneconst.setText(ph_number4);
                    String di1= parts[2];
                    String di2= parts[3];
                    String di3= parts[4];
                    String di4= parts[5];
                    dosehist.setText("Patient's vaccination history: "+"\n"+di1+"\n"+di2+"\n"+di3+"\n"+di4);
                }

            }
        });

    }


}