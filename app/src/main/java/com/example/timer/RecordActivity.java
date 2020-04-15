package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener, Chronometer.OnChronometerTickListener {

    Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Button b_start, b_stop, b_reset;
        b_start = findViewById(R.id.start);
        b_stop = findViewById(R.id.stop);
        b_reset = findViewById(R.id.reset);
        mChronometer = findViewById(R.id.m_chronometer);
        b_start.setOnClickListener(this);
        b_stop.setOnClickListener(this);
        b_reset.setOnClickListener(this);
        mChronometer.setOnChronometerTickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                String start = mChronometer.getText().toString();

                mChronometer.start();
                break;
            case R.id.stop:
                String stop = mChronometer.getText().toString();
                mChronometer.stop();
                break;
            case R.id.reset:
                mChronometer.setBase(SystemClock.elapsedRealtime());
                break;
            default:
                break;

        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {

    }
}
