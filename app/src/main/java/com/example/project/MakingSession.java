package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.Vector;

public class MakingSession extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner sess;
    public Spinner sty;
    public Spinner tim;
    public Button button;
    EditText first;
    EditText Day;


    public Calendar calendar;


    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    DatabaseReference fStore;
    private DatabaseReference myRef;


    String ID;
    String typecheck;
    String date;
    String TrainerID;

    String Fullname;
    String TheSession;
    String TheType;
    String TheDate;
    String TheTime;


    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MakingSession.this, Calendar.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_session);


        button = (Button) (findViewById(R.id.finish));
        sess = (Spinner) (findViewById(R.id.session));
        sty = (Spinner) (findViewById(R.id.stype));
        tim = (Spinner) (findViewById(R.id.time));
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        fStore = FirebaseDatabase.getInstance().getReference();
        first = (EditText) (findViewById(R.id.name));
        Day = (EditText) (findViewById(R.id.Date));

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Sessions, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sess.setAdapter(adapter);
        sess.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.SessionTypes, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sty.setAdapter(adapter2);
        sty.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.TimeSlots, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tim.setAdapter(adapter3);
        tim.setOnItemSelectedListener(this);


        Intent receive = getIntent();
        date = receive.getStringExtra("KEY_SENDER");
        Day.setText(date);


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
                        TrainerID = user.getID();
                        Fullname = user.getFirstname() + " " + user.getLastname();
                        typecheck = user.getType();
                        first.setText(Fullname);
                    }
                        break;
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fullname = Fullname;
                TheDate = date;
                TheTime = tim.getSelectedItem().toString();
                TheSession = sess.getSelectedItem().toString();
                TheType = sty.getSelectedItem().toString();
                String sessionid = fStore.push().getKey();


                if (sty.getSelectedItem().toString().equals("Private"))
                {
                    Session session = new Session(TheSession, TheType, Fullname, TheDate, TheTime, sessionid, 1, 0, "", TrainerID);
                    fStore.child("sessions").child(sessionid).setValue(session).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MakingSession.this, "Created New Session for " + Fullname, Toast.LENGTH_SHORT).show();
                                Intent returnto = new Intent(getApplicationContext(), Calendar.class);
                                startActivity(returnto);
                            }
                            else
                            {
                                Toast.makeText(MakingSession.this, "Error " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Session session = new Session(TheSession, TheType, Fullname, TheDate, TheTime, sessionid, 10, 0, null, TrainerID);
                    fStore.child("sessions").child(sessionid).setValue(session).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MakingSession.this, "Created New Session for " + Fullname, Toast.LENGTH_SHORT).show();
                                Intent returnto = new Intent(getApplicationContext(), Calendar.class);
                                startActivity(returnto);
                            } else {
                                Toast.makeText(MakingSession.this, "Error " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


