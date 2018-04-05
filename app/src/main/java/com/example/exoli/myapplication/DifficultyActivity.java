package com.example.exoli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DifficultyActivity extends AppCompatActivity {

    static String name;
    static String age;
    static String str;
    TextView txt;
    Button btnEasy;
    Button btnMedium;
    Button btnHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        bindUI();

        //TODO easy, medium, hard

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
