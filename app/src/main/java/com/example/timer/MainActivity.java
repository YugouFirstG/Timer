package com.example.timer;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;

import com.example.timer.Adapter.QuickFragmentPageAdapter;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnTabSelectListener {
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private SlidingTabLayout tabLayout;

    private Toolbar mToolbar;
    private int menuPosition;
    private int mMouth;
    private String[]mouth = {"January","February","March","April","May","June","July","August","September","October","November","December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("计划");
        ActionBar actionBar = getSupportActionBar();
        viewPager = findViewById(R.id.common_view_pager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = findViewById(R.id.tab_layout);
        String[] mTitlesArrays = {"计划", "习惯", "统计"};
        fragments = new ArrayList<>();
        fragments.add(new PlanFragment());
        fragments.add(new HabitFragment());
        fragments.add(new StatisticFragment());
        viewPager.setAdapter(new QuickFragmentPageAdapter<>(getSupportFragmentManager(), 0, fragments));
        tabLayout.setViewPager(viewPager, mTitlesArrays);
        tabLayout.setOnTabSelectListener(this);

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

    @SuppressLint("RestrictedApi")
    @Override
    public void onTabSelect(int position) {
        menuPosition = position;
        if (fragments.get(position)==fragments.get(0)){
            getSupportActionBar().setTitle("计划");
        }else if(fragments.get(position)==fragments.get(1)){
            getSupportActionBar().setTitle("习惯");
        }else {
            getSupportActionBar().setTitle(mouth[mMouth]+"");
        }
        getSupportActionBar().invalidateOptionsMenu();
    }

    @Override
    public void onTabReselect(int position) {

    }
}
