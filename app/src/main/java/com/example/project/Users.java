package com.example.project;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Users {

    private String firstname;
    private String lastname;
    private String age;
    private String type;
    private String email;
    private String gender;
    private String ID;
    private String SessionsRegisteredID;
    private String expdate;


    public Users() {
    }

    public Users(String firstname, String lastname, String age, String type, String email, String gender, String ID, String SessionsRegisteredID, String expdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.type = type;
        this.email = email;
        this.gender = gender;
        this.ID = ID;
        this.SessionsRegisteredID = SessionsRegisteredID;
        this.expdate = expdate;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setSessionsRegisteredID(String sessionsRegisteredID) {
        SessionsRegisteredID = sessionsRegisteredID;
    }
    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }



    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getAge() {
        return age;
    }
    public String getType() {
        return type;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getID() {
        return ID;
    }
    public String getSessionsRegisteredID() {
        return SessionsRegisteredID;
    }
    public String getExpdate() {
        return expdate;
    }
}
