package com.example.project;

import android.content.Intent;
import android.widget.Button;
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



public class MainActivity extends AppCompatActivity {
    private Button VMS;
    private Button Packages;
    private Button Sched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        VMS = (Button) findViewById(R.id.Membership);
        Packages = (Button) findViewById(R.id.Packages);
        Sched = (Button) findViewById(R.id.scheduling);
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
}