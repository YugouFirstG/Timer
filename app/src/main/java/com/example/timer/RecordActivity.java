package com.example.timer;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.timer.DateBase.RecordsDao;
import com.example.timer.DateBase.ThemeDao;
import com.example.timer.Services.CountService;
import com.example.timer.Utils.DateUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class RecordActivity extends BaseActivity implements View.OnClickListener, Chronometer.OnChronometerTickListener, AdapterView.OnItemSelectedListener, Observer {

    String TAG = "RecordActivity";

    Chronometer mChronometer;
    int current = 0;
    BottomSheetBehavior behavior;
    View bottomSheet;
    List<String> themes;
    Spinner spinner;
    EditText content;
    String startTime,endTime;
    FloatingActionButton b_start, b_stop, b_reset;
    CountService mService;
    Button bt_end;
    Intent i;

    ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((CountService.CountBinder) service).getService();
            mService.addObserver(RecordActivity.this);
            if(mService.getState()!=1&&current!=0){
                mService.startCount(current);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService=null;
        }
    };


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent  intent = getIntent();
        current = intent.getIntExtra("current",0);


        i = new Intent(this, CountService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startService(i);
        bindService(
                i,
                con,
                BIND_AUTO_CREATE
        );



        setContentView(R.layout.activity_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        b_start = findViewById(R.id.start);
        b_stop = findViewById(R.id.stop);
        b_reset = findViewById(R.id.reset);
        bt_end = findViewById(R.id.finish);
        content = findViewById(R.id.ed_content);


        b_start.setClickable(true);
        b_stop.setClickable(false);
        bt_end.setOnClickListener(this);
        mChronometer = findViewById(R.id.m_chronometer);
        b_start.setOnClickListener(this);
        b_stop.setOnClickListener(this);
        b_reset.setOnClickListener(this);
        mChronometer.setText(DateUtils.FormatMiss(current));
        mChronometer.setOnChronometerTickListener(this);
        bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        PieChart pieChart = findViewById(R.id.circular);
        drawDailyStatics(pieChart);

        spinner = findViewById(R.id.theme_sp);
        themes = ThemeDao.getInstance(this).select();
        if(themes.isEmpty()){
            themes.add("学习");
            themes.add("运动");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,themes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mChronometer.start();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.stop:
                stop();
                break;
            case R.id.reset:
                reset();
                break;
            case R.id.finish:
                complete(v);
                break;
            default:
                break;

        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        mChronometer.setText(DateUtils.FormatMiss(current));
    }



    private void drawDailyStatics(PieChart pieChart) {
        List<PieEntry> entryList = new ArrayList<>();
        PieDataSet pieDataSet;
        List<Integer> colors = new ArrayList<>();

        entryList.add(new PieEntry(100, ""));
        colors.add(0xff6686ff);

        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.setClickable(false);
        pieDataSet = new PieDataSet(entryList, "");
        pieDataSet.setColors(colors);


        PieData pieData = new PieData(pieDataSet);


        pieChart.setData(pieData);
        pieChart.getData().setDrawValues(false);
        Description description = new Description();
        description.setText("日统计");
        description.setEnabled(false);

        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(95f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(false);

        pieChart.invalidate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void complete(View v){

        if(content.getText().toString().isEmpty()){
            Toast.makeText(this,"请填入内容",Toast.LENGTH_SHORT).show();
            return;
        }
        if(current==0){
            Toast.makeText(this,"无效记录",Toast.LENGTH_SHORT).show();
            return;
        }
        String cot = content.getText().toString();
        String time = mChronometer.getText().toString();
        String theme = themes.get(spinner.getSelectedItemPosition());
        String date = DateUtils.getCurrentDate();
        ContentValues values = new ContentValues();
        values.put("title",cot);
        values.put("content",cot);
        values.put("startTime",startTime);
        values.put("costTime",current);
        values.put("endTime",endTime);
        values.put("startDate",date);
        values.put("type",theme);
        values.put("recorded",1);
        RecordsDao.getInstance(this).insert(values);

        Log.d("Re",themes.get(spinner.getSelectedItemPosition())+" "+time+" "+cot+" \n"+startTime+"\n"+endTime);
        reset();
    }

    private void reset(){
        b_start.setClickable(true);
        b_stop.setClickable(false);
        bt_end.setEnabled(false);
        current = 0;
        mService.stopCount();
        mChronometer.setText(DateUtils.FormatMiss(current));
    }

    private void stop(){
        b_start.setClickable(true);
        b_stop.setClickable(false);
        endTime = DateUtils.getCurrentTime();
        startTime = mService.getStartTime();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mService.stopCount();
    }

    private void start(){
        b_start.setClickable(false);
        b_stop.setClickable(true);
        mService.startCount(current);
    }

    @Override
    protected void onDestroy() {
        unbindService(con);
        super.onDestroy();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void update(Observable o, Object arg) {
            current = (int)arg;
    }
}

