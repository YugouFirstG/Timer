package com.example.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timer.Adapter.StatisticAdapter;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.Model.MultiBean;
import com.github.mikephil.charting.charts.PieChart;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment implements CalendarView.OnCalendarSelectListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private PieChart mPieChart;
    private CalendarView mCalenderView;
    private RecyclerView mRecycleView;
    private QuickMultiSupport<IViewType> mQuickSupport;
    private List<IViewType> mData = new ArrayList<>();


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
//        mPieChart = new PieChart(getContext());
//        drawDailyStatics();
        initFragment(view);

        return view;
    }

    private void initData() {
        mData.clear();
        for (int i = 0; i < 2; i++) {
            MultiBean bean = new MultiBean();
            bean.name = "mData----" + i;
            mData.add(bean);
        }
        mQuickSupport = new QuickMultiSupport<IViewType>() {
            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public int getLayoutId(IViewType data) {
                if (data instanceof MultiBean) {
                    return R.layout.item_piechart;
                }
                return 0;
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

    }

    private void initFragment(View view) {
        initData();
        mCalenderView = view.findViewById(R.id.calendarView1);

        mCalenderView.setOnCalendarSelectListener(this);
        mRecycleView = view.findViewById(R.id.recycle_list1);
        mRecycleView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecycleView.setAdapter(new StatisticAdapter(this.getContext(), mData, mQuickSupport));
    }


    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {

    }
}
