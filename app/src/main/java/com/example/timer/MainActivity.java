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


import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private SlidingTabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

}
