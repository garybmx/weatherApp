package com.example.weatherapp.model;

public class WeatherModel {
    private Integer temperatureValue;
    private Integer humidityValue;
    private String windCourse;
    private Integer windValue;
    private String cloudsType;

    public Integer getTemperatureValue() {
        return temperatureValue;
    }

    public Integer getHumidityValue() {
        return humidityValue;
    }

    public String getWindCourse() {
        return windCourse;
    }

    public Integer getWindValue() {
        return windValue;
    }

    public String getCloudsType() {
        return cloudsType;
    }
}
