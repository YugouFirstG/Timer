package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.timer.Model.Goal;
import com.example.timer.Utils.GoalsDao;
import com.example.timer.Utils.TestDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText e1,e2,e3,e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1,b2,b3,b4;
        FloatingActionButton f = findViewById(R.id.add_goal);

        b1 = findViewById(R.id.insert);
        b2 = findViewById(R.id.update);
        b3 = findViewById(R.id.delete);
        b4 = findViewById(R.id.select);
        e1 = findViewById(R.id.title);
        e2 = findViewById(R.id.ct);
        e3 = findViewById(R.id.sd);
        e4 = findViewById(R.id.ed);

        f.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_goal:
                Intent intent = new Intent(this,GoalAddActivity.class);
                startActivity(intent);

                break;
            case R.id.insert:
                Log.d("MainActivity",e1.getText().toString()+"\n"+e2.getText().toString());
                ContentValues values = new ContentValues();
                values.put("title",e1.getText().toString());
                values.put("content",e2.getText().toString());
                values.put("startDate",e3.getText().toString());
                values.put("endDate",e4.getText().toString());
                GoalsDao.getInstance(this).insert(values);
//                TestDao.getInstance(this).insert();
                break;
            case R.id.update:
                GoalsDao.getInstance(this).dropTable();
//                TestDao.getInstance(this).deleteAll();
                break;
            case R.id.delete:
//                TestDao.getInstance(this).delete();
                GoalsDao.getInstance(this).delete("123");
                break;
            case R.id.select:
                List<Goal> ls = GoalsDao.getInstance(this).select();
                Log.d("MainActivity","size "+ls.size());
//                List ls = TestDao.getInstance(this).select();
//                for(int i=0;i<ls.size();i++){
//                    Log.d("MainActivity",ls.get(i)+"");
//                }
//                Log.d("MainActivity",ls.size()+"");
                break;
            default:
                break;
        }
    }
}
