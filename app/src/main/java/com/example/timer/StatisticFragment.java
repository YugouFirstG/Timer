package com.example.timer;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.timer.Adapter.CalendarAdapter;
import com.example.timer.Adapter.StatisticAdapter;
import com.example.timer.DateBase.RecordsDao;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;

import com.example.timer.Model.RecordBean;
import com.example.timer.Utils.DateUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment implements CalendarView.OnCalendarSelectListener, TabLayout.OnTabSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CalendarLayout calendarLayout;
    private TabLayout tabLayout;
    private PieChart mPieChart;
    private CalendarView mCalenderView;
    private RecyclerView mRecycleView, statisticListView;
    private QuickMultiSupport<IViewType> mQuickSupport;
    private List<IViewType> mData = new ArrayList<>();
    private List<IViewType> mSData = new ArrayList<>();
    private StatisticAdapter adapter;
    private CalendarAdapter calendarAdapter;
    private int statisticDuration;


    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        initFragment(view);

        return view;
    }

    private void initData(String[] date) {
        mData.clear();
        mSData.clear();
//        if(RecordsDao.getInstance(getContext()).select(" startDate=?",date,null).isEmpty()){
//            for (int i = 0; i < 3; i++) {
//                String title,startTime,endTime,dt,content,type;
//                int costTime;
//                title = "test"+i;
//                startTime = DateUtils.getCurrentTime();
//                endTime = DateUtils.getForMatedTime(22,12,0);
//                costTime = 240;
//                dt = date[0];
//                content = "content";
//                type = "学习";
//
//                ContentValues values = new ContentValues();
//                values.put("title",title);
//                values.put("content",content);
//                values.put("startTime",startTime);
//                values.put("costTime",costTime);
//                values.put("endTime",endTime);
//                values.put("startDate",dt);
//                values.put("type",type);
//                RecordsDao.getInstance(getContext()).insert(values);
//            }
//        }

//        RecordsDao.getInstance(getContext()).dropTable();
        mSData.add(RecordsDao.getInstance(getContext()).getStatisticData(0,DateUtils.getCurrentDate()));
        if(RecordsDao.getInstance(getContext()).select("startDate=?",date,null).isEmpty()){
            mData.add(new RecordBean("type","无记录","content","00:00:00",0,"00:00:00",DateUtils.getCurrentDate()));
        }else {
            mData.addAll(RecordsDao.getInstance(getContext()).select("startDate=?",date,null));
        }

    }

    private void initFragment(View view) {
        String[] s = {"2020-04-28"};
        initData(s);
        tabLayout = view.findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("时间"));
        tabLayout.addTab(tabLayout.newTab().setText("百分比"));
        tabLayout.addOnTabSelectedListener(this);

        mQuickSupport = new QuickMultiSupport<IViewType>() {
            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public int getLayoutId(IViewType data) {
                if (data instanceof RecordBean) {
                    return R.layout.item_theme;
                }else {
                    return R.layout.item_piechart;
                }
            }

            @Override
            public int getItemViewType(IViewType data) {
                return 0;
            }

            @Override
            public boolean isSpan(IViewType data) {
                return false;
            }
        };
        mCalenderView = view.findViewById(R.id.calendarView1);
        calendarLayout = view.findViewById(R.id.calendarLayout1);
        mCalenderView.setOnCalendarSelectListener(this);
        mRecycleView = view.findViewById(R.id.recycle_list1);

        statisticListView = view.findViewById(R.id.statistic_list);
        statisticListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecycleView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new StatisticAdapter(this.getContext(), mData, mQuickSupport);
        calendarAdapter = new CalendarAdapter(getContext(), mSData, mQuickSupport);
        mRecycleView.setAdapter(calendarAdapter);
        mRecycleView.setNestedScrollingEnabled(false);
        statisticListView.setNestedScrollingEnabled(false);
        statisticListView.setAdapter(adapter);
    }

    private void reFreshView(String[] s, int week){
        adapter.clear();
        List<IViewType> ls = new ArrayList<>();
        if(statisticDuration==1){
            adapter.setDur(7);
            ls.addAll(RecordsDao.getInstance(getContext()).select("startDate between ? and ?",s,null));
        }else if(statisticDuration ==0){
            adapter.setDur(1);
            ls.addAll(RecordsDao.getInstance(getContext()).select("startDate=?",s,null));
        }else{
            adapter.setDur(30);
            ls.addAll(RecordsDao.getInstance(getContext()).select("startDate between ? and ?",s,null));
        }

        if(ls.isEmpty()){
            adapter.add(new RecordBean("type","无记录","content","00:00:00",0,"00:00:00",s[0]));
        }else {
            adapter.addAll(ls);
        }

        adapter.notifyData();
        //        mData.add(RecordsDao.getInstance(getContext()).getStatisticData(0,DateUtils.getCurrentDate()));
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        int year,month,day;
        year = calendar.getYear();
        month = calendar.getMonth();
        day = calendar.getDay();
        int w = calendar.getWeek();
        String[] s;
        if(statisticDuration==1){
             s= new String[]{DateUtils.getForMatedDate(year, month - 1, day - w), DateUtils.getForMatedDate(year, month - 1, day +7- w)};
        }else if(statisticDuration == 0){
            s = new String[]{DateUtils.getForMatedDate(year, month - 1, day)};
        }else {
            s= new String[]{DateUtils.getForMatedDate(year, month - 1, 1), DateUtils.getForMatedDate(year, month , 0)};
        }


        reFreshView(s,w);
        //TODO 调用查询函数刷新列表
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        if (tab.getPosition() == 1) {
            for (int i = 0; i < statisticListView.getChildCount(); i++) {
                String s = "";
                TextView v = statisticListView.getChildAt(i).findViewById(R.id.detail);

               if( mData.get(i) instanceof RecordBean){

                    s =((RecordBean) mData.get(i)).getCostTime() * 100 / (24 *adapter.getDur()* 60) + "%";
               }
                v.setText(s);
            }
        } else {
            for (int i = 0; i < statisticListView.getChildCount(); i++) {
                String s = "";
                if( mData.get(i) instanceof RecordBean){
                    s =((RecordBean) mData.get(i)).getCostTime()+"min";
                }
                TextView v = statisticListView.getChildAt(i).findViewById(R.id.detail);
                v.setText(s);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void setStatisticDuration(int t){
        this.statisticDuration = t;
        onCalendarSelect(mCalenderView.getSelectedCalendar(),true);
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Statistic","Destroy");
    }
}
