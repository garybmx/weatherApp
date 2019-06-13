package com.example.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.database.CityTable;
import com.example.weatherapp.database.DatabaseHelper;
import com.example.weatherapp.rest.OpenWeatherRepo;
import com.example.weatherapp.rest.entites.WeatherRequestRestModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherTodayFragment extends Fragment{
    private final Handler handler = new Handler();
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    WeatherRequestRestModel model = new WeatherRequestRestModel();
    String cityName;
    TextView cityText;
    TextView tempText;
    TextView windText;
    ImageView imageView;
    LinearLayout windBlock;
    NavigationView navigationView;
    SQLiteDatabase database;
    Integer lastTemperature;
    List<Integer> tempList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCityName();
        initViews(view);
        initLastTemperature();
        lastTemperature = getLastTemperature();
        requestRetrofit(cityName);

    }

    private void initLastTemperature() {
        lastTemperature = getLastTemperature();
        if(lastTemperature != null){
            tempText.setVisibility(View.VISIBLE);
            tempText.setText(String.valueOf(lastTemperature) + " \u2103");
        }
    }

    private void initCityName() {
        cityName = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES,
                Context.MODE_PRIVATE).getString(MainActivity.APP_CITY_NAME, "");


    }

    private Integer getLastTemperature() {
        tempList = CityTable.getLastTemperature(cityName, database);
        if(tempList.isEmpty()) return null;
        return tempList.get(0);
    }

    private void initViews(@NonNull View view) {
        cityText = view.findViewById(R.id.city_text);
        cityText.setVisibility(View.VISIBLE);
        cityText.setText(cityName);
        tempText = view.findViewById(R.id.temp_value_text);
        windText = view.findViewById(R.id.wind_value_text);
        imageView = view.findViewById(R.id.imageView);
        windBlock = view.findViewById(R.id.wind_block);
        navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_day);


    }

    private void initDB() {
        database = new DatabaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
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
                            setCloudsImage();
                            setTemperature();
                            saveTemperature();
                            setWind();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                  //      textTemp.setText(R.string.error);
                    }
                });

    }

    private void saveTemperature() {
        if(tempList.isEmpty()){
            CityTable.addCity(cityName, Math.round(model.main.temp), database);
        }
        else {
            CityTable.editCity(cityName, Math.round(model.main.temp), database);
        }
    }

    private void setCloudsImage() {
        String imagePath = getImagePath();
        imageView.setVisibility(View.VISIBLE);
        loadImage(imagePath);
    }

    private String getImagePath() {
        String path;
        switch (model.weather[0].description) {
            case  ("clear sky"):
                path = "https://openweathermap.org/img/w/01d.png";
                break;
            case  ("few clouds"):
                path = "https://openweathermap.org/img/w/02d.png";
                break;
            case  ("scattered clouds"):
                path = "https://openweathermap.org/img/w/03d.png";
                break;
            case  ("broken clouds"):
                path = "http://openweathermap.org/img/w/04d.png";
                break;
            case  ("shower rain"):
                path = "https://openweathermap.org/img/w/09d.png";
                break;
            case  ("rain"):
                path = "https://openweathermap.org/img/w/10d.png";
                break;
            case  ("thunderstorm"):
                path = "https://openweathermap.org/img/w/11d.png";
                break;
            case  ("snow"):
                path = "https://openweathermap.org/img/w/13d.png";
                break;
            case  ("mist"):
                path = "https://openweathermap.org/img/w/50d.png";
                break;
            default:
                path = "";
                break;
        }
        return path;
    }

    private void loadImage(String path) {
        Picasso.get()
                .load(path)
                .resize(200, 200)
                .into(imageView);
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
