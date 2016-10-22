package com.heitemol.sunshine;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

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

                        OpenWeatherMap.get("&q=rabat&cnt=7&units=metric", null, new JsonHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);

                                try {
                                    ForecastFragment.getWeatherDataFromJson(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
