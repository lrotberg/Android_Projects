package com.example.exoli.myapplication.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.exoli.myapplication.R;
import com.example.exoli.myapplication.res.DBController;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class HighScoresActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Location lastKnownLocation;
    private static LatLng home = new LatLng(32.0298225, 34.8005623);
    private boolean permissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment supportMapFragmen;
    private DBController dbController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        dbController = new DBController(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        supportMapFragmen = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frag_map);
        supportMapFragmen.getMapAsync(this);

    }

    public static LatLng getDefaultLocation() {
        return home;
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_high_scores, menu);
//        return true;
//    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (this.map != null) {
            try {
                if (permissionGranted) {
                    this.map.setMyLocationEnabled(true);
                    this.map.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    this.map.setMyLocationEnabled(false);
                    this.map.getUiSettings().setMyLocationButtonEnabled(false);
                    lastKnownLocation = null;
                }
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage());
            }
        }

        getLocation();

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
        Cursor scores = dbController.highestScores();

        while (scores.moveToNext()) {
            LatLng tempLocation = new LatLng(scores.getDouble(DBController.getColNumLatitude()),
                    scores.getDouble(DBController.getColNumLongitude()));
            map.addMarker(new MarkerOptions().position(tempLocation)
                    .title(scores.getString(DBController.getColNumName()))
                    .snippet("score: " + scores.getFloat(DBController.getColNumScore()) + getCompleteAddressString(tempLocation.latitude,tempLocation.longitude)));
        }
    }

    private String getCompleteAddressString(double latitude, double longitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    public void getLocation() {
        final int DEFAULT_ZOOM = 13;
        try {
            if (permissionGranted) {
                Task locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = (Location) task.getResult();
                            try {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } catch (NullPointerException e) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, DEFAULT_ZOOM));
                                map.getUiSettings().setMyLocationButtonEnabled(false);
                            }
                        } else {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
