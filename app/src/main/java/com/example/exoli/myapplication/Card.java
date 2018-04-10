package com.example.exoli.myapplication;

import android.content.Context;
import android.util.AttributeSet;

public class Card extends android.support.v7.widget.AppCompatImageButton {

    private boolean isFlipped = false;

    public Card(Context context) {
        super(context);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void flip() {
        if(this.isFlipped() == true)
            this.isFlipped = false;
        else
            this.isFlipped = true;
    }

    public boolean isFlipped() {
        return isFlipped;
    }
}
