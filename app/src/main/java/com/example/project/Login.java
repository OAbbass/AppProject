package com.example.project;

import android.content.Intent;
import android.widget.Button;

import androidx.activity.result.contract.ActivityResultContracts;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button loginbutton;
    public Button CrtAcc;
    private EditText email;
    private EditText password;
    public static String user;
    public static String pass;


    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbutton = (Button) findViewById(R.id.login);
        CrtAcc = (Button) findViewById(R.id.signup);

        CrtAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCrtAcc();
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmain();
            }
        });
    }


    public void openmain()
    {
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);

        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else
                {
                    Toast.makeText(Login.this, "Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openCrtAcc()
    {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }


    }
