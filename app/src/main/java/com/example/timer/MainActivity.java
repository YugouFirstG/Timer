package com.example.timer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.Model.GoalBean;
import com.example.timer.Model.MultiBean;
import com.example.timer.DateBase.GoalsDao;
import com.example.timer.Adapter.QuickAdapter;
import com.example.timer.Adapter.QuickViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private List<MainActivity.IViewType> mData = new ArrayList<>();
    private QuickMultiSupport<MainActivity.IViewType> mQuickSupport;
    SwipeRefreshLayout mGoalsRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1,b2,b3,b4;
        FloatingActionButton f = findViewById(R.id.add_goal);
        mGoalsRefresh = findViewById(R.id.srl_goal_list);
        mGoalsRefresh.setOnRefreshListener(this);

        b1 = findViewById(R.id.insert);
        b2 = findViewById(R.id.update);
        b3 = findViewById(R.id.delete);
        b4 = findViewById(R.id.select);


        f.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        initData();
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_goal:
                Intent intent = new Intent(this,GoalAddActivity.class);
                startActivity(intent);

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
                List<GoalBean> ls = GoalsDao.getInstance(this).select();
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

    @Override
    public void onRefresh() {
        initData();
        initViews();
    }

    public interface IViewType{
        int getItemType();
    }
    private void initData(){
        List<GoalBean> ls = GoalsDao.getInstance(this).select();
        mData.clear();
        mData.addAll(ls);
        mQuickSupport = new QuickMultiSupport<MainActivity.IViewType>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getLayoutId(MainActivity.IViewType data) {
                if(data instanceof GoalBean){
                    return R.layout.item_goals;
                }

                //返回其他布局
                return 0;
            }

            @Override
            public int getItemViewType(MainActivity.IViewType data) {
                return data.getItemType();
            }

            @Override
            public boolean isSpan(MainActivity.IViewType data) {
                if(data instanceof MultiBean){
                    return false;
                }
                return false;
            }
        };
        mGoalsRefresh.setRefreshing(false);
    }
    private void initViews(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        RecyclerView recyclerView = findViewById(R.id.goal_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MainActivity.CommAdapter(this,mData,mQuickSupport));
    }
    class CommAdapter extends QuickAdapter<MainActivity.IViewType> {

        public CommAdapter(Context context, List<MainActivity.IViewType> data, int layoutId) {
            super(context, data, layoutId);
        }

        public CommAdapter(Context context, List<MainActivity.IViewType> data, QuickMultiSupport<MainActivity.IViewType> support) {
            super(context, data, support);
        }

        @Override
        protected void convert(QuickViewHolder holder, MainActivity.IViewType item, final int position) {
            holder.setText(R.id.item_title,((GoalBean) item).getTitle());
            holder.setText(R.id.item_start_time,((GoalBean) item).getStartTime());
            holder.setText(R.id.item_estimate,((GoalBean) item).getEstimateDuration()+"minutes");
            holder.setText(R.id.item_date,((GoalBean) item).getType());
            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                }
            });
        }
    }
}
