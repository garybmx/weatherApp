package com.example.weatherapp;

import com.example.weatherapp.model.WeatherModel;

import java.util.ArrayList;

public class WeatherService {

    private static WeatherService service;

    public static WeatherService getinstance(){
        return service = (service == null) ? new WeatherService() : service;
    }

    private WeatherService(){
    }

    public ArrayList<WeatherModel> getWeather(){
        ArrayList<WeatherModel> wm = new ArrayList<>();
        wm.add(new WeatherModel(25, 68, "NW", 10, "Cloudy"));
        return wm;
    }

}
