package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.DebugUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timer.Model.Goal;
import com.example.timer.Utils.DateUtils;
import com.example.timer.Utils.GoalsDao;

import java.util.Date;

public class GoalAddActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "GoalAddActivity";

    TextView tv1,tv2;
    EditText ed1,ed2;
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
        ed1 = findViewById(R.id.ttl);
        ed2 = findViewById(R.id.cont);
        bt = findViewById(R.id.button_add);

        tv1.setText(DateUtils.getCurrentDate());
        tv2.setText(DateUtils.getCurrentDate());

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sdt:
                int year = Integer.parseInt(tv1.getText().subSequence(0,4).toString());
                int month = Integer.parseInt(tv1.getText().subSequence(5,7).toString());
                int day = Integer.parseInt(tv1.getText().subSequence(8,10).toString());

                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tv1.setText(DateUtils.getForMatedDate(year,month,dayOfMonth));
                }
            }, year, month-1, day).show();

                Log.d(TAG,"DATE");
                break;
            case R.id.edt:
                Log.d(TAG,"endDATE");
                int eYear = Integer.parseInt(tv2.getText().subSequence(0,4).toString());
                int eMonth = Integer.parseInt(tv2.getText().subSequence(5,7).toString());
                int eDay = Integer.parseInt(tv2.getText().subSequence(8,10).toString());

                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv2.setText(DateUtils.getForMatedDate(year,month,dayOfMonth));
                    }
                }, eYear, eMonth - 1, eDay).show();

                break;

            case R.id.button_add:
                addToDateBase();
                break;
            default:
                break;
        }
    }


    private void addToDateBase(){
        String title,startDate,endDate,content;
        title = ed1.getText().toString();
        startDate = tv1.getText().toString();
        endDate = tv2.getText().toString();
        content = ed2.getText().toString();

        if(title.isEmpty()){
            return;
        }

        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("startDate",startDate);
        values.put("endDate",endDate);

        GoalsDao.getInstance(this).insert(values);

    }
}
