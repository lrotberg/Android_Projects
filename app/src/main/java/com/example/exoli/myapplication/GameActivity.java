package com.example.exoli.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

public class GameActivity extends AppCompatActivity {

    private String name;
    private GridLayout gl;
//    private int rows;
//    private int cols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle data = getIntent().getExtras();
        name = data.getString("NAME");
        gl.setRowCount(data.getInt("ROWS"));
        gl.setColumnCount(data.getInt("COLS"));
    }
}
