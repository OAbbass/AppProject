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

public class Schedule extends AppCompatActivity {

    private Button boxing;
    private Button fitness;
    private Button yoga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_sessions);
        boxing = (Button) findViewById(R.id.boxing);
        fitness = (Button) findViewById(R.id.fitness);
        yoga = (Button) findViewById(R.id.Yoga);

        boxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openboxing();
            }
        });
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfitness();
            }
        });
        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openyoga();
            }
        });

    }
    public void openboxing() {
        Intent intent6 = new Intent(this, boxingClasses.class);
        startActivity(intent6);
    }
    public void openfitness() {
        Intent intent7 = new Intent(this, fitnessClasses.class);
        startActivity(intent7);
    }
    public void openyoga() {
        Intent intent8 = new Intent(this, yogaClasses.class);
        startActivity(intent8);
    }

}