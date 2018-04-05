package com.example.exoli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    EditText edtAge;
    TextView txtName;
    TextView txtAge;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    private void goToDifficultyActivity() {
        Intent  intent = new Intent(this, DifficultyActivity.class);
        intent.putExtra("NAME" ,edtName.getText().toString());
        intent.putExtra("AGE" ,edtAge.getText().toString());
        startActivity(intent);
    }

    private void bindUI() {
        edtName = (EditText)findViewById(R.id.edtName);
        edtAge = (EditText)findViewById(R.id.edtAge);
        txtName = (TextView)findViewById(R.id.txtName);
        txtAge = (TextView)findViewById(R.id.txtAge);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
    }
}
