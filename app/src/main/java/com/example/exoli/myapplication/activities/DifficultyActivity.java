package com.example.exoli.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.exoli.myapplication.R;

public class DifficultyActivity extends AppCompatActivity {

    private static String name;
    private static String age;
    private static String str;
    private final int EASY_ROWS = 2;
    private final int EASY_COLS = 4;
    private final int MEDIUM_ROWS_COLS = 4;
    private final int HARD_ROWS = 6;
    private final int HARD_COLS = 4;
    private final int EASY_TIME = 31;
    private final int MEDIUM_TIME = 46;
    private final int HARD_TIME = 61;
    private TextView txt;
    private Button btnEasy;
    private Button btnMedium;
    private Button btnHard;
    private Button.OnClickListener ocl;
    private Button btnHighscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindUI();

        ocl = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.btnEasy:
                        goToGameActivity(name, EASY_ROWS, EASY_COLS, EASY_TIME);
                        break;
                    case R.id.btnMedium:
                        goToGameActivity(name, MEDIUM_ROWS_COLS, MEDIUM_ROWS_COLS, MEDIUM_TIME);
                        break;
                    case R.id.btnHard:
                        goToGameActivity(name, HARD_ROWS, HARD_COLS, HARD_TIME);
                        break;
                }
            }
        };

        btnHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyActivity.this, HighScoresActivity.class);
                startActivity(intent);
            }
        });

        btnEasy.setOnClickListener(ocl);
        btnMedium.setOnClickListener(ocl);
        btnHard.setOnClickListener(ocl);

    }

    private void goToGameActivity(String name, int rows, int cols, int time) {
        Intent  intent = new Intent(DifficultyActivity.this, GameActivity.class);
        intent.putExtra(getString(R.string.intent_name) ,name);
        intent.putExtra(getString(R.string.intent_rows), rows);
        intent.putExtra(getString(R.string.intent_cols), cols);
        intent.putExtra(getString(R.string.intent_time), time);
        startActivity(intent);
    }

    private void bindUI() {
        setContentView(R.layout.activity_difficulty);
        Bundle data = getIntent().getExtras();
        name = (String)data.get(getString(R.string.intent_name));
        age = (String)data.get(getString(R.string.intent_age));
        str = String.format("Hello %s(%s) please choose a difficulty", name, age);

        txt = (TextView)findViewById(R.id.txt1);
        txt.setText(str);

        btnEasy = (Button)findViewById(R.id.btnEasy);
        btnMedium = (Button)findViewById(R.id.btnMedium);
        btnHard = (Button)findViewById(R.id.btnHard);
        btnHighscores = (Button) findViewById(R.id.btnHighscores);
    }
}
