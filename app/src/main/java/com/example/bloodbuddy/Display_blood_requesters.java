package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_blood_requesters extends AppCompatActivity {


    private TextView Donor_name_display, donor_blood_grp_display;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2;
    ProgressBar delete_acoount_progress_bar;
    RecyclerView requester_recyclerView;
    ArrayList<Requester_info> list1;
    Display_requester_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_blood_requesters);

        Donor_name_display              = (TextView) findViewById(R.id.Donor_name_display);
        donor_blood_grp_display         = (TextView) findViewById(R.id.donor_blood_grp_display);
        delete_acoount_progress_bar     = (ProgressBar) findViewById(R.id.delete_account_progress_bar);
        requester_recyclerView          = (RecyclerView) findViewById(R.id.display_requester_recycler_view);
        requester_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list1                           = new ArrayList<Requester_info>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user!=null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Donor").child(user.getUid());
            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Requester");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        Requester_info value = dataSnapshot1.getValue(Requester_info.class);
                        list1.add(value);

                    }
                   adapter = new Display_requester_adapter(Display_blood_requesters.this, list1);
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

            display_donor_name_bloodgrp();
        }

        else {

            finish();
            Intent return_to_donor_login_if_not_logged_in = new Intent(Display_blood_requesters.this, Blood_donate_login.class );
            startActivity(return_to_donor_login_if_not_logged_in);


             }

    }

    private void display_donor_name_bloodgrp() {

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
                Intent logout_intent = new Intent(Display_blood_requesters.this, Blood_donate_login.class);
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

                finish();
                Intent account_deleted = new Intent(Display_blood_requesters.this,Blood_donate_login.class);
                account_deleted.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(account_deleted);


                }
                break;


        }
        return true;
    }

    private void update_user_data_to_nulls(String uid) {
            Donor_info donor_info = new Donor_info("null","null","null","null","null","null","null","null");
            databaseReference.setValue(donor_info);


    }

    /*private void delete_user_from_realtime_database(String uid) {
        databaseReference.removeValue();
        Toast.makeText(getApplicationContext(),"Firebase realtime database cleared.", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Account deleted completely" , Toast.LENGTH_SHORT).show();
    }*/
}
