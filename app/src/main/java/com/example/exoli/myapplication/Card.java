package com.example.exoli.myapplication;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Objects;

public class Card extends android.support.v7.widget.AppCompatImageButton {

    private boolean isFlipped = false;
    private int imageID;
    private boolean isShowen = false;

    public Card(Context context, int imageID) {
        super(context);
        this.imageID =imageID;
        super.setImageResource(R.drawable.background_grey);
    }

    public void flip() {
        setFlipped(!isFlipped);
        if(!this.isFlipped()) {
            this.setImageResource(R.drawable.background_grey);
        }
        else {
            this.setImageResource(imageID);
        }
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    private void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }

    public boolean isShowen() {
        return isShowen;
    }

    public void setShowen(boolean isShowen) {
        this.isShowen = true;
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
