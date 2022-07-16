package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MakingSession extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner sess;
    public Spinner sty;
    public Spinner tim;
    public Button button;
    EditText first;
    EditText Day;


    public Calendar calendar;


    public FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    DatabaseReference fStore;


    String ID;
    String typecheck;
    String date;


    String Fullname;
    String TheSession;
    String TheType;
    String TheDate;
    String TheTime;


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


        ID = mAuth.getCurrentUser().getUid().toString();
        DocumentReference documentReference = mStore.collection("users").document(ID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                typecheck = value.getString("Type of Account");
                Fullname = value.getString("First Name") + " " + value.getString("Last Name");
                first.setText(Fullname);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                DocumentReference documentReference2 = fStore.collection("sessions").document(ID);
//                Map<String, Object> session = new HashMap<>();
//                session.put("TrainerName", Fullname);
//                session.put("Date", date);
//                session.put("Time",tim.getSelectedItem().toString());
//                session.put("Availability", sty.getSelectedItem().toString());
//                session.put("Type of Session", sess.getSelectedItem().toString());

                Fullname = Fullname;
                TheDate = date;
                TheTime = tim.getSelectedItem().toString();
                TheSession = sess.getSelectedItem().toString();
                TheType = sty.getSelectedItem().toString();
                String sessionid = fStore.push().getKey();

                User session = new User(Fullname, TheDate, TheTime, TheSession, TheType);
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

//                documentReference2.set(session).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(MakingSession.this, "Sessio " + Fullname, Toast.LENGTH_SHORT).show();
//                    }
//                });
//

//            }
//        });


        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


