package com.example.apiconnectionweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherAPIinfo extends AppCompatActivity {
    RequestQueue queue;// Declare RequestQueue as 'queue' using volley libraries
    private String location, description, setCity;
    private double temperature = 0, windSpeed = 0, feels = 0;

    //--------onCreate-----------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_apiinfo);
        //----receiving userinput city name from main activity-----//
        Bundle extras = getIntent().getExtras();
        String city = extras.getString("CITYNAME");

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=213fde37e5daeb4a1304c3aa11c187da&units=metric";

        //------instantiate new web request queue----
        queue = Volley.newRequestQueue(this);
        //----- Request a string response from the provided URL.---//
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    //Log.d("Weather App",response);
                    parseJASONandUpdateUI(response);
                }, error -> {
            //Log.d("Weather App",error.toString());
        });
        queue.add(stringRequest);
    }

    //-------------onRestore load SaveState-------
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //check if there is a saved UI data bundle, if so read from there using keys
        setCity = savedInstanceState.getString("SETCITY");
        description = savedInstanceState.getString("DESCRIPTION");
        location = savedInstanceState.getString("LOCATION");
        temperature = savedInstanceState.getDouble("TEMP", 10);
        windSpeed = savedInstanceState.getDouble("WINDSPEED", 2);
        feels = savedInstanceState.getDouble("FEELS", 6);

        TextView locationView = findViewById(R.id.locationView2);
        locationView.setText(location);
        TextView cityView = findViewById(R.id.tvCityAPI);
        cityView.setText(setCity);
        TextView tempView = findViewById(R.id.tempView2);
        tempView.setText("" + temperature + "C");
        TextView windView = findViewById(R.id.windView2);
        windView.setText("" + windSpeed + "m/s");
        TextView weatherConditionView = findViewById(R.id.weatherConditionView2);
        weatherConditionView.setText(description);
        TextView feelsLikeView = findViewById(R.id.feelsLikeView2);
        feelsLikeView.setText("Feel like:" + feels + "C");

        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.sonw);
    }

    //--------onDestroy cycle-----
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //-----onSave--Saving before onDestroy is called---saves data with key and values//
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("LOCATION", location);
        outState.putString("SETCITY", setCity);
        outState.putString("DESCRIPTION", description);
        outState.putDouble("TEMP", temperature);
        outState.putDouble("WINDSPEED", windSpeed);
        outState.putDouble("FEELS", feels);
    }

    // ------Convert JSON Object from JSONString(Got from StringRequest through API link)------//
    private void parseJASONandUpdateUI(String response) {
        try {
            JSONObject weatherResponse = new JSONObject(response);

            //----getting the targeted fields from JSON Object----//
            location = weatherResponse.getString("name");
            setCity = weatherResponse.getString("name");
            //setIcon = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("icon");
            temperature = weatherResponse.getJSONObject("main").getDouble("temp");
            windSpeed = weatherResponse.getJSONObject("wind").getDouble("speed");
            description = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("main");
            feels = weatherResponse.getJSONObject("main").getDouble("feels_like");

            //-----set the value in respective UI----//
            TextView locationView = findViewById(R.id.locationView2);
            locationView.setText(location);
            TextView cityView = findViewById(R.id.tvCityAPI);
            cityView.setText(setCity);
            TextView tempView = findViewById(R.id.tempView2);
            tempView.setText("" + temperature + "C");
            TextView windView = findViewById(R.id.windView2);
            windView.setText("" + windSpeed + "m/s");
            TextView weatherConditionView = findViewById(R.id.weatherConditionView2);
            weatherConditionView.setText(description);
            TextView feelsLikeView = findViewById(R.id.feelsLikeView2);
            feelsLikeView.setText("Feel like:" + feels + "C");
            if (description.contains("Clear")) {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.sunny);
            } else if (description.contains("Snow")) {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.sonw);
            } else if (description.contains("Clouds")) {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.cloudy);
            } else if (description.contains("Haze")) {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.haze_2);
            }else if (description.contains("Rain")) {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.rain);
            }else if (description.contains("Drizzle")) {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.drizzle);
            } else {
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageResource(R.drawable.resource_else);
            }

        } catch (JSONException e) {
            //error when JASON parsing //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }
}

