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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ViewMembership extends AppCompatActivity {

    public TextView firstN;
    public TextView lastN;
    public TextView age;
    public TextView gender;
    public TextView cert;
    public TextView type;
    public TextView expiration;


    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_membership);

        firstN = (TextView) findViewById(R.id.first);
        lastN = (TextView) findViewById(R.id.last);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.Gender);
        type = (TextView) findViewById(R.id.type);
        expiration = (TextView) findViewById(R.id.Exp);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid().toString();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                firstN.setText(value.getString("First Name"));
                lastN.setText(value.getString("Last Name"));
                age.setText(value.getString("Age"));
                gender.setText(value.getString("Gender"));
                type.setText(value.getString("Type of Account"));
                expiration.setText("TEST");
            }
        });

        String finame;
        String laname;
        String age;
        String gender;
        String type;

    }
}