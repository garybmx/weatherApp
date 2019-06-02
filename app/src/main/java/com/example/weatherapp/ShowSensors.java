package com.example.weatherapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowSensors extends Fragment {
    private SensorManager sensorManager;
    private Sensor defTempSensor;
    private Sensor defHumSensor;
    private TextView tempNow;
    private TextView humNow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getContext().getSystemService(getContext().SENSOR_SERVICE);
        defTempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        defHumSensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sensors_fragment, container, false);

        if(defTempSensor != null){
            tempNow = v.findViewById(R.id.temp_now);
            sensorManager.registerListener(listenerTemp, defTempSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(defHumSensor != null){
            humNow = v.findViewById(R.id.humidity_now);
            sensorManager.registerListener(listenerHum, defHumSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemp, defTempSensor);
        sensorManager.unregisterListener(listenerHum, defHumSensor);
    }

    private void showTempSensors(SensorEvent event){
        tempNow.setText(String.valueOf(event.values[0]));

    }

    private void showHumSensors(SensorEvent event){
        humNow.setText(String.valueOf(event.values[0]));

    }

    SensorEventListener listenerTemp = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTempSensors(event);
        }
    };

    SensorEventListener listenerHum = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumSensors(event);
        }
    };

}
