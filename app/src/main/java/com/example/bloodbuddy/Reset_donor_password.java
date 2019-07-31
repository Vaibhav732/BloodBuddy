package com.example.bloodbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Reset_donor_password extends AppCompatActivity {

    Button  reset_donor_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_donor_password);


        reset_donor_password = (Button) findViewById(R.id.login_from_reset_window);

        reset_donor_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Password reseted successfully",Toast.LENGTH_SHORT).show();
                Intent account_revived_intent = new Intent(Reset_donor_password.this,Display_blood_requesters.class);
                startActivity(account_revived_intent);
            }
        });

    }
}
