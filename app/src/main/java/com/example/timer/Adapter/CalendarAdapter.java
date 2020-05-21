package com.example.timer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.Model.SimpleInfo;
import com.example.timer.Model.StatisticBean;
import com.example.timer.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends QuickAdapter<IViewType> {
    private PieChart pieChart;
    private int dur = 1;

    public void setDur(int dur) {
        this.dur = dur;
    }

    public CalendarAdapter(Context context, List<IViewType> data, int layoutId) {
        super(context, data, layoutId);
    }

    public CalendarAdapter(Context context, List<IViewType> data, QuickMultiSupport<IViewType> support) {
        super(context, data, support);
    }
    public int getDur() {
        return dur;
    }

    @Override
    protected void convert(QuickViewHolder holder, IViewType item, int position) {

        if (holder.getView(R.id.p_chart) != null) {
            pieChart = holder.getView(R.id.p_chart);
            Legend l = pieChart.getLegend();
            pieChart.animateXY(1000,1000);
//            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setEnabled(false);
            drawDailyStatics(pieChart);
        }
    }

    private void drawDailyStatics(PieChart pieChart) {
        List<PieEntry> entryList = new ArrayList<>();
        PieDataSet pieDataSet;
        List<Integer> colors = new ArrayList<>();

        if(getData().get(0) instanceof StatisticBean){
            StatisticBean statisticBean = (StatisticBean) getData().get(0);
            for (int i = 0; i < statisticBean.getTypes(); i++) {
                SimpleInfo s = statisticBean.getList().get(i);
                entryList.add(new PieEntry(s.getTime()*1.0f/(dur*24*3600)*100, s.getType()));
                colors.add(s.getColor());
            }
        }


        pieDataSet = new PieDataSet(entryList, "");

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);


        Description description = new Description();
        description.setText("日统计");
        description.setEnabled(false);
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(70f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(false);
//        pieChart.setEntryLabelTextSize(25f);
//        pieChart.setEntryLabelTypeface(Typeface.SANS_SERIF);
        pieChart.invalidate();
    }

    private void drawMonthStatics() {

    }

    private void drawWeekStatics() {

    }
}
