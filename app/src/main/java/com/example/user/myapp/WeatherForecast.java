package com.example.user.myapp;

import android.graphics.Color;

public class WeatherForecast {
    private boolean isGeoId;
    private int geoid;
    private int lat;
    private int lon;
    private String lang;
    private int limit;
    private boolean hours;
    private boolean colors;
    private boolean extra;

    public int getGeoid() {
        return geoid;
    }

    public void setGeoid(int geoid) {
        this.geoid = geoid;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
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

    public boolean isHours() {
        return hours;
    }

    public void setHours(boolean hours) {
        this.hours = hours;
    }

    public boolean isColors() {
        return colors;
    }

    public void setColors(boolean colors) {
        this.colors = colors;
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
