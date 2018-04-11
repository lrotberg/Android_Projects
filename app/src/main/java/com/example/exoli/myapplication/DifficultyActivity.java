package com.example.exoli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DifficultyActivity extends AppCompatActivity {

    private static String name;
    private static String age;
    private static String str;
    private final int EASY_ROWS = 2;
    private final int EASY_COLS = 4;
    private final int MEDIUM_ROWS_COLS = 4;
    private final int HARD_ROWS = 5;
    private final int HARD_COLS = 4;
    private final int EASY_TIME = 31;
    private final int MEDIUM_TIME = 46;
    private final int HARD_TIME = 61;
    private TextView txt;
    private Button btnEasy;
    private Button btnMedium;
    private Button btnHard;
    private Button.OnClickListener ocl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        bindUI();

        ocl = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.btnEasy:
                        goToEasyMode();
                        break;
                    case R.id.btnMedium:
                        goToMediumMode();
                        break;
                    case R.id.btnHard:
                        goToHardMode();
                        break;
                }
            }
        };

        btnEasy.setOnClickListener(ocl);
        btnMedium.setOnClickListener(ocl);
        btnHard.setOnClickListener(ocl);

    }

    private void goToEasyMode() {
        Intent  intent = new Intent(this, GameActivity.class);
        intent.putExtra("NAME" ,name);
        intent.putExtra("ROWS", EASY_ROWS);
        intent.putExtra("COLS", EASY_COLS);
        intent.putExtra("TIME", EASY_TIME);
        startActivity(intent);
    }

    private void goToMediumMode() {
        Intent  intent = new Intent(this, GameActivity.class);
        intent.putExtra("NAME" ,name);
        intent.putExtra("ROWS", MEDIUM_ROWS_COLS);
        intent.putExtra("COLS", MEDIUM_ROWS_COLS);
        intent.putExtra("TIME", MEDIUM_TIME);
        startActivity(intent);
    }

    private void goToHardMode() {
        Intent  intent = new Intent(this, GameActivity.class);
        intent.putExtra("NAME" ,name);
        intent.putExtra("ROWS", HARD_ROWS);
        intent.putExtra("COLS", HARD_COLS);
        intent.putExtra("TIME", HARD_TIME);
        startActivity(intent);
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();
        name = (String)data.get("NAME");
        age = (String)data.get("AGE");
        str = String.format("Hello %s(%s) please choose a difficulty",name,age);

        txt = (TextView)findViewById(R.id.txt1);
        txt.setText(str);

        btnEasy = (Button)findViewById(R.id.btnEasy);
        btnMedium = (Button)findViewById(R.id.btnMedium);
        btnHard = (Button)findViewById(R.id.btnHard);
    }
}
