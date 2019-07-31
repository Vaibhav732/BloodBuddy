package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_blood_donors extends AppCompatActivity {


    private TextView Donor_name_display, donor_blood_grp_display;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2;
    RecyclerView requester_recyclerView;
    ArrayList<Donor_info> list1;
    Display_donor_adapter adapter;
    ProgressBar delete_acoount_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_blood_donors);

        Donor_name_display      = (TextView) findViewById(R.id.Requester_name_display);
        donor_blood_grp_display = (TextView) findViewById(R.id.Requester_blood_grp_display);
        delete_acoount_progress_bar = (ProgressBar) findViewById(R.id.Requester_delete_account_progress_bar);
        requester_recyclerView          = (RecyclerView) findViewById(R.id.display_donor_recycler_view);
        requester_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list1                           = new ArrayList<Donor_info>();


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user!=null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Requester").child(user.getUid());
            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Donor");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        Donor_info value = dataSnapshot1.getValue(Donor_info.class);
                        list1.add(value);

                    }
                    adapter = new Display_donor_adapter(Display_blood_donors.this, list1);
                    requester_recyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Could not fetch corresponding user." , Toast.LENGTH_SHORT).show();
                }
            });
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            requester_donor_name_bloodgrp();
        }

        else {

            finish();
            Intent return_to_donor_login_if_not_logged_in = new Intent(Display_blood_donors.this, Blood_request_login.class );
            startActivity(return_to_donor_login_if_not_logged_in);


        }

    }

    private void requester_donor_name_bloodgrp() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               String donor_name   = dataSnapshot.child("name").getValue().toString();

                //String donor_name   = dataSnapshot.getRef().getParent().getPath().toString().replace("/","").trim();
                // (the above line accesses and displays  the word Donor)

               String blood_grp    = dataSnapshot.child("blood_grp").getValue().toString();

                Donor_name_display.setText(donor_name);
                donor_blood_grp_display.setText(blood_grp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                //Toast.makeText(getApplicationContext(),"Could not fetch name and blood group.",Toast.LENGTH_LONG).show();
                Donor_name_display.setText("No name");
                donor_blood_grp_display.setText("No blood grp");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.Logout:
            { FirebaseAuth.getInstance().signOut();
                finish();
                Intent logout_intent = new Intent(Display_blood_donors.this, Blood_request_login.class);
                logout_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logout_intent);}
            break;

            /*case R.id.delete_account:
                final AlertDialog.Builder delete_acoount_warning = new AlertDialog.Builder(Display_blood_requesters.this);
                delete_acoount_warning.setTitle("Are you sure?");
                delete_acoount_warning.setMessage("You will loose all your access to the app and you may have to register again.");
                delete_acoount_warning.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete_acoount_progress_bar.setVisibility(View.VISIBLE);

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                               // finish();
                                delete_acoount_progress_bar.setVisibility(View.GONE);

                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext() , "Account deleted from firebase auth",Toast.LENGTH_SHORT).show();
                                    delete_user_from_realtime_database(user.getUid());

                                    Toast.makeText(getApplicationContext(),"Account deleted completely!",Toast.LENGTH_SHORT).show();
                                    Intent account_deleted = new Intent(Display_blood_requesters.this,Blood_donate_login.class);
                                    //account_deleted.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(account_deleted);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                delete_acoount_warning.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = delete_acoount_warning.create();
                alertDialog.show();

                break;
*/
            case R.id.delete_account:

            {
                user.delete();   //this line deletes user account from firebase auth
                Toast.makeText(getApplicationContext(),"Firebase Auth cleared", Toast.LENGTH_SHORT).show();
                //delete_user_from_realtime_database(user.getUid());

                update_user_data_to_nulls(user.getUid());


                Toast.makeText(getApplicationContext(),"(Ab to sri lines display ho gyi!)", Toast.LENGTH_SHORT).show();
                finish();
                Intent account_deleted = new Intent(Display_blood_donors.this,Blood_request_login.class);
                account_deleted.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(account_deleted);


            }
            break;


        }
        return true;
    }

    private void update_user_data_to_nulls(String uid) {
        Requester_info donor_info = new Requester_info("null","null","null","null","null","null","null","null");
        databaseReference.setValue(donor_info);
       // Toast.makeText(getApplicationContext(),"mne chupke se value ko null naam de dia h ",Toast.LENGTH_SHORT).show();


    }

    /*private void delete_user_from_realtime_database(String uid) {
        databaseReference.removeValue();
        Toast.makeText(getApplicationContext(),"Firebase realtime database cleared.", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Account deleted completely" , Toast.LENGTH_SHORT).show();
    }*/
}
