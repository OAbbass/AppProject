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
import java.util.Vector;

public class Session {

    private String sess;
    private String ty;
    private String tra;
    private String dat;
    private String ti;
    private String sessionID;
    private Integer size;
    private Integer currentIn;
    private String trainerID;
    private String attendees;
    private String trainerEM;



    public Session() {
    }

    public Session(String sess, String ty, String tra, String dat, String ti, String sessionID, Integer size, Integer currentIn, String attendees, String trianerID, String TrainerEm) {
        this.sess = sess;
        this.ty = ty;
        this.tra = tra;
        this.dat = dat;
        this.ti = ti;
        this.sessionID = sessionID;
        this.size = size;
        this.currentIn = currentIn;
        this.attendees  = attendees;
        this.trainerID = trianerID;
        this.trainerEM = trainerEM;
    }





    public void setSess(String sess) {
        this.sess = sess;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public void setTra(String tra) {
        this.tra = tra;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setCurrentIn(Integer currentIn) {
        this.currentIn = currentIn;
    }

    public void setTrainerEM(String trainerEM) {
        this.trainerEM = trainerEM;
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

    public String getSessionID() {
        return sessionID;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getCurrentIn() {
        return currentIn;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getAttendees() {
        return attendees;
    }

    public String getTrainerEM() {
        return trainerEM;
    }

}
