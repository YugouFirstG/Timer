package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.timer.Adapter.QuickFragmentPageAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnScrollChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private BottomNavigationView bottom_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
////            case R.id.add_goal:
////                Intent intent = new Intent(this,GoalAddActivity.class);
////                startActivity(intent);
////
////                break;
//            case R.id.update:
//                GoalsDao.getInstance(this).dropTable();
////                TestDao.getInstance(this).deleteAll();
//                break;
//            case R.id.delete:
////                TestDao.getInstance(this).delete();
//                GoalsDao.getInstance(this).delete(1);
//                break;
//            case R.id.select:
//                intent = new Intent(this,RecordActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
    }





    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        viewPager = findViewById(R.id.common_view_pager);
        viewPager.setOffscreenPageLimit(3);
        bottom_nav = findViewById(R.id.bottom_nav);
        bottom_nav.setOnNavigationItemSelectedListener(this);
        fragments = new ArrayList<>();
        fragments.add(new PlanFragment());
        fragments.add(new HabitFragment());
        fragments.add(new StatisticFragment());
        viewPager.setAdapter(new QuickFragmentPageAdapter<>(getSupportFragmentManager(), 0, fragments));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bottom_nav_plan:
                viewPager.setCurrentItem(0);
                break;
            case R.id.bottom_nav_habit:
                viewPager.setCurrentItem(1);
                break;
            case R.id.bottom_nav_statistic:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottom_nav.setSelected(false);
        bottom_nav.getMenu().getItem(viewPager.getCurrentItem()).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }
}
