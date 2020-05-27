package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.timer.Adapter.ThemeLabAdapter;
import com.example.timer.DateBase.ThemeDao;
import com.example.timer.Interfaces.QuickMultiSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ThemesActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    RecyclerView rc;
    QuickMultiSupport<String> quickMultiSupport;
    List<String> labs;
    ThemeLabAdapter adapter;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        init();
        adapter.addObserver(this);
    }

    private void init(){
        rc = findViewById(R.id.rc_lab);
        bt = findViewById(R.id.bt_ok);
        bt.setEnabled(false);
        bt.setOnClickListener(this);
        quickMultiSupport = new QuickMultiSupport<String>() {
            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public int getLayoutId(String data) {
                return R.layout.item_theme_lab;
            }

            @Override
            public int getItemViewType(String data) {
                return 0;
            }

            @Override
            public boolean isSpan(String data) {
                return false;
            }
        };

        labs = new ArrayList<>();
        labs = ThemeDao.getInstance(this).select();
        if(labs.isEmpty()){
            labs.add("学习");
            labs.add("运动");
            labs.add("游戏");

        }
        labs.add("addFlag");
        rc.setLayoutManager(
                new StaggeredGridLayoutManager(
                        3,
                        StaggeredGridLayoutManager.VERTICAL
                ));

        adapter = new ThemeLabAdapter(this,labs,quickMultiSupport);

        rc.setAdapter(adapter);

    }

    @Override
    public void update(Observable o, Object arg) {
        Message msg = (Message) arg;
        if(msg.what == 0){
            Log.d("Theme",adapter.getData().get((Integer) msg.obj)+(Integer) msg.obj);
        }else if(msg.what==2){
            adapter.addAt("新建项",adapter.getCount()-1);
            Log.d("Theme",(Integer) msg.obj+"");
            bt.setEnabled(true);
        }else if(msg.what==3){
            bt.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ok:
                saveData();
                break;
            default:
                break;
        }
    }

    private void saveData(){
        Log.d("Theme","Save");
        ThemeDao.getInstance(this).clearTable();
        List<String> s = new ArrayList<>();
        for (int i=0;i<adapter.getItemCount()-1;i++)
        {
            EditText et= rc.getChildAt(i).findViewById(R.id.theme_n);
            if(!s.contains(et.getText().toString())){
                s.add(et.getText().toString());
            }
        }
        ThemeDao.getInstance(this).insertAll(s);
        bt.setEnabled(false);
    }
}
