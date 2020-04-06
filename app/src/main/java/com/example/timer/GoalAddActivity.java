package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timer.Utils.DateUtils;
import com.example.timer.DateBase.GoalsDao;

public class GoalAddActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "GoalAddActivity";

    TextView tv1,tv3;
    EditText ed1,tv2,ed2;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_add);
        initView();
    }

    void initView(){
        tv1 = findViewById(R.id.sdt);
        tv2 = findViewById(R.id.edt);
        tv3 = findViewById(R.id.date);
        ed1 = findViewById(R.id.ttl);
        ed2 = findViewById(R.id.cont);
        bt = findViewById(R.id.button_add);

        tv1.setText(DateUtils.getCurrentTime());
        tv3.setText(DateUtils.getCurrentDate());

        tv1.setOnClickListener(this);
        tv3.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sdt:
//                int year = Integer.parseInt(tv1.getText().subSequence(0,4).toString());
//                int month = Integer.parseInt(tv1.getText().subSequence(5,7).toString());
//                int day = Integer.parseInt(tv1.getText().subSequence(8,10).toString());
//
//                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    tv1.setText(DateUtils.getForMatedDate(year,month,dayOfMonth));
//                }
//            }, year, month-1, day).show();
                final int hour = Integer.parseInt(tv1.getText().subSequence(0,2).toString());
                int minutes =  Integer.parseInt(tv1.getText().subSequence(3,5).toString());
                Log.d(TAG,"DATE");
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv1.setText(DateUtils.getForMatedTime(hourOfDay,minute,0));
                    }
                },hour,minutes,true).show();
                break;

            case R.id.button_add:
                invalidData();
                break;
            case R.id.date:
                 int year = Integer.parseInt(tv3.getText().subSequence(0,4).toString());
                 int month = Integer.parseInt(tv3.getText().subSequence(5,7).toString());
                 int day = Integer.parseInt(tv3.getText().subSequence(8,10).toString());
                 new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv3.setText(DateUtils.getForMatedDate(year,month,dayOfMonth));
                    }
                }, year, month-1, day).show();
                break;
            default:
                break;
        }
    }


    private void invalidData(){
        String title,content;

        title = ed1.getText().toString();

        content  = ed2.getText().toString();

        if(title.isEmpty()||content.isEmpty()||tv2.getText().toString().isEmpty()){
            Toast.makeText(this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
        }else{
            addToDateBase();
        }
    }

    private void addToDateBase(){
        String title,startTime,date,content,type;
        int estimate_time;
        title = ed1.getText().toString();
        startTime = tv1.getText().toString();
        estimate_time = Integer.parseInt(tv2.getText().toString());
        content  = ed2.getText().toString();
        date = tv3.getText().toString();
        type = "学习";
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("startTime",startTime);
        values.put("estimateTime",estimate_time);
        values.put("startDate",date);
        values.put("type",type);
        GoalsDao.getInstance(this).insert(values);
    }
}
