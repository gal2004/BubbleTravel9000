package com.example.bubbletravel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherActivity extends AppCompatActivity {

    TextView txtLocation, txtTemperature;
    ImageView weatherIcon;

    final int REQUEST_CODE = 1234;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

    final String API_KEY = "2013cbb8d05f985325a7945757d64373";

    final long MIN_TIME = 5000;

    final float MIN_DISTANCE = 1000;

    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    // Create Instance of LocationManager and LocationListener
    LocationManager mLocationManager;
    LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        txtTemperature = findViewById(R.id.txtTemperature);
        weatherIcon = findViewById(R.id.weatherIcon);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String cityName = intent.getStringExtra("city");

        if (cityName != null) {

            getNewCityWeather(cityName);

        } else {
            getCurrentLocationWeather();
        }
    }

    private void getNewCityWeather(String cityName) {

        RequestParams requestParams = new RequestParams();

        requestParams.put("q", cityName);
        requestParams.put("appid", API_KEY);

        apiCall(requestParams);
    }

    private void getCurrentLocationWeather() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                RequestParams requestParams = new RequestParams();

                requestParams.put("lat", latitude);
                requestParams.put("lon", longitude);
                requestParams.put("appid", API_KEY);

                apiCall(requestParams);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

            return;
        }

        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationWeather();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void apiCall(RequestParams requestParams) {
        if (isConnected()) {

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

            asyncHttpClient.get(WEATHER_URL, requestParams, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    Weather weather = Weather.fromJson(response);
                    updateWeatherDetails(weather);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);

                    Toast.makeText(WeatherActivity.this, "Error occurred while making request!", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(this, "No internet connection! Try to connect to a working internet and try again", Toast.LENGTH_LONG).show();
        }
    }

    private void updateWeatherDetails(Weather weather) {

        txtTemperature.setText(weather.getTemperature());
        int resourceIdentifier = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
        weatherIcon.setImageResource(resourceIdentifier);
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_change_city:
                Intent intent = new Intent(this, FindCityWeatherActivity.class);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}