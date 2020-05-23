package com.example.timer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import android.content.ContentValues;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

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
import com.example.timer.Model.SimpleInfo;
import com.example.timer.Model.StatisticBean;
import com.example.timer.Utils.DateUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity implements View.OnClickListener, Chronometer.OnChronometerTickListener, AdapterView.OnItemSelectedListener {

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button bt_end;
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
        String[] mItems = getResources().getStringArray(R.array.lunar_str);
        themes = ThemeDao.getInstance(this).select();
        if(themes.isEmpty()){
            themes.add("学习");
            themes.add("运动");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,themes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                b_start.setClickable(false);
                b_stop.setClickable(true);
                startTime = DateUtils.getCurrentTime();
                mChronometer.start();
                break;
            case R.id.stop:
                b_start.setClickable(true);
                b_stop.setClickable(false);
                endTime = DateUtils.getCurrentTime();
                mChronometer.stop();
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
        current++;
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
//        RecordsDao.getInstance(this).insert(values);

        Log.d("Re",themes.get(spinner.getSelectedItemPosition())+" "+current+" "+cot+" \n"+startTime+"\n"+endTime);
        reset();
    }

    private void reset(){
        b_start.setClickable(true);
        b_stop.setClickable(false);
        current = 0;
        mChronometer.stop();
        mChronometer.setText(DateUtils.FormatMiss(current));
    }
//    public static void slideToUp(View view){
//        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//
//        slide.setDuration(2000);
//        slide.setFillAfter(true);
//        slide.setFillEnabled(true);
//        view.startAnimation(slide);
//
//        slide.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
//
//    public void onDialog(View view)
//    {
//        Dialog dialog=new Dialog(this);//可以在style中设定dialog的样式
//        dialog.setContentView(R.layout.diallog);
//        WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
//        lp.gravity= Gravity.BOTTOM;
//        lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.width= WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().setAttributes(lp);
//        //设置该属性，dialog可以铺满屏幕
//        dialog.getWindow().setBackgroundDrawable(null);
//        dialog.show();
////      dialog.getWindow().setWindowAnimations();
//        slideToUp(dialog.getWindow().findViewById(R.id.pop_layout));
//
//    }
}

