package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Calendar extends AppCompatActivity {

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Calendar.this, MainActivity.class));
        finish();
    }

    Button button;
    CalendarView calendar;


    public String date;
    String trainer = "Trainer";
    String typecheck;
    String ID;


    public FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        calendar = (CalendarView)(findViewById(R.id.calendar));
        button = (Button)(findViewById(R.id.button));
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 + "/" + (i1 + 1) + "/" + i;
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Users user = dataSnapshot.getValue(Users.class);
                    String userID = mAuth.getCurrentUser().getUid().toString();

                    if (userID.equals(user.getID().toString()))
                    {
                        typecheck = user.getType().toString();
                        if (typecheck.equals(trainer))
                        {
                            button.setText("Create Session");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent sender = new Intent(getApplicationContext(), MakingSession.class);
                                    sender.putExtra("KEY_SENDER", date);
                                    startActivity(sender);
                                }
                            });
                        }
                        else
                        {
                            button.setText("Register for Session");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent sender = new Intent(getApplicationContext(), RegisterCalendar.class);
                                    sender.putExtra("KEY_SENDER", date);
                                    startActivity(sender);
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }


}