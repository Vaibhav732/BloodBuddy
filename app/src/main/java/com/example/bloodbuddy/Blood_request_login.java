package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Blood_request_login extends AppCompatActivity {

    private Button login ;
    private Button reset_password;
    private Button back_to_signup;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference category_check_ref;
    private EditText Donor_login_email,Donor_login_password;
    ProgressBar login_progress_bar;
    Intent Display_blood_requesters_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_login);

        login                   =  (Button) findViewById(R.id.Requester_login_from_login);
        //reset_password          =  (Button) findViewById(R.id.reset_Requester_password);
        back_to_signup          =  (Button) findViewById(R.id.Request_back_to_signup);
        Donor_login_email       =  (EditText) findViewById(R.id.Requester_login_email);
        Donor_login_password    =  (EditText) findViewById(R.id.Requester_login_password);
        login_progress_bar      =  (ProgressBar) findViewById(R.id.Requester_progress_bar);


        mAuth = FirebaseAuth.getInstance();
        Display_blood_requesters_intent = new Intent(Blood_request_login.this, Display_blood_donors.class);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donor_login();

            }
        });


        /*reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reset_password_intent = new Intent(Blood_request_login.this, Reset_requester_password.class);
                startActivity(reset_password_intent);
            }
        });*/



        back_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_to_signup_intent = new Intent(Blood_request_login.this , Blood_request_Signup.class);
                startActivity(back_to_signup_intent);
            }
        });


    }

    private void donor_login(){
        String email    = Donor_login_email.getText().toString().trim();
        String password = Donor_login_password.getText().toString().trim();


        if (email.isEmpty()){
            Donor_login_email.setError("E-mail id cannot be empty");
            Donor_login_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Donor_login_email.setError("Please provide a valid E-mail id!");
            Donor_login_email.requestFocus();
            return;
        }

        if (password.length()<6){
            Donor_login_password.setError("Password should be at least 6 characters long!");
            Donor_login_password.requestFocus();
            return;
        }

        login_progress_bar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                login_progress_bar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    user = mAuth.getCurrentUser();
                    category_check_ref = FirebaseDatabase.getInstance().getReference().child("Donor").child(user.getUid());
                    category_check_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            finish();
                            Toast.makeText(getApplicationContext(),"Login successful."+"\n"+"Welcome Requester!",Toast.LENGTH_SHORT).show();
                            Display_blood_requesters_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(Display_blood_requesters_intent);




                            /*String categogry_check   = dataSnapshot.getRef().getParent().getPath().toString().replace("/","").trim();
                            Toast.makeText(getApplicationContext(), categogry_check,Toast.LENGTH_SHORT).show();

                            if (categogry_check == "Requester"){
                                finish();
                                Toast.makeText(getApplicationContext(),"Login successful."+"\n"+"Welcome Requester!",Toast.LENGTH_SHORT).show();
                                Display_blood_requesters_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(Display_blood_requesters_intent);
                            }
                            else if((categogry_check == "Donor")){
                                Toast.makeText(getApplicationContext(),"You have registered as a donor," + "\n"+ "not a requester!",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Please go to Donors' portal.",Toast.LENGTH_SHORT).show();
                            }*/
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),"Could not check your category.",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login failed" + "\n" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser()!= null)
        {
            finish();
            Display_blood_requesters_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(Display_blood_requesters_intent);
        }

    }
}
