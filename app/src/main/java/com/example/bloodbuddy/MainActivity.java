package com.example.bloodbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button donate_blood;
   private  Button request_blood ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donate_blood = (Button) findViewById(R.id.donate);
        request_blood = (Button) findViewById(R.id.request);

        donate_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Blood_donate_login_intent = new Intent(MainActivity.this, Blood_donate_login.class);
                startActivity(Blood_donate_login_intent);            }
        });

        request_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Blood_request_login_intent = new Intent(MainActivity.this, Blood_request_login.class);
                startActivity(Blood_request_login_intent);

            }
        });

    }
}
