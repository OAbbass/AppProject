package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewTrainers extends AppCompatActivity {

    RecyclerView recycle;
    ArrayList<Users> list;
    DatabaseReference databaseReference;
    MyAdapter2 adapter2;
    String trainer = "Trainer";
    FirebaseAuth mAuth;
    private String userID;




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ViewTrainers.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid().toString();
        Log.e("TAG", userID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trainers);

        recycle = (RecyclerView)(findViewById(R.id.Recyleview));

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        list = new ArrayList<>();
        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new MyAdapter2(this, list);
        recycle.setAdapter(adapter2);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    mAuth = FirebaseAuth.getInstance();
                    userID = mAuth.getCurrentUser().getUid().toString();
                    Log.e("TAG", userID);
                    Users user = dataSnapshot.getValue(Users.class);
                    Log.e("TAG", user.getID().toString());
                    if (trainer.equals(user.getType().toString()) && !user.getID().toString().equals(userID))
                    {
                        list.add(user);
                    }
                }
                adapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}