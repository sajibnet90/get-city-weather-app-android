package com.example.apiconnectionweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // calling 'activity_weather_apiinfo' activity from main activity on buttonClick
    public void openAPI_Activity(View view) {
        // ----getting userInput city name and save it as string---//
        TextInputEditText et = findViewById(R.id.cityEtMain);
        String city = et.getText().toString();

        // ----Initiating intent to send to other activity---//
        Intent openAPI= new Intent(this,weatherAPIinfo.class);
        openAPI.putExtra("CITYNAME",city);//---sending userinput city name to 2nd activity using 'key'--//
        startActivity(openAPI);
    }
}