package com.example.exoli.myapplication;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class EasyModeActivity extends AppCompatActivity {

    private TextView txtTimeEasy;
    private TextView txtNameEasy;
    private final int EASY_TIME = 31;
    private final int ARR_SIZE = 8;
    private final int NUM_OF_COUPLES = 4;
    private CountDownTimer cdt;
    private ImageButton[] ib;
    private ArrayList<Integer> imageID;
    private ArrayList<Integer> flippedButtons = new ArrayList<Integer>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_mode);

        bindUI();

        cdt = new CountDownTimer(EASY_TIME*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtTimeEasy.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                txtTimeEasy.setText("Time is up!");
                finish();
            }
        };
        cdt.start();
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();
        txtTimeEasy = (TextView)findViewById(R.id.txtTime);
        txtNameEasy = (TextView)findViewById(R.id.txtName);
        txtNameEasy.setText((String)data.get("NAME"));

        imageID = new ArrayList<Integer>(8);
        setImages(imageID);
        Collections.shuffle(imageID);

        ib = new ImageButton[ARR_SIZE];
        setButtons(ib);
    }

    private void setImages(ArrayList<Integer> images) {
        for (int i = 0 ; i < NUM_OF_COUPLES ; i++){
            String temp = "btn_" + i;
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/"+temp, null, c.getPackageName());
            images.add(id);
        }

        for (int i = ARR_SIZE - NUM_OF_COUPLES ,  j = 0 ; i < ARR_SIZE ; i++, j++) {
            images.add(images.get(j));
        }
    }


    private void setButtons(final ImageButton[] buttons) {
        for (int i = 0 ; i < buttons.length ; i++){
            String temp = "ib_" + i;
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("id/"+temp, null, c.getPackageName());
            buttons[i] = new ImageButton(getApplicationContext());
            buttons[i] = (ImageButton)findViewById(id);
            buttons[i].setImageResource(R.color.white);
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                int counter = 0;
                @Override
                public void onClick(View v) {
                    buttons[finalI].setImageResource(imageID.get(finalI));
                    flippedButtons.add(finalI);
                    counter++;
                    if (counter == 2) {
                        if (!(buttons[flippedButtons.get(0)] == (buttons[flippedButtons.get(1)]))) {
                            buttons[flippedButtons.get(0)].setImageResource(R.color.white);
                            buttons[flippedButtons.get(1)].setImageResource(R.color.white);
                        }
                        else {
                            buttons[flippedButtons.get(0)].setClickable(false);
                            buttons[flippedButtons.get(1)].setClickable(false);
                        }
                        flippedButtons.clear();
                        counter = 0;
                    }
                }
            });
        }
    }

}
