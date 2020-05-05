package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.timer.Adapter.QuickFragmentPageAdapter;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Calendar;


@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private FragmentContainerView fragmentContainer;
    private SegmentTabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;

    private Toolbar mToolbar;
    private int menuPosition;
    private int mMouth;
    private String[]mouth = {"January","February","March","April","May","June","July","August","September","October","November","December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuPosition = 0;
        if(savedInstanceState!=null){
            menuPosition = savedInstanceState.getInt("position",0);
        }

        initViews();
        Calendar calendar = Calendar.getInstance();
        mMouth = (calendar.get(Calendar.MONTH));
    }

//
//    @Override
//    public void onClick(View v) {
////        switch (v.getId()){
//////            case R.id.add_goal:
//////                Intent intent = new Intent(this,GoalAddActivity.class);
//////                startActivity(intent);
//////
//////                break;
////            case R.id.update:
////                GoalsDao.getInstance(this).dropTable();
//////                TestDao.getInstance(this).deleteAll();
////                break;
////            case R.id.delete:
//////                TestDao.getInstance(this).delete();
////                GoalsDao.getInstance(this).delete(1);
////                break;
////            case R.id.select:
////                intent = new Intent(this,RecordActivity.class);
////                startActivity(intent);
////                break;
////            default:
////                break;
////        }
//    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",menuPosition);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("计划");
        ActionBar actionBar = getSupportActionBar();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (menuPosition){
            case 0:
                fragmentTransaction.add(R.id.fragment_ce,new PlanFragment());
                break;
            case 1:
                fragmentTransaction.add(R.id.fragment_ce,new HabitFragment());
                break;
            case 2:
                fragmentTransaction.add(R.id.fragment_ce,new StatisticFragment());
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        switch (menuPosition){
            case 2:
                getMenuInflater().inflate(R.menu.statistic_toolbar_menu, menu);
                break;
            default:
                getMenuInflater().inflate(R.menu.appbar_menu, menu);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_ce);
        switch (item.getItemId()){
            case R.id.week:
                if(f instanceof StatisticFragment){

                    ((StatisticFragment) f).setStatisticDuration(1);

                }
                break;
            case R.id.day:
                if(f instanceof StatisticFragment){

                    ((StatisticFragment) f).setStatisticDuration(0);

                }
                break;
            case R.id.mouth:
                if(f instanceof StatisticFragment){

                    ((StatisticFragment) f).setStatisticDuration(3);

                }
                break;
            default:
                break;
        }

        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MaACT","onDestroy");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int mSysThemeConfig = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (mSysThemeConfig) {
            // 亮色主题
            case Configuration.UI_MODE_NIGHT_NO:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            // 深色主题
            case Configuration.UI_MODE_NIGHT_YES:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int position = 0;
        switch (item.getItemId()){
            case R.id.bottom_nav_plan:
                position = 0;
                break;
            case R.id.bottom_nav_habit:
                position = 1;
                break;
            case R.id.bottom_nav_statistic:
                position = 2;
                break;
            default:
                break;
        }
        menuPosition = position;
        if (position==0){
            getSupportActionBar().setTitle("计划");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_ce,new PlanFragment());
            fragmentTransaction.commit();
        }else if(position==1){
            getSupportActionBar().setTitle("习惯");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_ce,new HabitFragment());
            fragmentTransaction.commit();
        }else {
            getSupportActionBar().setTitle(mouth[mMouth]+"");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_ce,new StatisticFragment());
            fragmentTransaction.commit();
        }
        getSupportActionBar().invalidateOptionsMenu();
        return true;
    }
}
