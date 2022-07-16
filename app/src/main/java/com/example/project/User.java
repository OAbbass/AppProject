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

import java.util.HashMap;
import java.util.Map;

public class User {

    private String sess;
    private String ty;
    private String tra;
    private String dat;
    private String ti;

    public User() {
    }

    public User(String sess, String ty, String tra, String dat, String ti) {
        this.sess = sess;
        this.ty = ty;
        this.tra = tra;
        this.dat = dat;
        this.ti = ti;
    }


    public String getSess() {
        return sess;
    }

    public String getTy() {
        return ty;
    }

    public String getTra() {
        return tra;
    }

    public String getDat() {
        return dat;
    }

    public String getTi() {
        return ti;
    }
}
