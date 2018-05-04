package com.example.user.myapp;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static String WEATHER_FORECAST_FORMAT;
    static String LAT_LON_FORMAT;
    static String GEO_FORMAT;
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
        tempBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
