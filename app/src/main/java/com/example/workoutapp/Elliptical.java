package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Elliptical extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elliptical);

        TextView indicationsETV = findViewById(R.id.indicationsETV);
        indicationsETV.setText(R.string.elliptical1);
    }

    public void startexercise(View view) {
        Intent intent=new Intent(getApplicationContext(), StartExercise.class);
        startActivity(intent);
    }
}