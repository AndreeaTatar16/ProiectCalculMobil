package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Exercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }

    public void intentEvent(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.chestBtn:
                intent = new Intent(getApplicationContext(), Chest.class);
                startActivity(intent);
                break;

            case R.id.backBtn:
                intent = new Intent(getApplicationContext(), Back.class);
                startActivity(intent);
                break;

            case R.id.bicepsBtn:
                intent = new Intent(getApplicationContext(), Biceps.class);
                startActivity(intent);
                break;

            case R.id.legsBtn:
                intent = new Intent(getApplicationContext(), Legs.class);
                startActivity(intent);
                break;

            case R.id.cardioBtn:
                intent = new Intent(getApplicationContext(), Cardio.class);
                startActivity(intent);
                break;

            case R.id.absBtn:
                intent = new Intent(getApplicationContext(), Abs.class);
                startActivity(intent);
                break;
        }
    }
}