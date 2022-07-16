package com.example.project;
import android.content.Intent;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;



    public class viewPackages extends AppCompatActivity {
        private Button privateS;
        private Button groupS;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_packages);

            privateS = (Button) findViewById(R.id.privateSession);
            groupS = (Button) findViewById(R.id.groupSessions);
            privateS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPrivate();
                }
            });
            groupS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openGroup();
                }
            });
        }

        public void openPrivate() {
            Intent intent3 = new Intent(this, PrivateSession.class);
            startActivity(intent3);
        }
        public void openGroup() {
            Intent intent4 = new Intent(this, GroupSession.class);
            startActivity(intent4);
        }

    }
