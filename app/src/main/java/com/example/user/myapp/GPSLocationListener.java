package com.example.user.myapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class GPSLocationListener extends IntentService implements LocationListener {

    private LocationManager locationManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *

     */
    public GPSLocationListener() {
        super("service");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                100, 1000, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                100, 100, this);
        LocationWeatherStorage.currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Log.e(getClass().getName(),"onCreate");
        if(LocationWeatherStorage.currentLocation != null){
            Log.e("Loc",LocationWeatherStorage.currentLocation.toString());
        }
        LocationWeatherStorage.currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(LocationWeatherStorage.currentLocation != null){
            Log.e("Loc",LocationWeatherStorage.currentLocation.toString());
        }
        super.onCreate();
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.e("MainActivity", "onLocationChanged");
        LocationWeatherStorage.currentLocation = location;

        //Log.d("tag", String.valueOf(currentLocation.getLatitude()));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("MainActivity", "onStatusChanged");


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onProviderEnabled(String provider) {


        Log.e("MainActivity", "onProviderEnabled");
        LocationWeatherStorage.currentLocation = locationManager.getLastKnownLocation(provider);
//        Log.e("Provider", provider + currentLocation.toString());

    }

    @Override
    public void onProviderDisabled(String provider) {


        Log.e("MainActivity", "onProviderDisabled");


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(getClass().getName(),"Handle");

    }
};
