package com.example.exoli.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EasyModeActivity extends AppCompatActivity {

    private TextView txtTimeEasy;
    private TextView txtNameEasy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_mode);

        bindUI();

        new CountDownTimer(30*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtTimeEasy.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                txtTimeEasy.setText("Time is up!");
                finish();
            }
        }.start();
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();
        txtTimeEasy = (TextView)findViewById(R.id.txtTimeEasy);
        txtNameEasy = (TextView)findViewById(R.id.txtNameEasy);
        txtNameEasy.setText((String)data.get("NAME"));
    }
}
