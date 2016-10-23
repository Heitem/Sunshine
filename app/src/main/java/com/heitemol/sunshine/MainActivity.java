package com.heitemol.sunshine;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements ForecastFragment.OnFragmentInteractionListener {

    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srl = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        //Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user performs a swipe-to-refresh gesture.
        srl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("SwipeToRefresh", "onRefresh called from SwipeRefreshLayout");

                        OpenWeatherMap.get("&q=sale&cnt=7&units=metric", null, new JsonHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);

                                //ForecastFragment.adapter.clear();
                                try {
                                    //ForecastFragment.adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, getWeatherDataFromJson(response));
                                    getWeatherDataFromJson(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                ForecastFragment.adapter.notifyDataSetChanged();
                                srl.setRefreshing(false);
                            }
                        });
                    }
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Parse weather data from JSON
    public WeatherDay[] getWeatherDataFromJson(JSONObject forecastJson) throws JSONException, ParseException {

        JSONArray days = forecastJson.getJSONArray("list");
        JSONObject city = forecastJson.getJSONObject("city");
        JSONObject coord = city.getJSONObject("coord");
        WeatherDay[] wdList = new WeatherDay[days.length()];
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        for(int i = 0; i < days.length(); i++){
            JSONObject dayInfo = days.getJSONObject(i);
            JSONObject temp = dayInfo.getJSONObject("temp");
            JSONObject weather = dayInfo.getJSONArray("weather").getJSONObject(0);
            WeatherDay wd = new WeatherDay(city.getString("name"), coord.getDouble("long"), coord.getDouble("lat"),
                    city.getString("country"), forecastJson.getInt("cnt"), dayInfo.getLong("dt"), temp.getDouble("day"),
                    temp.getDouble("min"), temp.getDouble("max"), temp.getDouble("night"), temp.getDouble("eve"), temp.getDouble("morn"),
                    dayInfo.getDouble("pressure"), dayInfo.getInt("humidity"), weather.getInt("id"), weather.getString("main"),
                    weather.getString("description"), weather.getString("icon"), dayInfo.getDouble("speed"), dayInfo.getInt("deg"),
                    dayInfo.getInt("clouds"), dayInfo.getDouble("rain"), date);



            Log.d("DATE", wd.getDate().toString());
            /*String datetime = dayInfo.getString("dt_txt");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = sdf.getCalendar();
            String date = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) + ", " + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + cal.get(Calendar.DAY_OF_MONTH);

            wd[i] = date + " - " + CapsFirst(description) + " - " + Math.round(temp_max) + "/" + Math.round(temp_min);
            Log.d("DATE", date + " - " + CapsFirst(description) + " - " + Math.round(temp_max) + "/" + Math.round(temp_min));*/
        }
        return wdList;
    }

    //Uppercase the first letter of every word
    public String CapsFirst(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if(i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }
}
