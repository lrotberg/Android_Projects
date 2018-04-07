package com.example.exoli.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HardModeActivity extends AppCompatActivity {

    private TextView txtTimeHard;
    private TextView txtNameHard;
    private final int HARD_TIME = 61;
    private CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_mode);

        bindUI();

        cdt = new CountDownTimer(HARD_TIME*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtTimeHard.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                txtTimeHard.setText("Time is up!");
                finish();
            }
        };
        cdt.start();
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();
        txtTimeHard = (TextView)findViewById(R.id.txtTimeHard);
        txtNameHard = (TextView)findViewById(R.id.txtNameHard);
        txtNameHard.setText((String)data.get("NAME"));
    }
}
