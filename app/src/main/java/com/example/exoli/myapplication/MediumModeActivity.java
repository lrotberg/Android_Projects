package com.example.exoli.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MediumModeActivity extends AppCompatActivity {

    private TextView txtTimeMedium;
    private TextView txtNameMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_mode);

        bindUI();

        new CountDownTimer(45*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtTimeMedium.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                txtTimeMedium.setText("Time is up!");
                finish();
            }
        }.start();
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();
        txtTimeMedium = (TextView)findViewById(R.id.txtTimeMedium);
        txtNameMedium = (TextView)findViewById(R.id.txtNameMedium);
        txtNameMedium.setText((String)data.get("NAME"));
    }
}
