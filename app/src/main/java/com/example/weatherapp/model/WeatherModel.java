package com.example.weatherapp.model;

public class WeatherModel {
    private Integer temperatureValue;
    private Integer humidityValue;
    private String windCourse;
    private Integer windValue;
    private String cloudsType;

    public WeatherModel(Integer temperatureValue, Integer humidityValue, String windCourse, Integer windValue, String cloudsType){
        this.temperatureValue = temperatureValue;
        this.humidityValue = humidityValue;
        this.windCourse = windCourse;
        this.windValue = windValue;
        this.cloudsType = cloudsType;
    }

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
