package com.heitemol.sunshine;

import java.util.Date;

/**
 * Created by user on 23/10/2016.
 */

public class WeatherDay {

    private String name;
    private double coord_long;
    private double coord_lat;
    private String country;
    private int count;
    private int dt;
    private double temp_day;
    private double temp_min;
    private double temp_max;
    private double temp_night;
    private double temp_eve;
    private double temp_morn;
    private double pressure;
    private int humidity;
    private int weather_id;
    private String weather_main;
    private String weather_description;
    private String weather_icon;
    private double speed;
    private int deg;
    private int clouds;
    private double rain;
    private Date date;
    private String dateStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCoord_long() {
        return coord_long;
    }

    public void setCoord_long(double coord_long) {
        this.coord_long = coord_long;
    }

    public double getCoord_lat() {
        return coord_lat;
    }

    public void setCoord_lat(double coord_lat) {
        this.coord_lat = coord_lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public double getTemp_day() {
        return temp_day;
    }

    public void setTemp_day(double temp_day) {
        this.temp_day = temp_day;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_night() {
        return temp_night;
    }

    public void setTemp_night(double temp_night) {
        this.temp_night = temp_night;
    }

    public double getTemp_eve() {
        return temp_eve;
    }

    public void setTemp_eve(double temp_eve) {
        this.temp_eve = temp_eve;
    }

    public double getTemp_morn() {
        return temp_morn;
    }

    public void setTemp_morn(double temp_morn) {
        this.temp_morn = temp_morn;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(int weather_id) {
        this.weather_id = weather_id;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public WeatherDay(String name, double coord_long, double coord_lat, String country, int count, int dt, double temp_day, double temp_min, double temp_max, double temp_night, double temp_eve, double temp_morn, double pressure, int humidity, int weather_id, String weather_main, String weather_description, String weather_icon, double speed, int deg, int clouds, double rain, Date date, String dateStr) {
        this.name = name;
        this.coord_long = coord_long;
        this.coord_lat = coord_lat;
        this.country = country;
        this.count = count;
        this.dt = dt;
        this.temp_day = temp_day;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.temp_night = temp_night;
        this.temp_eve = temp_eve;
        this.temp_morn = temp_morn;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather_id = weather_id;
        this.weather_main = weather_main;
        this.weather_description = weather_description;
        this.weather_icon = weather_icon;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
        this.date = date;
        this.dateStr = dateStr;
    }

    @Override
    public String toString() {
        return "WeatherDay{" +
                "name='" + name + '\'' +
                ", coord_long=" + coord_long +
                ", coord_lat=" + coord_lat +
                ", country='" + country + '\'' +
                ", count=" + count +
                ", dt=" + dt +
                ", temp_day=" + temp_day +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", temp_night=" + temp_night +
                ", temp_eve=" + temp_eve +
                ", temp_morn=" + temp_morn +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weather_id=" + weather_id +
                ", weather_main='" + weather_main + '\'' +
                ", weather_description='" + weather_description + '\'' +
                ", weather_icon='" + weather_icon + '\'' +
                ", speed=" + speed +
                ", deg=" + deg +
                ", clouds=" + clouds +
                ", rain=" + rain +
                ", date=" + date +
                '}';
    }
}
