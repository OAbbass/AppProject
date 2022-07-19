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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static final String TAG = "TAG";
    public EditText FirstName;
    private EditText LastName;
    private EditText password;
    private EditText age;
    private EditText Email;
    public Spinner gender;
    public Spinner typeofaccount;
    public Button Finish;
    public String userID;


    public FirebaseAuth mAuth;
    DatabaseReference mStore;
    FirebaseFirestore fStore;

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CreateAccount.this, MainActivity.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        Finish = (Button) (findViewById(R.id.finish));


        FirstName = (EditText) (findViewById(R.id.First));
        LastName = (EditText) (findViewById(R.id.Last));
        password = (EditText) (findViewById(R.id.Password));
        age = (EditText) (findViewById(R.id.Age));
        Email = (EditText) (findViewById(R.id.email));
        gender = (Spinner) (findViewById(R.id.Gender));
        typeofaccount = (Spinner) (findViewById(R.id.Type));


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mStore = FirebaseDatabase.getInstance().getReference();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        typeofaccount.setAdapter(adapter);
        typeofaccount.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gender.setAdapter(adapter2);
        gender.setOnItemSelectedListener(this);

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    public void check()
    {

        String finame = FirstName.getText().toString().trim();
        String laname = LastName.getText().toString().trim();
        String em = Email.getText().toString().trim();
        String ag = age.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String typ = typeofaccount.getSelectedItem().toString();
        String gen = gender.getSelectedItem().toString();

        if (pass.length()<6)
        {
            password.setError("Password must be longer than 6 characters");
            return;
        }
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser account = mAuth.getCurrentUser();
                    account.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(CreateAccount.this, "Verification E-Mail has been sent to " + em, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(CreateAccount.this, "Error " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    userID = mAuth.getCurrentUser().getUid().toString();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Users user = new Users(finame, laname, ag, typ, em, gen, userID);
                    mStore.child("users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(CreateAccount.this, "Created New Account for " + finame + " " + laname, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(CreateAccount.this, "Error " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//                    Toast.makeText(CreateAccount.this, finame +" " + laname + " Account Created", Toast.LENGTH_SHORT).show();
//                    userID = mAuth.getCurrentUser().getUid();
//                    DocumentReference documentReference = fStore.collection("users").document(userID);
//                    Map<String, Object> user = new HashMap<>();
//                    user.put("First Name", finame);
//                    user.put("Last Name", laname);
//                    user.put("Age", ag);
//                    user.put("Type of Account", typ);
//                    user.put("Gender", gen);
//                    user.put("Email", em);
//                    user.put("USER ID", userID)
                }
                else
                {
                    Toast.makeText(CreateAccount.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

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