package com.example.project;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    FirebaseFirestore fStore;


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


//        s = String.valueOf(age.getText());
//        int i = Integer.parseInt(s);
//        if (i < 0 || i > 115)
//        {
//            age.setError("Age must be older than 0 and smaller than 115");
//            return;
//        }




        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(CreateAccount.this, finame +" " + laname + " Account Created", Toast.LENGTH_SHORT).show();
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("First Name", finame);
                    user.put("Last Name", laname);
                    user.put("Age", ag);
                    user.put("Type of Account", typ);
                    user.put("Gender", gen);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: User Profile is Created for " + userID);
                        }
                    });
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