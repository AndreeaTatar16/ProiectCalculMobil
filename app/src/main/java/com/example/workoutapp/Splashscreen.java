package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);   //dispare dupa 2500 secunde
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);    //merge la activity main
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}