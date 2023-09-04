package com.example.aubcovax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class doseConfirmation extends AppCompatActivity {
    EditText phone;
    EditText dosehist;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_confirmation);

        //Button to search for patient's dose information by phone number
        Button SearchBtn = (Button)findViewById(R.id.searchBtn);

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone= (EditText)findViewById(R.id.phoneNumber);
                String ph_number= phone.getText().toString();


                //Sending phonenumber information to server to check dose info of patient with that phone number
                String str1= "patientinfobyphone,"+ph_number;
                OneShotTask ost1= new OneShotTask(str1);
                Thread t1 = new Thread(ost1);
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String ans= ost1.get();

                if (ans.equals("none")) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "This phone number doesn't belong to any registered patient!", duration);
                    toast.show();

                }
                else {
                    Context context = getApplicationContext();
                    dosehist= (EditText) findViewById(R.id.dosehistory);

                    //Split ans into different fields to get the name, ID and dose history and info of the patient:
                    String[] parts = ans.split("!");
                    String name= parts[0];
                    id= parts[1];
                    String di1= parts[2];
                    String di2= parts[3];
                    String di3= parts[4];
                    String di4= parts[5];
                    dosehist.setText("Patient's name: "+name+"\n"+"\n" +"Patient's ID number: "+id+"\n"+"\n"+ "Patient's vaccination history: "+"\n"+di1+"\n"+di2+"\n"+di3+"\n"+di4);
                }

            }
        });


        //Confirm button: used to confirm if the patient has taken 2 doses or not.
        //Therefore, it will check if he has taken 1 dose only and prompt the medical
        //personnel to book an appointment for the second dose. If he has taken both
        //doses, it will send him his certificate by email!

        //Button to search for patient's dose information by phone number

        Button ConfirmBtn = (Button)findViewById(R.id.confirmDose);

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone= (EditText)findViewById(R.id.phoneNumber);
                String ph_numberr= phone.getText().toString();

                //Checking how many doses were taken by patient as described above
                String str2= "dosehistory,getthingsdone,"+ph_numberr;
                OneShotTask ost2= new OneShotTask(str2);
                Thread t2 = new Thread(ost2);
                t2.start();
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String anss= ost2.get();

                if (anss.equals("Patient cannot be found!")) {
                    Context contexttt = getApplicationContext();
                    int durationnn = Toast.LENGTH_SHORT;
                    Toast toasttt = Toast.makeText(contexttt, "Patient cannot be found!", durationnn);
                    toasttt.show();

                } else if (anss.equals("2 doses done!")) {
                    //Handle patients who already took 2 doses
                    Context contexttt = getApplicationContext();
                    int durationnn = Toast.LENGTH_SHORT;
                    Toast toasttt = Toast.makeText(contexttt, "Patient has taken 2 doses!", durationnn);
                    toasttt.show();

                    //Send the certificate by email
                }

                else if (anss.equals("First dose not taken yet")) {
                    Context contexttt = getApplicationContext();
                    int durationnn = Toast.LENGTH_SHORT;
                    Toast toasttt = Toast.makeText(contexttt, "Patient hasn't taken dose 1 yet!", durationnn);
                    toasttt.show();
                }
                else {
                    //Patient has took only one dose. Book appointment for second dose!
                    Context contexttt = getApplicationContext();
                    int durationnn = Toast.LENGTH_SHORT;
                    Toast toasttt = Toast.makeText(contexttt, "Patient's dose 1 confirmed. Need to book appointment for dose 2!", durationnn);
                    toasttt.show();
                    go_temp();
                }



            }
        });

    }

    private void go_temp() {
        Intent intent = new Intent(this, temp_activity.class);
        String dosenum="2";
        intent.putExtra("idd", id);
        intent.putExtra("dose_number", dosenum);
        startActivity(intent);
    }
}