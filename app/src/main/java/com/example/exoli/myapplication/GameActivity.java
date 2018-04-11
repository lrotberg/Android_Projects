package com.example.exoli.myapplication;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
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
    private ArrayList<Card> cardsArrayList;
    private TextView txtTime;
    private TextView txtName;
    private Card card1;
    private Card card2;
    private int counter = 0;
    private CountDownTimer gameTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bindUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameTime.cancel();
    }

    private void bindUI() {
        Bundle data = getIntent().getExtras();

        setLayout((GridLayout)findViewById(R.id.glGame));

        setName(data.getString("NAME"));
        layout.setRowCount(data.getInt("ROWS"));
        layout.setColumnCount(data.getInt("COLS"));

        txtName = (TextView)findViewById(R.id.txtNameGame);
        txtName.setText(getName());
        txtTime = (TextView)findViewById(R.id.txtTimeGame);

        gameTime = new CountDownTimer(data.getInt("TIME")*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                finish();
                Toast.makeText(getApplicationContext(), R.string.game_lose, Toast.LENGTH_LONG).show();
            }


        };
        gameTime.start();

        numOfCouples = ((layout.getColumnCount() * layout.getRowCount()) / 2);

        images = new int[numOfCouples * 2];
        setImages(images, numOfCouples);

        cardsArrayList = new ArrayList<Card>();
        setCards(cardsArrayList, numOfCouples * 2);
    }

    private void setCards(ArrayList<Card> cards, int numOfCards) {
        for (int i = 0 ; i < numOfCards ; i++) {
            final Card tempCard = new Card(this);
            tempCard.setImageID(getImages()[i]);

            tempCard.setOnClickListener(new CardOnClickListener(tempCard));

            cards.add(tempCard);
        }
        Collections.shuffle(cards);

        for(Card c : cards) {
            layout.addView(c);
        }
    }

    public String getName() {
        return name;
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

    protected void raiseCounter() {
        this.counter++;
    }


    class CardOnClickListener implements ImageButton.OnClickListener {

        private Card card;

        public CardOnClickListener(Card card) {
            this.card = card;
        }

        @Override
        public void onClick(View v) {
            if(!card.isFlipped()) {
                card.flip();
                raiseCounter();
            }
            checkMatched(card);
        }
    }

    public int getCounter() {
        return counter;
    }

    private void checkMatched(Card card) {
        if(getCounter() % 2 != 0)
            card1 = card;
        else
            card2 = card;

        if(card1 != null && card2 != null) {
            if (card1.equals(card2)) {
                card1.setShowen();
                card1 = null;
                card2.setShowen();
                card2 = null;
                if(checkIfGameOver()) {
                    finish();
                    Toast.makeText(getApplicationContext(), R.string.game_win, Toast.LENGTH_LONG).show();
                }
            }
            else {
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        card1.flip();
                        card1 = null;
                        card2.flip();
                        card2 = null;
                    }
                };
                handler.postDelayed(runnable, 1 * 1000);
            }
        }
    }

    private boolean checkIfGameOver() {
        for(Card card : cardsArrayList) {
            if(!card.isShowen())
                return false;
        }
        return true;
    }
}
