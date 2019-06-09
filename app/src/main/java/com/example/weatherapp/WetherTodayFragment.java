package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.rest.OpenWeatherRepo;
import com.example.weatherapp.rest.entites.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WetherTodayFragment extends Fragment{
    private final Handler handler = new Handler();
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    WeatherRequestRestModel model = new WeatherRequestRestModel();
    String cityName;
    TextView cityText;
    TextView tempText;
    TextView windText;
    LinearLayout windBlock;
    NavigationView navigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCityName();
        initViews(view);
        requestRetrofit(cityName);
    }

    private void initCityName() {
        cityName = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES,
                Context.MODE_PRIVATE).getString(MainActivity.APP_CITY_NAME, "");
    }

    private void initViews(@NonNull View view) {
        cityText = view.findViewById(R.id.city_text);
        cityText.setVisibility(View.VISIBLE);
        cityText.setText(cityName);
        tempText = view.findViewById(R.id.temp_value_text);
        windText = view.findViewById(R.id.wind_value_text);
        windBlock = view.findViewById(R.id.wind_block);
        navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_day);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_weather_fragment, container, false);
    }

    private void requestRetrofit(String city) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city + ",ru",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            model = response.body();
                            setTemperature();
                            setWind();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                  //      textTemp.setText(R.string.error);
                    }
                });

    }

    private void setWind() {
        String speedText = String.valueOf(Math.round(model.wind.speed)) + " "
                +getResources().getString(R.string.speed_text);
        windBlock.setVisibility(View.VISIBLE);
        windText.setText(speedText);

    }

    private void setTemperature() {
        String text = String.valueOf(Math.round(model.main.temp))+ " \u2103";
        tempText.setVisibility(View.VISIBLE);
        tempText.setText(text);
    }


}
