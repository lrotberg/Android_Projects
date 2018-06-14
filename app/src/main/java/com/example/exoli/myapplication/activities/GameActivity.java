package com.example.exoli.myapplication.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exoli.myapplication.res.Card;
import com.example.exoli.myapplication.R;
import com.example.exoli.myapplication.res.DBController;
import com.example.exoli.myapplication.res.SensorService;

import java.util.ArrayList;
import java.util.Collections;

import tyrantgit.explosionfield.ExplosionField;


public class GameActivity extends AppCompatActivity implements SensorService.SensorServiceListener {

    final String TAG = "GameBoardActivity";
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
    private DBController dbController;
    private Location lastKnownLocation = null;
    private float[] startValues;
    private static boolean gameOver;
    private static final int RADIUS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(this ,SensorService.class), serviceConnection, Context.BIND_AUTO_CREATE);

        dbController = new DBController(this);

        gameOver = false;

        bindUI();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof SensorService.SensorServiceBinder) {
                SensorService.SensorServiceBinder sensorServiceBinder = (SensorService.SensorServiceBinder) iBinder;
                sensorServiceBinder.setListener(GameActivity.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unbindService(serviceConnection);
        }
    };

    @Override
    public void onSensorChanged(float[] values) {

        if(startValues == null) {
            startValues = new float[3];
            startValues[0] = values[0];
            startValues[1] = values[1];
            startValues[2] = values[2];
        }
        if(gameOver)
            return;

        float x = Math.abs(values[0]);
        float y = Math.abs(values[1]);
        float z = Math.abs(values[2]);
        float sx = Math.abs(startValues[0]);
        float sy = Math.abs(startValues[1]);
        float sz = Math.abs(startValues[2]);

        if(x > sx + RADIUS || y > sy + RADIUS || z > sz + RADIUS)
        {
            addCardsToGame();
        }
    }

    private void addCardsToGame(){
        boolean cardsAdded = false;
        Card card = null;
        int counter = 0;
        for(int i = 0; i < cardsArrayList.size() && counter < 2 ; i++) {
            if(cardsArrayList.get(i).isShowen() && counter == 0){
                card = cardsArrayList.get(i);
                card.setEnabled(true);
                card.setClickable(true);
                card.setShowen(false);
                card.flip();
                counter++;
                cardsAdded = true;
            }
            else if(counter > 0 && cardsArrayList.get(i).isShowen()){
                if(cardsArrayList.get(i).equals(card)){
                    cardsArrayList.get(i).setEnabled(true);
                    cardsArrayList.get(i).setClickable(true);
                    cardsArrayList.get(i).setShowen(false);
                    cardsArrayList.get(i).flip();
                    counter++;
                    //cardsAdded = true;
                }
            }
        }
        if(cardsAdded)
            Toast.makeText(this, R.string.card_return, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
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
                ExplosionField explosionField = ExplosionField.attach2Window(GameActivity.this);
                explosionField.explode(gridLayout);
                Toast.makeText(GameActivity.this, R.string.game_lose, Toast.LENGTH_SHORT).show();
                enableCards(false);

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
        winAnimation();

        txtScore.setText(String.format("%s %.1f", getString(R.string.score_txt), score));

        getLocation();

        dbController.addScore(name, score, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

        Toast.makeText(GameActivity.this, R.string.game_win, Toast.LENGTH_SHORT).show();
        enableCards(false);

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, FINISH_DELAY);
    }

    private void winAnimation() {
        for (int i = 0 ; i < cardsArrayList.size() ; i++) {
            cardsArrayList.get(i).animate().rotation(1800).setDuration(2000);
        }
    }

//    private int getDiff() {
//        switch (numOfCouples) {
//            case 4:
//                return 1;
//            case 8:
//                return 2;
//            case 12:
//                return 3;
//            default:
//                return -1;
//        }
//    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"No GPS - Turn on");
        }
        try {
            lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            lastKnownLocation.getLongitude();
        } catch (Exception e) {
            lastKnownLocation = new Location("default location");
            lastKnownLocation.setLatitude(32.113819);
            lastKnownLocation.setLatitude(34.817794);
        }
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
