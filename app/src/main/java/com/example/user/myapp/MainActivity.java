package com.example.user.myapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapp.weatherInfo.FactWeather;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.example.user.myapp.LocationWeatherStorage.currentLocation;

public class MainActivity extends AppCompatActivity {

    public static String WEATHER_FORECAST_FORMAT;
    public static String LAT_LON_FORMAT;
    public static String GEO_FORMAT;
    public static String KEY;
    public static final int PERMISSION_REQUEST_CODE = 1;

   // = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

    private TextView tempTv;
    private Button tempBut;
    private ImageView weatherImageView;
    private TextView humidityTextView;
    private TextView pressureTextView;
    private TextView windSpeedTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WEATHER_FORECAST_FORMAT = getString(R.string.weather_forecast_format);
        LAT_LON_FORMAT = getString(R.string.lat_lon_format_string);
        GEO_FORMAT = getString(R.string.geo_format_string);
        KEY = getString(R.string.api_key);

        tempTv = (TextView) findViewById(R.id.temperatureTextView);
        tempBut = (Button) findViewById(R.id.tempBut);
        weatherImageView = (ImageView)findViewById(R.id.icon);
        humidityTextView = (TextView)findViewById(R.id.humidity);
        pressureTextView = (TextView)findViewById(R.id.pressure);
        windSpeedTextView = (TextView)findViewById(R.id.wind_speed);
        getLocation();
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            askToTurnOnGPS();
        }



        tempBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeatherForecast wf = new WeatherForecast(false, 0, currentLocation.getLatitude(), currentLocation.getLongitude(), "ru_RU", 3, false);
                TempAsyncTask weatherTask = new TempAsyncTask();
                weatherTask.execute(wf);
            }
        });
    }

    private void askToTurnOnGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("");
        builder.setMessage(R.string.turn_on_GPS_string);
        builder.setPositiveButton(getString(R.string.ok_message), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent activationGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(activationGPSIntent);
            }
        });
        builder.setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                Log.d("Log", permission);
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }



                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                },
                                PERMISSION_REQUEST_CODE);
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void askForPermissions(){


            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


    }
    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                askForPermissions();

        }
        Log.e("getLocation","Intent");
        Intent intentMyIntentService = new Intent(this, GPSLocationListener.class);
        startService(intentMyIntentService);
                    //currentLocation = locationListener.getCurrentLocation();
    }
    class TempAsyncTask extends AsyncTask<WeatherForecast,Integer,FactWeather> {
        @Override
        protected void onPostExecute(FactWeather factWeather) {
            weatherImageView.setImageBitmap(factWeather.getBitmapICon());

            humidityTextView.setText(String.format(getString(R.string.humidity),factWeather.getMain().getHumidity()));
            windSpeedTextView.setText(String.format(getString(R.string.wind_speed),factWeather.getWind().getSpeed()));
            pressureTextView.setText(String.format(getString(R.string.pressure),factWeather.getMain().getPressure()));
            double kelvinT = factWeather.getMain().getTemp();
            int celsius = (int)(kelvinT - 273.15);
            tempTv.setText(String.format(getString(R.string.temperature),celsius));
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
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                if (weatherForecasts[0].isGeoId()) {

                    geoOrLon = String.format(MainActivity.GEO_FORMAT, wF.getGeoid());
                    url = new URL(String.format(MainActivity.WEATHER_FORECAST_FORMAT,
                            geoOrLon, wF.getGeoid(), wF.getLang(), wF.getLimit(), wF.isExtra()));

                } else {

                    geoOrLon = String.format(MainActivity.LAT_LON_FORMAT, (int) wF.getLat(), (int) wF.getLon());
                    Log.e("TempAsyncTask", geoOrLon);
                    Log.e("TempAsyncTask", String.format(MainActivity.WEATHER_FORECAST_FORMAT,
                            geoOrLon,MainActivity.KEY));
                    url = new URL(String.format(MainActivity.WEATHER_FORECAST_FORMAT,
                            geoOrLon,MainActivity.KEY));

                }
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));


                String inputLine;
                // построчно считываем результат в объект StringBuilder
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                    Log.e("Ответ", inputLine);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return  getFactWeather(stringBuilder.toString());

        }
        private FactWeather getFactWeather(String jsonWeather){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            FactWeather weather = null;
            try {
               weather = new FactWeather();
                JsonNode node = objectMapper.readTree(jsonWeather);

                weather.setCoord(objectMapper,node);
                weather.setClouds(objectMapper,node);
                weather.setMain(objectMapper,node);
                weather.setSys(objectMapper,node);
                weather.setWind(objectMapper,node);
                weather.setWeather(objectMapper,node);
             //   weather.setClouds(objectMapper,node);
                Log.e("getFactWeather",weather.toString());

                InputStream in = new URL(String.format(getString(R.string.icon_url),weather.getWeather().getIcon())).openStream();
                weather.setBitmapICon(BitmapFactory.decodeStream(in));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
