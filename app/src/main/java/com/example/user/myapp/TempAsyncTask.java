package com.example.user.myapp;

import android.os.AsyncTask;

import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class TempAsyncTask extends AsyncTask<WeatherForecast,Integer,FactWeather> {
    @Override
    protected void onPostExecute(FactWeather factWeather) {
        super.onPostExecute(factWeather);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected FactWeather doInBackground(WeatherForecast... weatherForecasts) {
        URL url = null;
        WeatherForecast wF = weatherForecasts[0];
        String geoOrLon;
        BufferedReader reader = null;
        JSONArray ar=new JSONArray();

        try {
            if(weatherForecasts[0].isGeoId()) {

                geoOrLon = String.format(MainActivity.GEO_FORMAT,wF.getGeoid());
                url = new URL(String.format(MainActivity.WEATHER_FORECAST_FORMAT,
                        geoOrLon,wF.getGeoid(),wF.getLang(),wF.getLimit(),wF.isHours(),wF.isColors(),wF.isExtra()));

            }
            else{
                geoOrLon = String.format(MainActivity.LAT_LON_FORMAT,wF.getLat(),wF.getLon());
                url =  new URL(String.format(MainActivity.WEATHER_FORECAST_FORMAT,
                        geoOrLon,wF.getGeoid(),wF.getLang(),wF.getLimit(),wF.isHours(),wF.isColors(),wF.isExtra()));
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            
        } catch (MalformedURLException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
