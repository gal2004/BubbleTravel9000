package com.example.bubbletravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FindCityWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_city_weather);

        final EditText edtCityName = findViewById(R.id.edtCityName);

        Button btnGetWeather = findViewById(R.id.btnGetWeather);

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCityName = edtCityName.getText().toString();

                Intent intent = new Intent(FindCityWeatherActivity.this, WeatherActivity.class);
                intent.putExtra("city", newCityName);
                startActivity(intent);
                finish();
            }
        });

        Intent IntentCityName = getIntent();
        String cityName = IntentCityName.getStringExtra("cityKey");
        edtCityName.setText(cityName);
    }
}
