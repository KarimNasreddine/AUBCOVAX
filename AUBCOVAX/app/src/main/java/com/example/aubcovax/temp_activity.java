package com.example.aubcovax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class temp_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


        Button AppBtn = (Button)findViewById(R.id.Book);

        AppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Send_email();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void Send_email() throws InterruptedException {
        Intent intent = getIntent();
        String id_number = intent.getStringExtra("idd");
        String dose_num= intent.getStringExtra("dose_number");
        String str= "sendemail,"+id_number+","+dose_num;
        OneShotTask ost= new OneShotTask(str);
        Thread t = new Thread(ost);
        t.start();
        t.join();
        String ans= ost.get();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, ans, duration);
        toast.show();
        Intent intentt = new Intent(this, MainActivity.class);
        startActivity(intentt);
        }




    }