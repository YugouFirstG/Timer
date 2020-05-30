package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.timer.DateBase.ThemeDao;
import com.example.timer.Utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PartTimePlan extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spinner_th,spinner_ty,spinner_pr;
    CheckBox c1,c2,c3,c4,c5,c6,c7;
    EditText ed;
    TextView tv;
    ImageView spinner_al;
    Button bt;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_time_plan);

        initView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_th = findViewById(R.id.theme_sp);
        spinner_al = findViewById(R.id.time_sp);
        spinner_pr = findViewById(R.id.priority_sp);
        spinner_ty = findViewById(R.id.type_sp);
        ed = findViewById(R.id.ed_content);
        tv = findViewById(R.id.time_s);
        bt = findViewById(R.id.bt_ok);

        List<String> themes = new ArrayList<>();
        themes = ThemeDao.getInstance(this).select();
        if(themes.isEmpty()){
            themes.add("学习");
            themes.add("运动");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,themes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_th.setAdapter(adapter);
        spinner_th.setOnItemSelectedListener(this);

        List<String> types = new ArrayList<>();
        types.add("临时记录");
        types.add("习惯");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ty.setAdapter(adapter1);
        spinner_ty.setOnItemSelectedListener(this);

        List<String> priority = new ArrayList<>();
        priority.add("高");
        priority.add("中");
        priority.add("低");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,priority);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pr.setAdapter(adapter2);
        spinner_pr.setOnItemSelectedListener(this);

        spinner_al.setOnClickListener(this);

        c7=findViewById(R.id.sun);
        c1=findViewById(R.id.mon);
        c2=findViewById(R.id.thu);
        c3=findViewById(R.id.thr);
        c4=findViewById(R.id.the);
        c5=findViewById(R.id.frd);
        c6=findViewById(R.id.sat);

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tv.setText(DateUtils.getForMatedTime(hourOfDay,minute,0));
            }
        }, hourOfDay, minute, true);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count!=0){
                    bt.setEnabled(true);
                }else {
                    bt.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_sp:

                timePickerDialog.show();
                break;

            case R.id.bt_ok:
                complete();
                break;
            default:
                break;
        }
    }


    private void complete(){

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("timer", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initialNotification() {

        createNotificationChannel();
        Intent intent = new Intent(this, RecordActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this,"timer")
                .setSmallIcon(R.mipmap.pause)
                .setContentTitle("title")
                .setContentText(ed.getText())
                .setChannelId("timer")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1,builder.build());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
