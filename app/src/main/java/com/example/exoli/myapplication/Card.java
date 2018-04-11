package com.example.exoli.myapplication;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Objects;

public class Card extends android.support.v7.widget.AppCompatImageButton {

    private boolean isFlipped = false;
    private int imageID;
    private boolean isShowen = false;

    public Card(Context context) {
        super(context);
        this.setImageResource(R.drawable.background_grey);
    }

    public void flip() {
        if(!this.isFlipped()) {
            this.setImageResource(imageID);
            this.setFlipped(true);
        }
        else {
            this.setImageResource(R.drawable.background_grey);
            this.setFlipped(false);
        }
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    private void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isShowen() {
        return isShowen;
    }

    public void setShowen() {
        isShowen = true;
        this.setEnabled(false);
        this.setClickable(false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Card card = (Card) obj;
        return imageID == card.imageID;
    }
}
