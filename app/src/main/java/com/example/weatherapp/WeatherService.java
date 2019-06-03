package com.example.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.weatherapp.model.WeatherModel;

import java.util.ArrayList;


public class WeatherService extends Service {

    /** Object for returning from {@code onBind} method. */
    private final IBinder mBinder = new ServiceBinder();


    /**
     * Method for clients.
     * */
    public ArrayList<WeatherModel> getWeather(){
        ArrayList<WeatherModel> wm = new ArrayList<>();
        wm.add(new WeatherModel(45, 68, "NW", 10, "Cloudy"));
        return wm;
    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.
     * */
    @Override
    public IBinder onBind(Intent intent) { return mBinder; }

    /**
     * Class for returning from {@code onBind} method.
     * */
    class ServiceBinder extends Binder {
        WeatherService getService() {
            return WeatherService.this;
        }
    }

}




