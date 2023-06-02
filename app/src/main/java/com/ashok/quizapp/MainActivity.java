package com.ashok.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName;
    Button startButton;
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.nameEditText);
        startButton = findViewById(R.id.startButton);

        sharedPrefs = getSharedPreferences("ashok", Context.MODE_PRIVATE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                if (name.isEmpty() || name.equals(null)) {
                    Toast.makeText(view.getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Store the name in SharedPreferences
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("name", name);
                editor.apply();
                // Launch the QuestionActivity
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String nameInt = intent.getStringExtra("name") != null ? intent.getStringExtra("name") : "";
        if (!nameInt.equals(null) && !nameInt.equals("")) {
            userName.setText(nameInt);
        }


    }
}