package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterCalendar extends AppCompatActivity {

    RecyclerView recycle;
    ArrayList<Session> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    String date;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterCalendar.this, Calendar.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_session);


        recycle = (RecyclerView)(findViewById(R.id.Recyleview));
        databaseReference = FirebaseDatabase.getInstance().getReference("sessions");

        Intent receive2 = getIntent();
        date = receive2.getStringExtra("KEY_SENDER");


        list = new ArrayList<>();
        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recycle.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Session session = dataSnapshot.getValue(Session.class);
                    if (session.getDat().toString().equals(date))
                    {
                        list.add(session);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}