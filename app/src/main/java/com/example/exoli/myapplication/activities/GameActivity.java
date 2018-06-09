package com.example.exoli.myapplication.activities;

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

import com.example.exoli.myapplication.Card;
import com.example.exoli.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;

import tyrantgit.explosionfield.ExplosionField;


public class GameActivity extends AppCompatActivity {

    private static final int FLIP_TIME = 300;
    private static final int FINISH_DELAY = 2000;
    private static final float RAISE_SCORE = 1.1f;
    private static final float LOWER_SCORE = 0.1f;
    private String name;
    private GridLayout gridLayout;
    private int[] images;
    private int numOfCouples;
    private ArrayList<Card> cardsArrayList;
    private TextView txtTime;
    private TextView txtName;
    private TextView txtScore;
    private float score = 0f;
    private Card card1;
    private Card card2;
    private int counter = 0;
    private Handler handler = new Handler();
    private CountDownTimer gameTime;
    private long timeRemain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameTime.cancel();
    }

    private void bindUI() {
        setContentView(R.layout.activity_game);
        gridLayout = (GridLayout)findViewById(R.id.glGame);

        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText(String.format("%s %.1f", getString(R.string.score_txt), score));

        Bundle data = getIntent().getExtras();
        name = data.getString("NAME");
        gridLayout.setRowCount(data.getInt("ROWS"));
        gridLayout.setColumnCount(data.getInt("COLS"));

        txtName = (TextView)findViewById(R.id.txtNameGame);
        txtName.setText(name);
        txtTime = (TextView)findViewById(R.id.txtTimeGame);

        gameTime = new CountDownTimer(data.getInt("TIME")*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemain = millisUntilFinished / 1000;
                txtTime.setText(String.format("%s%d", getString(R.string.remaining_time), timeRemain));
            }

            @Override
            public void onFinish() {
                Toast.makeText(GameActivity.this, R.string.game_lose, Toast.LENGTH_SHORT).show();
                enableCards(false);
                ExplosionField explosionField = ExplosionField.attach2Window(GameActivity.this);
                explosionField.explode(gridLayout);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, FINISH_DELAY);
            }
        }.start();

        numOfCouples = ((gridLayout.getColumnCount() * gridLayout.getRowCount()) / 2);

        images = new int[numOfCouples * 2];
        setImages(images, numOfCouples);

        cardsArrayList = new ArrayList<Card>();
        setCards(cardsArrayList, numOfCouples * 2);
    }

    private void setCards(ArrayList<Card> cards, int numOfCards) {
        for (int i = 0 ; i < numOfCards ; i++) {
            final Card tempCard = new Card(this, images[i]);
            tempCard.setOnClickListener(new CardOnClickListener(tempCard));
            cards.add(tempCard);
        }
        Collections.shuffle(cards);

        for(Card c : cards) {
            gridLayout.addView(c);
        }
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

    class CardOnClickListener implements ImageButton.OnClickListener {

        private Card card;

        public CardOnClickListener(Card card) {
            this.card = card;
        }

        @Override
        public void onClick(View v) {
            if (!card.isFlipped()) {
                counter++;
                card.flip();
            }

            if (counter % 2 != 0)
                card1 = card;
            else
                card2 = card;

            checkMatched();
        }
    }

    private void enableCards(boolean enable) {
        for (int i = 0; i < cardsArrayList.size(); i++) {
            if (!cardsArrayList.get(i).isShowen()) {
                cardsArrayList.get(i).setEnabled(enable);
                cardsArrayList.get(i).setClickable(enable);
            }
        }
    }

    private void checkGameOver() {
        for (int i = 0; i < cardsArrayList.size(); i++) {
            if (!cardsArrayList.get(i).isShowen())
                return;
        }
        score = score + timeRemain;
        txtScore.setText(String.format("%s %.1f", getString(R.string.score_txt), score));
        Toast.makeText(GameActivity.this, R.string.game_win, Toast.LENGTH_SHORT).show();
        enableCards(false);
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, FINISH_DELAY);
    }

    private void checkMatched() {
        if (card1 != null && card2 != null) {
            enableCards(false);

            if (card1.isFlipped() && card2.isFlipped()) {
                if (card1.equals(card2)) {
                    card1.setShowen(true);
                    card2.setShowen(true);
                    card1.setEnabled(false);
                    card2.setEnabled(false);
                    card1.setClickable(false);
                    card2.setClickable(false);
                    score += RAISE_SCORE;
                    txtScore.setText(String.format("%s %.1f", getString(R.string.score_txt), score));
                    card1 = null;
                    card2 = null;
                    checkGameOver();
                    enableCards(true);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            card1.flip();
                            card2.flip();
                            if(score > LOWER_SCORE) {
                                score -= LOWER_SCORE;
                                txtScore.setText(String.format("%s %.1f", getString(R.string.score_txt), score));
                            }
                            card1 = null;
                            card2 = null;
                            enableCards(true);
                        }
                    }, FLIP_TIME);
                }
            }
        }
    }
}
