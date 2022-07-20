package com.example.project;

import android.content.Intent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private Button VMS;
    private Button Packages;
    private Button Sched;
    private Button Trainers;
    private Button verb;

    private TextView ver;

    private String userID;

    FirebaseAuth mAuth;
    FirebaseUser Account;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        VMS = (Button) findViewById(R.id.Membership);
        Packages = (Button) findViewById(R.id.Packages);
        Sched = (Button) findViewById(R.id.scheduling);
        Trainers = (Button) findViewById(R.id.Trainer);
        verb = (Button) findViewById(R.id.verbut);

        ver = (TextView) findViewById(R.id.verify);

        Account = mAuth.getCurrentUser();

        if (!Account.isEmailVerified()) {

            Toast.makeText(MainActivity.this, "All Buttons Are Disabled Pending Verification", Toast.LENGTH_SHORT).show();
            VMS.setEnabled(false);
            Packages.setEnabled(false);
            Sched.setEnabled(false);
            Trainers.setEnabled(false);


            verb.setVisibility(View.VISIBLE);
            ver.setVisibility(View.VISIBLE);

            verb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Account.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this, "Verification E-Mail has been sent" + Account.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }



        VMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmembership();
            }
        });
        Packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpackages();
            }
        });
        Sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensched();
            }
        });

        Trainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opentrian();
            }
        });

    }

    public void openmembership() {
        Intent intent2 = new Intent(this, ViewMembership.class);
        startActivity(intent2);
    }
    public void openpackages() {
        Intent intent5 = new Intent(this, RegisterSession.class);
        startActivity(intent5);
    }
    public void opensched() {
        Intent intent9 = new Intent(this, Calendar.class);
        startActivity(intent9);
    }
    public void opentrian() {
        Intent intent10 = new Intent(this, ViewTrainers.class);
        startActivity(intent10);
    }


}