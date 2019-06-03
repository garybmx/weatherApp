package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.model.WeatherModel;

import java.util.ArrayList;

public class WetherTodayFragment extends Fragment{

    WeatherService weatherService;
    ArrayList<WeatherModel> weatherArray;
    String cityName;
    TextView cityText;
    NavigationView navigationView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherService = WeatherService.getinstance();
        weatherArray = weatherService.getWeather();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityName = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE).getString(MainActivity.APP_CITY_NAME, "");
        cityText = view.findViewById(R.id.city_text);
        cityText.setVisibility(View.VISIBLE);
        cityText.setText(cityName);
        navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_day);
    }

    public int getIndex() {
        int index = getArguments().getInt("index", 0);
        return index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_weather_fragment, container, false);
    }

}
