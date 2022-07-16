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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Calendar extends AppCompatActivity {

    Button button;
    CalendarView calendar;


    public String date;
    String trainer = "Trainer";
    String typecheck;
    String ID;


    public FirebaseAuth mAuth;
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


        ID = mAuth.getCurrentUser().getUid().toString();
        DocumentReference documentReference = fStore.collection("users").document(ID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                typecheck = value.getString("Type of Account");

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
                }

            }
        });





    }


}