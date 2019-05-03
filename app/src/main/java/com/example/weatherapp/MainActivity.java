package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    Button sendButton;
    EditText cityName;
    Switch isDate;
    CheckBox isHumidity;
    CheckBox isWindCourse;
    CheckBox isWindSpeed;
    public final static String cityExtra = "cityName";
    public final static String isDateExtra = "isDate";
    public final static String isHumidityExtra = "isHumidity";
    public final static String isWindCourseExtra = "isCourseWind";
    public final static String isWindSpeedExtra = "isSpeedWind";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = findViewById(R.id.send_button);
        cityName = findViewById(R.id.city_name);
        isDate = findViewById(R.id.isDate);
        isHumidity = findViewById(R.id.isDamp);
        isWindCourse = findViewById(R.id.isWindCourse);
        isWindSpeed = findViewById(R.id.isWindValue);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(cityExtra, cityName.getText().toString());
                if(isDate.isChecked()) intent.putExtra(isDateExtra, isDate.getText().toString());
                if(isHumidity.isChecked()) intent.putExtra(isHumidityExtra, isHumidity.getText().toString());
                if(isWindCourse.isChecked()) intent.putExtra(isWindCourseExtra, isWindCourse.getText().toString());
                if(isWindSpeed.isChecked()) intent.putExtra(isWindSpeedExtra, isWindSpeed.getText().toString());
                MainActivity.this.startActivity(intent);

            }
        });

    }
}
