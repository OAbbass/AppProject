package com.example.project;

import android.content.Intent;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    public String userID;


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ViewMembership.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_membership);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");


        firstN = (TextView) findViewById(R.id.first);
        lastN = (TextView) findViewById(R.id.last);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.Gender);
        type = (TextView) findViewById(R.id.type);
        expiration = (TextView) findViewById(R.id.Exp);


//kalogip341@tebyy.com
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Users user = dataSnapshot.getValue(Users.class);
                    userID = mAuth.getCurrentUser().getUid().toString();
                    Log.e("TAG", userID);
                    Log.e("TAG", user.getID().toString());

                    if (userID.equals(user.getID().toString()))
                    {
                        firstN.setText(user.getFirstname().toString());
                        lastN.setText(user.getLastname().toString());
                        age.setText(user.getAge().toString());
                        gender.setText(user.getGender().toString());
                        type.setText(user.getType().toString());
                        expiration.setText(user.getExpdate().toString());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}


