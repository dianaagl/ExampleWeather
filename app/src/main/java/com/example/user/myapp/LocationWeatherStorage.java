package com.example.user.myapp;

import android.app.Application;
import android.location.Location;

import com.example.user.myapp.weatherInfo.FactWeather;

public class LocationWeatherStorage extends Application {
    public static Location currentLocation = null;
    public static FactWeather currentWeather = null;
}
