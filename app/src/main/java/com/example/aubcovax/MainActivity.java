package com.example.aubcovax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText usern, passw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usern= (EditText)findViewById(R.id.Username);
        String username= usern.getText().toString();
        passw = (EditText)findViewById(R.id.Password);
        String password= passw.getText().toString();

        //Register Button
        Button RegisterBtn = (Button)findViewById(R.id.registerUser);


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterPatient();

            }
        });

        //Patient login button
        Button LoginBtn = (Button)findViewById(R.id.loginBtnPatient);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usern= (EditText)findViewById(R.id.Username);
                String username= usern.getText().toString();
                passw = (EditText)findViewById(R.id.Password);
                String password= passw.getText().toString();

                //Sending sign in information to server to check patient's credentials
                String str1= "patientsignin,"+username+","+password;
                OneShotTask ost1= new OneShotTask(str1);
                Thread t1 = new Thread(ost1);
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String ans= ost1.get();

                if (ans.equals("Sign in succesful!")) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Login succesfull", duration);
                    toast.show();
                    PatienttConfirmationTemp();

                }
                else {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Wrong credentials. Please try again", duration);
                    toast.show();
                }

            }
        });

        //Medical Personel login button
        Button MedLoginBtn = (Button)findViewById(R.id.loginBtnMedicalPersonnel);

        MedLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usern= (EditText)findViewById(R.id.Username);
                String username= usern.getText().toString();
                passw = (EditText)findViewById(R.id.Password);
                String password= passw.getText().toString();

                //Sending sign in information to server to check patient's credentials
                String str1= "medicalpers,"+username+","+password;
                OneShotTask ost1= new OneShotTask(str1);
                Thread t1 = new Thread(ost1);
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String ans= ost1.get();

                if (ans.equals("Nurse can be granted access!")) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Login succesfull", duration);
                    toast.show();
                    dose_confirmation();

                }
                else {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Wrong credentials. Please try again", duration);
                    toast.show();
                }

            }
        });


        //Admin login button
        Button AdminBtn = (Button)findViewById(R.id.loginBtnAdmin);


        AdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usern= (EditText)findViewById(R.id.Username);
                String username= usern.getText().toString();
                passw = (EditText)findViewById(R.id.Password);
                String password= passw.getText().toString();

                //Sending sign in information to server to check patient's credentials
                String str2= "adminsignin,"+username+","+password;
                OneShotTask ost2= new OneShotTask(str2);
                Thread t2 = new Thread(ost2);
                t2.start();
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String ans= ost2.get();

                if (ans.equals("Admin can be granted access!")) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Login succesfull", duration);
                    toast.show();
                    openAdminPage();
                } else {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Wrong Credentials. Please try again!", duration);
                    toast.show();
                }

            }
        });



    }

    private void openAdminPage() {
        Intent intent = new Intent(this, adminPage.class);
        startActivity(intent);
    }

    private void dose_confirmation() {
        Intent intent = new Intent(this, doseConfirmation.class);
        startActivity(intent);
    }


    public void openRegisterPatient(){
        Intent intent = new Intent(this, registerPatient.class);
        startActivity(intent);
    }

    public void PatienttConfirmationTemp(){
        String usernmee= ((EditText)findViewById(R.id.Username)).getText().toString();
        Intent intent = new Intent(this, patientConfirmation.class);
        intent.putExtra("username", usernmee);
        startActivity(intent);
    }
}