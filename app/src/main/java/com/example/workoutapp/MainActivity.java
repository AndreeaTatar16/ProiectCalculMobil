package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    //url-ul din care voi prelua datele din api pentru daily Tip
    String jsonURL ="https://calories-burned-by-api-ninjas.p.rapidapi.com/v1/caloriesburned?activity=skiing";

    //valorile care se modifica
    TextView stepsTV, distanceTV, caloriesTV, dailyTipTV;
    //textView statice
    TextView Steps, Km, kcal, distanceDone, calorieDone;
    Button btnTrain, btnRemove, btnAdd, btnReset;

    int count = 0;
    int reference;

    double measurePrevious = 0;
    SensorManager sensorManager;
    boolean running = false;

    Sensor countSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepsTV = findViewById(R.id.stepsTV);
        distanceTV = findViewById(R.id.distanceTV);
        caloriesTV = findViewById(R.id.caloriesTV);
        dailyTipTV = findViewById(R.id.dailyTipTV);
        btnTrain = findViewById(R.id.btnTrain);
        btnReset = findViewById(R.id.btnReset);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SharedPreferences preferences = getSharedPreferences("STEP_COUNTER_REF", MODE_PRIVATE);
        reference = preferences.getInt("STEP_REF",0);

        running = true;
        //TYPE_STEP_COUNTER
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   //senzor pentru numararea pasilor
        //daca senzorul este gasit il inregistreaza in sensorManager
        if(countSensor!=null)
        {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);   //primeste updateuri despre senzor si le trimie la UI o data la 0.2 secunde
        }
        else {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
        }

        //verific daca TYPE_STEP_COUNTER exista pe device-ul meu
//        if(!HasGotSensorCaps()){
//            Toast.makeText(this, "Nu are senzorul ala", Toast.LENGTH_SHORT).show();
//        }

        //butonul de Train va incarca urmatoarea activitate
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Exercise.class);
                startActivity(intent);
            }
        });

        //butonul de reset permite resetarea nr de pasi facuti, a km si a caloriilor
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

//    public boolean HasGotSensorCaps()
//    {
//        PackageManager pm = this.getPackageManager();
//
//        // Require at least Android KitKat
//
//        int currentApiVersion = Build.VERSION.SDK_INT;
//
//        // Check that the device supports the step counter and detector sensors
//
//        return currentApiVersion >= 19
//                && pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
//                && pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)
//    }



    //preia datele si le "prelucreaza" sub forma de JSONArray
    public void onClickGetData(View view) throws IOException {
        Toast.makeText(this, "Getting the tip for you...", Toast.LENGTH_SHORT).show();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://calories-burned-by-api-ninjas.p.rapidapi.com/v1/caloriesburned?activity=skiing")
                .get()
                .addHeader("X-RapidAPI-Key", "0895180283msh73f4f2f480296bfp1b4e79jsna7ee12686549")
                .addHeader("X-RapidAPI-Host", "calories-burned-by-api-ninjas.p.rapidapi.com")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i(TAG, "onFailure: something went wrong");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String receivedData = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(receivedData);
                    JSONObject tip = jsonArray.getJSONObject(7);
                    String name = "";
                    for(int i=0; i < jsonArray.length(); i++) {
                        name = tip.getString("calories_per_hour");
                    }
                    String finalName = name;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dailyTipTV.setText("Did you know that 1h of downhill skiing can burn up to " + finalName + "kcal?");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);   //primeste updateuri despre senzor si le trimie la UI o data la 0.2 secunde
    }

    @Override
    protected void onPause() {
        super.onPause();
        running=false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent Event) {
        if(Event != null){
            float xAcceleration = Event.values[0];
            float yAcceleration = Event.values[1];
            float zAcceleration = Event.values[2];

            double measure = Math.sqrt(xAcceleration * xAcceleration + yAcceleration * yAcceleration + zAcceleration * zAcceleration);
            double delta = measure - measurePrevious;
            measurePrevious = measure;

            if(delta >5)
                count++;

            //sa arate cifrele cu 2 zecimale dupa virgula
            DecimalFormat df = new DecimalFormat("#.##");

            stepsTV.setText(String.valueOf(count));
            distanceTV.setText(df.format(count * 0.076));   //un pas normal are 0.76m
            caloriesTV.setText(df.format(count * 0.05));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void reset()
    {
        reference = count;
        count  = 0;
        SharedPreferences.Editor editor = getSharedPreferences("STEP_COUNT_REF", MODE_PRIVATE).edit();
        editor.putInt("STEP_REF", reference);

        stepsTV.setText("0");
        distanceTV.setText("0");
        caloriesTV.setText("0");
    }

}