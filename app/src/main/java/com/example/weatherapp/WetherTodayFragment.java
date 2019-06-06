package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.model.WeatherModel;

import org.json.JSONObject;

public class WetherTodayFragment extends Fragment{
    private final Handler handler = new Handler();
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    WeatherModel weatherToday;
    String cityName;
    TextView cityText;
    TextView tempText;
    NavigationView navigationView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        updateWeatherData("Moscow");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_weather_fragment, container, false);
    }


    private void updateWeatherData(final String city) {
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(city);
                if(jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                         //   Toast.makeText(getApplicationContext(), R.string.place_not_found, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            weatherToday = renderWeather(jsonObject);
                            tempText.setText(weatherToday.getTemperatureValue().toString());
                        }
                    });
                }
            }
        }.start();
    }

    private WeatherModel renderWeather(JSONObject jsonObject) {
        WeatherModel weatherModel = new WeatherModel();
        Log.d(LOG_TAG, "json: " + jsonObject.toString());
        try {
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            weatherModel.setLastUpdatedDate(jsonObject.getLong("dt") * 1000);
            Double md = main.getDouble("temp");
            weatherModel.setTemperatureValue(( Integer.valueOf(md.intValue())));
            weatherModel.setHumidityValue(Integer.valueOf(main.getString("humidity")));
            weatherModel.setCloudsType(details.getString("description"));
            weatherModel.setPressure(main.getString("pressure"));
        } catch (Exception exc) {
            exc.printStackTrace();
            Log.e(LOG_TAG, "One or more fields not found in the JSON data");
        }
        return weatherModel;
    }
}
