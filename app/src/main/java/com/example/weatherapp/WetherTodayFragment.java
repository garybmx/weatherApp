package com.example.weatherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    TextView tempText;
    NavigationView navigationView;
    private MyServiceConnection mConnection = null;
    private boolean isBind = false;
    private WeatherService.ServiceBinder mService = null;
    ArrayList<WeatherModel> weatherToday;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (!isBind) {
             Intent intent = new Intent(getContext(),
                    WeatherService.class);
         getActivity().bindService(intent, mConnection, getActivity().BIND_AUTO_CREATE);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherService = new WeatherService();
        weatherArray = weatherService.getWeather();
        mConnection = new MyServiceConnection();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityName = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE).getString(MainActivity.APP_CITY_NAME, "");
        cityText = view.findViewById(R.id.city_text);
        cityText.setVisibility(View.VISIBLE);
        cityText.setText(cityName);
        tempText = view.findViewById(R.id.temp_value_text);
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

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            /* Get service object (binder) */
            mService = (WeatherService.ServiceBinder) service;
            isBind = mService != null;

            if (isBind) {
                weatherToday =  mService.getService().getWeather();
                tempText.setText(weatherToday.get(0).getTemperatureValue().toString() + " °C");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            mService = null;
        }

    }

    @Override
    public void onDestroy() {
        /* Unbind from a service */
        if (isBind) {
            getActivity().unbindService(mConnection);
            isBind = false;
        }

        super.onDestroy();
    }
}
