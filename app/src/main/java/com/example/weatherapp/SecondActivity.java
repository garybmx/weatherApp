package com.example.weatherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    TextView cityText;
    TextView textDate;
    TextView textWindCourse;
    TextView textWindSpeed;
    LinearLayout dampBlock;
    LinearLayout windBlock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        cityText = findViewById(R.id.city_text);
        textDate = findViewById(R.id.date_text);
        textWindCourse = findViewById(R.id.wind_course_text);
        textWindSpeed = findViewById(R.id.wind_value_text);
        dampBlock = findViewById(R.id.damp_block);
        windBlock = findViewById(R.id.wind_block);

        setViewElements();
    }

    private void setViewElements() {
        if(getIntent().hasExtra(MainActivity.cityExtra)) {
            cityText.setText(getIntent().getStringExtra(MainActivity.cityExtra));
        }
        if(getIntent().hasExtra((MainActivity.isDateExtra))){
            textDate.setVisibility(View.VISIBLE);
            Date date = new Date();
            textDate.setText(String.format("%1te %2tB %3tY %4tA", date, date,date, date));
        }
        if(getIntent().hasExtra((MainActivity.isHumidityExtra))){
            dampBlock.setVisibility(View.VISIBLE);
        }
        if((!getIntent().hasExtra(MainActivity.isWindCourseExtra)) && (!getIntent().hasExtra(MainActivity.isWindSpeedExtra))){
            windBlock.setVisibility(View.GONE);
        }
        if(getIntent().hasExtra(MainActivity.isWindCourseExtra)){
            textWindCourse.setVisibility(View.VISIBLE);
        }
        if(getIntent().hasExtra(MainActivity.isWindSpeedExtra)){
            textWindSpeed.setVisibility(View.VISIBLE);
        }
    }
}
