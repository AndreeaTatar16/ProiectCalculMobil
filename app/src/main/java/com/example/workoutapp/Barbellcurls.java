package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Barbellcurls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbellcurls);
        TextView indicationsBCTV = findViewById(R.id.indicationsBCTV);
        indicationsBCTV.setText(R.string.barbellcurls1);
    }

    public void startexercise(View view) {
        Intent intent=new Intent(getApplicationContext(), StartExercise.class);
        startActivity(intent);
    }
}