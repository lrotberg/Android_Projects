package com.example.exoli.myapplication.activities;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.exoli.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class HighScoresActivity extends AppCompatActivity implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;
    private Location lastKnownLocation;
    private LatLng home;

    @Override
    public void onMapLoaded() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        home = new LatLng(32.0298225,34.8005623);
        //default location

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_scores, menu);
        return true;
    }

}
