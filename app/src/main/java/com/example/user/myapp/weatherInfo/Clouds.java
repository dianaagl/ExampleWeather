package com.example.user.myapp.weatherInfo;

public class Clouds {
    private int all ;

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }//"clouds":{"all":75}


}
