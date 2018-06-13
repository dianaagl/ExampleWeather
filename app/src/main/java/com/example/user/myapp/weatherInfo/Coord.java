package com.example.user.myapp.weatherInfo;

public class Coord {
    private int lon;
    private int lat;
    public Coord() {
        super();
    }

    public Coord(int lon, int lat) {
        super();
        this.lon = lon;
        this.lat = lat;
    }



    public void setLon(int lon) {
        this.lon = lon;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }



    public int getLon() {
        return lon;
    }

    public int getLat() {
        return lat;
    }
    @Override
    public String toString() {
        return "Coord{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }

}
