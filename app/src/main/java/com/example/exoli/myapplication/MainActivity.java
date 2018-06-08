package com.example.exoli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtAge;
    private TextView txtName;
    private TextView txtAge;
    private Button btnSubmit;
    private Button btnHighscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindUI();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().length() == 0)
                    edtName.setError("Please enter a name");
                else if(edtAge.getText().length() == 0)
                    edtAge.setError("Please enter your age");
                else
                    goToDifficultyActivity();
            }
        });

        btnHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goToDifficultyActivity() {
        Intent  intent = new Intent(this, DifficultyActivity.class);
        intent.putExtra(getString(R.string.intent_name) ,edtName.getText().toString());
        intent.putExtra(getString(R.string.intent_age) ,edtAge.getText().toString());
        startActivity(intent);
    }

    private void bindUI() {
        setContentView(R.layout.activity_main);
        edtName = (EditText)findViewById(R.id.edtName);
        edtAge = (EditText)findViewById(R.id.edtAge);
        txtName = (TextView)findViewById(R.id.txtName);
        txtAge = (TextView)findViewById(R.id.txtAge);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnHighscores = (Button) findViewById(R.id.btnHighscores);
    }
}
