package com.example.bubbletravel;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    private String temperature;
    private String city;
    private String iconName;

    private int condition;

    public static Weather fromJson(JSONObject jsonObject) {

        try {

            Weather weather = new Weather();

            weather.city = jsonObject.getString("name");
            weather.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weather.iconName = determineWeatherIcon(weather.condition);


            double temperatureInKelvin = jsonObject.getJSONObject("main").getDouble("temp");
            double temperatureInCelsius = temperatureInKelvin - 273.15;

            int approximateTemperature = (int) Math.rint(temperatureInCelsius);
            weather.temperature = Integer.toString(approximateTemperature);

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String determineWeatherIcon(int condition) {

        if (condition >= 200 && condition <= 233) {
            return "thunderstorm";
        } else if (condition >= 300 && condition <= 321) {
            return "drizzle";
        } else if (condition >= 500 && condition <= 531) {
            return "rain";
        } else if (condition >= 600 && condition <= 622) {
            return "snow";
        } else if (condition >= 701 && condition <= 781) {
            return "mist";
        } else if (condition == 800) {
            return "clear_sky";
        } else if (condition >= 801 && condition <= 804) {
            return "clouds";
        }

        return "unknown";

    }

    public String getTemperature() {
        return temperature + "°C";
    }

    public String getCity() {
        return city;
    }

    public String getIconName() {
        return iconName;
    }
}