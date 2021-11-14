package com.example.hci_hw;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jokegenerator.JokeMainActivity;
import com.example.newsapp.NewsMainActivity;
import com.example.weatherapp.view.ui.SplashActivity;

import java.text.DateFormat;
import java.util.Calendar;

import in.basulabs.shakealarmclock.Activity_AlarmsList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView AlarmCV, WeatherCV, NewsCV, JokesCV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To get Date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate);

        AlarmCV = (CardView) findViewById(R.id.cardViewAlarm);
        WeatherCV = (CardView) findViewById(R.id.cardViewNews);
        NewsCV = (CardView) findViewById(R.id.cardViewJoke);
        JokesCV = (CardView) findViewById(R.id.cardViewWeather);

        AlarmCV.setOnClickListener(this);
        WeatherCV.setOnClickListener(this);
        NewsCV.setOnClickListener(this);
        JokesCV.setOnClickListener(this);


        }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.cardViewAlarm :
                intent = new Intent(this, Activity_AlarmsList.class);
                startActivity(intent);
                break;
            case R.id.cardViewNews :
                intent = new Intent(this, NewsMainActivity.class);
                startActivity(intent);
                break;
            case R.id.cardViewJoke :
                intent = new Intent(this, JokeMainActivity.class);
                startActivity(intent);
                break;
            case R.id.cardViewWeather :
                intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                break;
        }
    }

}




