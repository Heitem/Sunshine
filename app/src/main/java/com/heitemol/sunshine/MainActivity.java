package com.heitemol.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements ForecastFragment.OnFragmentInteractionListener {

    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srl = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        callForData();

        //Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user performs a swipe-to-refresh gesture.
        srl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        callForData();
                    }
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings_menu:
                // Go to SettingsActivity by skipping header screen
                Intent intent = new Intent( this, SettingsActivity.class );
                intent.putExtra( SettingsActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment.class.getName() );
                intent.putExtra( SettingsActivity.EXTRA_NO_HEADERS, true );
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Call for data
    public void callForData(){

        Log.i("SwipeToRefresh", "onRefresh called from SwipeRefreshLayout");

        // Restore preferences
        String city = "Salé";
        String unit = "metric";
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPref.contains("location") && sharedPref.contains("units")) {
            city = sharedPref.getString("location", "");
            unit = sharedPref.getString("units", "");
        }

        OpenWeatherMap.get("&q=" + city + "&cnt=7&units=" + unit, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    ForecastFragment.adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, getStringFromWeatherDayArray(getWeatherDataFromJson(response)));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ForecastFragment.lv.setAdapter(ForecastFragment.adapter);
                srl.setRefreshing(false);

                ForecastFragment.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Start DetailActivity with the Extra by clicking on an item
                        startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("data", ForecastFragment.adapter.getItem(i)));
                    }
                });
            }
        });
    }

    //Parse weather data from JSON
    public List<WeatherDay> getWeatherDataFromJson(JSONObject forecastJson) throws JSONException, ParseException {

        JSONArray days = forecastJson.getJSONArray("list");
        JSONObject city = forecastJson.getJSONObject("city");
        JSONObject coord = city.getJSONObject("coord");
        List<WeatherDay> wdList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        for(int i = 0; i < days.length(); i++){
            JSONObject dayInfo = days.getJSONObject(i);
            JSONObject temp = dayInfo.getJSONObject("temp");
            JSONObject weather = dayInfo.getJSONArray("weather").getJSONObject(0);

            //if JSON has the string "rain"
            Double rain = 0d;
            if(dayInfo.has("rain")){
                rain = dayInfo.getDouble("rain");
            }

            //Fill the object with the data
            WeatherDay wd = new WeatherDay(city.getString("name"), coord.getDouble("lon"), coord.getDouble("lat"),
                    city.getString("country"), forecastJson.getInt("cnt"), dayInfo.getInt("dt"), temp.getDouble("day"),
                    temp.getDouble("min"), temp.getDouble("max"), temp.getDouble("night"), temp.getDouble("eve"), temp.getDouble("morn"),
                    dayInfo.getDouble("pressure"), dayInfo.getInt("humidity"), weather.getInt("id"), weather.getString("main"),
                    weather.getString("description"), weather.getString("icon"), dayInfo.getDouble("speed"), dayInfo.getInt("deg"),
                    dayInfo.getInt("clouds"), rain, date, getReadableDateString(date.getTime()));

            Log.d("Object", wd.toString());

            //Add one day to the current date
            cal.add(Calendar.DATE, 1);
            date = cal.getTime();
            wdList.add(wd);
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

    //Convert date from milliseconds to readable date
    public String getReadableDateString(long time){

        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE, MMM dd");
        return shortenedDateFormat.format(time);
    }

    public List<String> getStringFromWeatherDayArray(List<WeatherDay> weatherDays){
        List<String> wd = new ArrayList<>();
        for (int i = 0; i < weatherDays.size(); i++){
            wd.add(weatherDays.get(i).getDateStr() + " - " + CapsFirst(weatherDays.get(i).getWeather_description())
                    + " - " + Math.round(weatherDays.get(i).getTemp_max()) + "/" + Math.round(weatherDays.get(i).getTemp_min()));
        }
        return wd;
    }

    public void openPreferredLocationInMap(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPref.getString("location", "");
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else Log.d("Error", "Error on openPreferredLocationInMap");
    }
}
