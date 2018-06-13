package com.example.exoli.myapplication.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.exoli.myapplication.R;
import com.example.exoli.myapplication.res.DBControl;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.SupportMapFragment;

public class HighScoresActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Location lastKnownLocation;
    private LatLng home;
    private DBControl dbControl;
    private boolean permissionGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment supportMapFragmen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        home = new LatLng(32.0298225, 34.8005623);
        //default location

        dbControl = new DBControl(HighScoresActivity.this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HighScoresActivity.this);
        supportMapFragmen = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frag_map);
        supportMapFragmen.getMapAsync(HighScoresActivity.this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_scores, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (map != null) {
            try {
                if (permissionGranted) {
                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    map.setMyLocationEnabled(false);
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    lastKnownLocation = null;
                }
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage());
            }
        }

        try {

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }

        fillScores();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
            }
        }
    }

    private void fillScores() {
        Cursor scores = dbControl.highestScores();

        while (scores.moveToNext()) {
            LatLng tempLocation = new LatLng(scores.getFloat(DBControl.getColNumLatitude()),
                    scores.getFloat(DBControl.getColNumLongitude()));
            map.addMarker(new MarkerOptions().position(tempLocation)
                    .title(scores.getString(DBControl.getColNumName()))
                    .snippet("score: " + scores.getInt(DBControl.getColNumDiff())));
        }
    }
}
