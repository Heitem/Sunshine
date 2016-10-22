package com.heitemol.sunshine;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        //myUpdateOperation();
                    }
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
