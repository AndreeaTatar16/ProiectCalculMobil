package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class StartExercise extends AppCompatActivity {

    private EditText minutesInput;
    private TextView minutesTV;
    private Button btnSet, btnStart, btnReset;
    private CountDownTimer countDownTimer;
    private Boolean mtimerRunning;
    private long mstartTimeInMillis;
    private long mLeftTimeInMillis;
    private long mEndTime;
    private SoundPool soundPool;
    private int sound1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);

        minutesInput = findViewById(R.id.minutesInput);
        btnSet = findViewById(R.id.btnSet);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        minutesTV = findViewById(R.id.minutesTV);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
        sound1=soundPool.load(getApplicationContext(),R.raw.timer,1);  //sunetul de alarma

        //butonul de setare a timpului de executie
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = minutesInput.getText().toString();
                //in caz ca inputul este 0, userul va fi avertizat
                if (input.length() == 0) {
                    Toast.makeText(StartExercise.this, "Please enter a number first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisinput = Long.parseLong(input) * 60000;
                if (millisinput == 0) {
                    Toast.makeText(StartExercise.this, "Please enter valid number of seconds.", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisinput);
                minutesInput.setText("");

            }
        });

        //butnul de start care verifica daca merge deja counterul de minute, astfel se va
        //apela functia de pauza, iar daca nu, se va incepe in mod normal timerul
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mtimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        //functia de reset, face timerul 0
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                soundPool.stop(sound1);   //opreste sunetul cand se apasa pe reset
            }
        });
    }

    //setarea timpului pentru exercitiu
    private void setTime(long milliseconds) {
        mstartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();  //tastatura se inchide automat
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mLeftTimeInMillis;
        // un thread care numara in fundal cate secunde sunt ramase pana la terminarea timpului
        countDownTimer = new CountDownTimer(mLeftTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mLeftTimeInMillis = millisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                mtimerRunning = false;  //timpul de masurat s-a terminat
                updateWatchinterface();
                //la finalizarea counterului, se porneste sunetul
                soundPool.play(sound1,1,1,0,-1,1);
            }
        }.start();
        mtimerRunning = true;
        updateWatchinterface();
    }

    private void pauseTimer() {
        countDownTimer.cancel();  //se opreste temporar numararea
        mtimerRunning = false;   //timpul nu mai curge
        updateWatchinterface();
    }

    //resetarea timpului, care reatribuie valoare timpului ramas la valoarea de start, pentru a o putea lua de la capat
    private void resetTimer() {
        mLeftTimeInMillis = mstartTimeInMillis;
        updateText();
        updateWatchinterface();
    }

    //functia care afiseaza la ecran numarul de ore, sau minute ramase pana la terminarea timpului
    private void updateText() {
        int hours = (int) ((mLeftTimeInMillis / 1000) / 3600);
        int minutes = (int) ((mLeftTimeInMillis / 1000) % 3600) / 60;
        int seconds = (int) ((mLeftTimeInMillis / 1000) % 3600) % 60;
        String timeFormat;
        if (hours > 0) {
            timeFormat = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        minutesTV.setText(timeFormat);
    }

    //modifica interfata in functie de anumite aspecte
    private void updateWatchinterface() {
        //daca timpul curge, nu vor fi vizibile la user campul de adaugare si butoanele
        if (mtimerRunning) {
            minutesInput.setVisibility(View.INVISIBLE);
            btnSet.setVisibility(View.INVISIBLE);
            btnReset.setVisibility(View.INVISIBLE);
            btnStart.setText("PAUSE");  //butonul de start va fi de pauza
        } else {
            minutesInput.setVisibility(View.VISIBLE);
            btnSet.setVisibility(View.VISIBLE);
            btnStart.setText("START");
            //se poate incepe inapoi cu start daca e oprit temporar timpul
            if (mLeftTimeInMillis < 1000) {
                btnStart.setVisibility(View.INVISIBLE);
            } else {
                btnStart.setVisibility(View.VISIBLE);
            }
            //in cazul in care se apasa pauza, va fi vizibil din nou butonul de reset
            if (mLeftTimeInMillis < mstartTimeInMillis) {
                btnReset.setVisibility(View.VISIBLE);
            } else {
                btnReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    //functie folosita pentru a inchide tastatura si a ramane doar view-ul in sine
    private void closeKeyboard() {
        View view = this.getCurrentFocus();  //preia ceea ce este in focus din acel view
        //daca view-ul nu este null, apelez metoda hideSoftInput, cu argumentul view-ului si cu 0, ca sa inchid imediat tastatura
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //cu shared preferences retin valoare introdusa inainte pentru exercitiul asta
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences log = getSharedPreferences("log", MODE_PRIVATE);
        SharedPreferences.Editor editor = log.edit();
        editor.putLong("starttimeinmillis", mstartTimeInMillis);
        editor.putLong("millisleft", mLeftTimeInMillis);
        editor.putBoolean("timerRunning", mtimerRunning);
        editor.putLong("endtime", mEndTime);
        editor.apply();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences log = getSharedPreferences("log", MODE_PRIVATE);
        mstartTimeInMillis = log.getLong("starttimeinmillis",60000);
        mLeftTimeInMillis = log.getLong("millisleft", mstartTimeInMillis);
        mtimerRunning= log.getBoolean("timerRunning",false);
        updateText();
        updateWatchinterface();
        if(mtimerRunning){
            mEndTime = log.getLong("endtime",0);
            mLeftTimeInMillis = mEndTime - System.currentTimeMillis();
            if(mLeftTimeInMillis < 0){
                mLeftTimeInMillis = 0;
                mtimerRunning = false;
                updateText();
                updateWatchinterface();
            }
            else{
                startTimer();
            }
        }
    }
}