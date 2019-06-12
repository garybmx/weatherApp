package com.example.weatherapp.model;

import java.text.DateFormat;

public class WeatherModel {
    private Integer temperatureValue;
    private Integer humidityValue;
    private String windCourse;
    private Integer windValue;
    private String cloudsType;
    private String lastUpdatedDate;
    private String pressure;

    public WeatherModel(){
        this.lastUpdatedDate = "";
        this.temperatureValue = 0;
        this.humidityValue = 0;
        this.windCourse = "";
        this.windValue = 0;
        this.cloudsType = "";

    }

    public void setPressure(String pressure) {        this.pressure = pressure;
    }

    public void setTemperatureValue(Integer temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public void setHumidityValue(Integer humidityValue) {
        this.humidityValue = humidityValue;
    }

    public void setWindCourse(String windCourse) {
        this.windCourse = windCourse;
    }

    public void setWindValue(Integer windValue) {
        this.windValue = windValue;
    }

    public void setCloudsType(String cloudsType) {
        this.cloudsType = cloudsType;
    }

    public void setLastUpdatedDate(Long mls) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String updateOn = dateFormat.format(mls);
        this.lastUpdatedDate = updateOn;
    }

    public WeatherModel(String LastUpdatedDate, Integer temperatureValue, Integer humidityValue, String windCourse, Integer windValue, String cloudsType, String pressure){
        this.lastUpdatedDate = LastUpdatedDate;
        this.temperatureValue = temperatureValue;
        this.humidityValue = humidityValue;
        this.windCourse = windCourse;
        this.windValue = windValue;
        this.cloudsType = cloudsType;
        this.pressure = pressure;
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
