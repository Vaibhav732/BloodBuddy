package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.GONE;

public class Blood_donate_signup extends AppCompatActivity {


    private Button create_account;
    Button fetch_location;
     EditText Donor_name, Donor_phone, Donor_email, Donor_age, Donor_signin_password;
     TextView show_location, show_lat, show_long;
    private FirebaseAuth mAuth;
    ProgressBar Signup_progress_bar;
    Spinner Blood_grp_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate_signup);


        create_account          =  (Button)      findViewById(R.id.create_account);
        fetch_location          =  (Button)      findViewById(R.id.get_location);
        show_location           =  (TextView)   findViewById(R.id.show_loc);
        show_lat                =  (TextView)   findViewById(R.id.fetched_lat);
        show_long               =  (TextView)   findViewById(R.id.fetched_long);
        Donor_name              =  (EditText)    findViewById(R.id.Donor_name);
        Donor_phone             =  (EditText)    findViewById(R.id.Donor_phone);
        Donor_email             =  (EditText)    findViewById(R.id.Donor_email);
        Blood_grp_spinner       =  (Spinner)     findViewById(R.id.blood_grp_spinner);
        Donor_age               =  (EditText)    findViewById(R.id.Donor_age);
        Donor_signin_password   =  (EditText)    findViewById(R.id.Donor_signin_password);
        Signup_progress_bar     =  (ProgressBar) findViewById(R.id.Signup_progress_bar);


        mAuth = FirebaseAuth.getInstance();

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_new_donor();

            }
        });

        fetch_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Blood_donate_signup.this, UserLocation.class ));
                Intent intent = new Intent(Blood_donate_signup.this , UserLocation.class);
                startActivityForResult(intent, 1);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode ==1){
            if (resultCode == RESULT_OK){
                double fetched_lat = data.getDoubleExtra("final lat", 0);
                double fetched_long = data.getDoubleExtra("final long",0);
                String fetched_address = data.getStringExtra("final address");
                show_location.setText(fetched_address);
                Toast.makeText(getApplicationContext() , "latitude: " + fetched_lat + "\n" + "longitude: " + fetched_long,Toast.LENGTH_LONG).show();

                show_lat.setText(String.valueOf(fetched_lat));
                show_long.setText(String.valueOf(fetched_long));



            }
            if (requestCode == RESULT_CANCELED){
                show_location.setText("Could not get your location.");
                show_lat.setText("Null");
                show_long.setText("Null");
                show_location.setText("Null");
            }

        }
    }

    private void register_new_donor(){
        final String name               = Donor_name.getText().toString().trim();
        final String phone              = Donor_phone.getText().toString().trim();
        final String email              = Donor_email.getText().toString().trim();
        final String blood_grp_spinner  = Blood_grp_spinner.getSelectedItem().toString().trim();
        final String age                = Donor_age.getText().toString();
        final String fetched_lat        = show_lat.getText().toString();
        final String fetched_long       = show_long.getText().toString();
        final String address            = show_location.getText().toString();
        String password                 = Donor_signin_password.getText().toString().trim();


        if (name.isEmpty()){
            Donor_name.setError("name cannot be empty");
            Donor_name.requestFocus();
            return;
        }


        if (phone.isEmpty()){
            Donor_phone.setError("Contacts cannot be empty");
            Donor_phone.requestFocus();
            return;
        }

        if (phone.length()!=10){
            Donor_phone.setError("Please enter a valid contact number!");
            Donor_phone.requestFocus();
            return;
        }

        if (email.isEmpty()){
            Donor_email.setError("E-mail id cannot be empty");
            Donor_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Donor_email.setError("Please provide a valid E-mail id!");
            Donor_email.requestFocus();
            return;
        }

        if (age.isEmpty()){
            Donor_age.setError("Age cannot be empty");
            Donor_age.requestFocus();
            return;
        }

        if (Integer.valueOf(age.trim())<18){
            Donor_age.setError("Donor should be at least 18 yrs old.");
            Donor_age.requestFocus();
            return;

        }

        if (Integer.valueOf(age.trim())>65){
            Donor_age.setError("Donor can't not be more than 65 yrs old.");
            Donor_age.requestFocus();
            return;

        }

        if (address == "(Your location will be displayed here)" || address == "Could not get your location."){

            Toast.makeText(getApplicationContext() ,"Please save your location first.",Toast.LENGTH_SHORT).show();
            show_location.setError("Save your location first.");
            return;

        }


        if (password.isEmpty()){
            Donor_signin_password.setError("Password cannot be empty");
            Donor_signin_password.requestFocus();
            return;
        }

        if (password.length()<6){
            Donor_signin_password.setError("Password should be at least 6 characters long!");
            Donor_signin_password.requestFocus();
            return;
        }

        Signup_progress_bar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>()

                {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Signup_progress_bar.setVisibility(GONE);


                    Donor_info donor_info = new Donor_info(name,  email, phone, blood_grp_spinner, age, fetched_lat, fetched_long, address  );

                    FirebaseDatabase.getInstance().getReference("Donor").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid()).
                            setValue(donor_info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(getApplicationContext(), "Account created successfully.", Toast.LENGTH_LONG).show();
                                Intent login_after_signup_intent = new Intent(Blood_donate_signup.this, Blood_donate_login.class);
                                startActivity(login_after_signup_intent);
                                finish();
                            }

                            else{
                                Toast.makeText(getApplicationContext(),"Account to bnn gya but sari info ni ja pai!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                else {
                    Signup_progress_bar.setVisibility(GONE);

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(), "You have already registered. Please Login", Toast.LENGTH_LONG).show();
                   }
                   else {
                       Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                   }
                }
            }
        });

    }





}
