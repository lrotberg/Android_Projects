package com.example.exoli.myapplication;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class GameActivity extends AppCompatActivity {

    private String name;
    private GridLayout layout;
    private int[] images;
    private int numOfCouples;
    private ArrayList<Card> cards;
    private TextView txtTime;
    private TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bindUI();
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();

        setLayout((GridLayout)findViewById(R.id.glGame));

        setName(data.getString("NAME"));
        layout.setRowCount(data.getInt("ROWS"));
        layout.setColumnCount(data.getInt("COLS"));

        txtName = (TextView)findViewById(R.id.txtNameGame);
        txtName.setText(data.getString("NAME"));
        txtTime = (TextView)findViewById(R.id.txtTimeGame);

        CountDownTimer gameTime = new CountDownTimer(data.getInt("TIME")*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                finish();
                Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
            }


        };
        gameTime.start();

        numOfCouples = ((layout.getColumnCount() * layout.getRowCount()) / 2);

        images = new int[numOfCouples * 2];
        setImages(images, numOfCouples);

        cards = new ArrayList<Card>();
        setCards(cards, numOfCouples * 2);
    }

    private void setCards(ArrayList<Card> cards, int numOfCards) {
        for (int i = 0 ; i < numOfCards ; i++) {
            final Card tempCard = new Card(this);
            tempCard.setImageID(getImages()[i]);

            tempCard.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tempCard.flip();
                }
            });

            cards.add(tempCard);
        }
        Collections.shuffle(cards);

        for(Card c : cards) {
            layout.addView(c);
        }
    }

    public int[] getImages() {
        return images;
    }



    private void setImages(int[] images, int numOfCouples) {
        for (int i = 0, j = ((this.numOfCouples * 2) - numOfCouples); i < numOfCouples; i++, j++){
            String temp = "btnimg_" + i;
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/"+temp, null, c.getPackageName());
            images[i] = id;
            images[j] = id;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLayout(GridLayout layout) {
        this.layout = layout;
    }
}
