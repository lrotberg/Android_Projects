package com.example.exoli.myapplication.res;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class SensorService extends Service implements SensorEventListener {

    public interface SensorServiceListener {
        void onSensorChanged(float[] values);
    }

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorServiceListener sensorServiceListener;

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);

        return new SensorServiceBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        setSensorServiceListener(null);
        sensorManager.unregisterListener(this);

        return super.onUnbind(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorServiceListener != null) {
            sensorServiceListener.onSensorChanged(sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void setSensorServiceListener(SensorServiceListener sensorServiceListener) {
        this.sensorServiceListener = sensorServiceListener;
    }

    public class SensorServiceBinder extends Binder {
       public void setListener(SensorServiceListener listener) {
            setSensorServiceListener(listener);
        }
    }
}
