package com.example.user.myapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    static public String WEATHER_FORECAST_FORMAT;
    static public String LAT_LON_FORMAT;
    static public String GEO_FORMAT;
    static public String KEY;
    public static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locManager;// = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    private Location currentLocation;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView mTempTv = (TextView) findViewById(R.id.temperatureTextView);
        Button tempBut = (Button) findViewById(R.id.tempBut);
        WEATHER_FORECAST_FORMAT = getString(R.string.weather_forecast_format);
        LAT_LON_FORMAT = getString(R.string.lat_lon_format_string);
        GEO_FORMAT = getString(R.string.geo_format_string);
        KEY = getString(R.string.api_key);

        tempBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                   askForPermissions();
               }
               else {
                   getLocation();
                   WeatherForecast wf = new WeatherForecast(false, 0, currentLocation.getLatitude(), currentLocation.getLongitude(), "ru_RU", 3, false);
                   TempAsyncTask weatherTask = new TempAsyncTask();
                   weatherTask.execute(wf);
               }
            }
        });
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
                        getLocation();
                        WeatherForecast wf = new WeatherForecast(false, 0, currentLocation.getLatitude(), currentLocation.getLongitude(), "ru_RU", 3, false);
                        TempAsyncTask weatherTask = new TempAsyncTask();
                        weatherTask.execute(wf);
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

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.e("MainActivity", "onLocationChanged");
            currentLocation = location;
            Log.d("tag", String.valueOf(currentLocation.getLatitude()));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("MainActivity", "onStatusChanged");

            Log.d("tag", String.valueOf(currentLocation.getLatitude()));
            Toast toast = Toast.makeText(getApplicationContext(),

                    "onStatusChanged" , Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public void onProviderEnabled(String provider) {


            Log.e("MainActivity", "onProviderEnabled");
            Toast toast = Toast.makeText(getApplicationContext(),

                    "onProviderEnabled", Toast.LENGTH_SHORT);
            toast.show();

        }

        @Override
        public void onProviderDisabled(String provider) {


            Log.e("MainActivity","onProviderDisabled");
            Toast toast = Toast.makeText(getApplicationContext(),

                    "onProviderDisabled", Toast.LENGTH_SHORT);
            toast.show();

        }
    };

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
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
            currentLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            currentLocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.e("TAGLOCATION","LOCATION");



        }


    }
    class TempAsyncTask extends AsyncTask<WeatherForecast,Integer,FactWeather> {
        @Override
        protected void onPostExecute(FactWeather factWeather) {
            Toast toast = Toast.makeText(MainActivity.this,

                    factWeather.toString(), Toast.LENGTH_LONG);
            toast.show();
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
             //   weather.setClouds(objectMapper,node);



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
