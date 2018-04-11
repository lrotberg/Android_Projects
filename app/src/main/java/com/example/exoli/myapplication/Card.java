package com.example.exoli.myapplication;

import android.content.Context;
import android.util.AttributeSet;

public class Card extends android.support.v7.widget.AppCompatImageButton {

    private boolean isFlipped = false;
    private int imageID;

    public Card(Context context) {
        super(context);
        this.setImageResource(R.drawable.background_grey);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void flip() {
        if(!this.isFlipped()) {
            this.setImageResource(imageID);
            this.setFlipped(true);
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


}
