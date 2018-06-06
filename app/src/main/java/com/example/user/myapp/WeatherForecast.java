package com.example.user.myapp;

import android.graphics.Color;

public class WeatherForecast {
    private boolean isGeoId;
    private int geoid;
    private double lat;
    private double lon;
    private String lang;//ru_RU
    private int limit;//count of days for forecast
    private boolean extra;

    public WeatherForecast(boolean isGeoId, int geoid, double lat, double lon, String lang, int limit,boolean extra) {
        this.isGeoId = isGeoId;
        this.geoid = geoid;
        this.lat = lat;
        this.lon = lon;
        this.lang = lang;
        this.limit = limit;
        this.extra = extra;
    }

    public WeatherForecast(String lang, int limit,  boolean extra) {
        this.lang = lang;
        this.limit = limit;
        this.extra = extra;
    }

    public int getGeoid() {
        return geoid;
    }

    public void setGeoid(int geoid) {
        this.isGeoId = true;
        this.geoid = geoid;
    }


    public void setLat(int lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }



    public boolean isExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public boolean isGeoId() {
        return isGeoId;
    }

    public void setGeoId(boolean geoId) {
        isGeoId = geoId;
    }
}
