package com.example.exoli.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    EditText edtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
    }

    private void bindUI() {
        edtName = (EditText)findViewById(R.id.edtName);
        edtAge = (EditText)findViewById(R.id.edtAge);
    }
}
