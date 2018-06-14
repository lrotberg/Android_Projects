package com.example.exoli.myapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exoli.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtAge;
    private TextView txtName;
    private TextView txtAge;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindUI();

        getLocationPermission();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().length() == 0)
                    edtName.setError("Please enter a name");
                else if(edtAge.getText().length() == 0)
                    edtAge.setError("Please enter your age");
                else
                    goToDifficultyActivity();
            }
        });
   }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void goToDifficultyActivity() {
        Intent  intent = new Intent(MainActivity.this, DifficultyActivity.class);
        intent.putExtra(getString(R.string.intent_name) ,edtName.getText().toString());
        intent.putExtra(getString(R.string.intent_age) ,edtAge.getText().toString());
        startActivity(intent);
    }

    private void bindUI() {
        setContentView(R.layout.activity_main);
        edtName = (EditText)findViewById(R.id.edtName);
        edtAge = (EditText)findViewById(R.id.edtAge);
        txtName = (TextView)findViewById(R.id.txtName);
        txtAge = (TextView)findViewById(R.id.txtAge);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
    }
}
