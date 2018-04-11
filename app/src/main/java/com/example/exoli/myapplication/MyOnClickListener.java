package com.example.exoli.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyOnClickListener implements ImageButton.OnClickListener {

    private Card card;

    public MyOnClickListener(Card card) {
        this.card = card;
    }

    @Override
    public void onClick(View v) {

    }


}
