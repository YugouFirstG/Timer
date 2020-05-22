package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.Chronometer;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class RecordActivity extends BaseActivity implements View.OnClickListener, Chronometer.OnChronometerTickListener {

    String TAG = "RecordActivity";

    Chronometer mChronometer;
    int current = 0;
    BottomSheetBehavior behavior;
    View bottomSheet;
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
        mChronometer.setText(FormatMiss(current));
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

                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            default:
                break;

        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        mChronometer.setText(FormatMiss(current));
        current++;
    }
    public static String FormatMiss(int time){
        String hh=time/3600>9?time/3600+"":"0"+time/3600;
        String mm=(time% 3600)/60>9?(time% 3600)/60+"":"0"+(time% 3600)/60;
        String ss=(time% 3600) % 60>9?(time% 3600) % 60+"":"0"+(time% 3600) % 60;
        return hh+":"+mm+":"+ss;
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

