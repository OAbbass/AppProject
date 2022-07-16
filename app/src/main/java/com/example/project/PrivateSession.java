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

public class PrivateSession extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_package);

    }
}