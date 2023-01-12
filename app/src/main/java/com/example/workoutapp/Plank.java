package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Plank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
        TextView indicationsPTV = findViewById(R.id.indicationsPTV);
        indicationsPTV.setText(R.string.plank1);
    }

    public void startexercise(View view) {
        Intent intent=new Intent(getApplicationContext(), StartExercise.class);
        startActivity(intent);
    }

}