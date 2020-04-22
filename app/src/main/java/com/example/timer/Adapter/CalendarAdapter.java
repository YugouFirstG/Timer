package com.example.timer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;
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

    public CalendarAdapter(Context context, List<IViewType> data, int layoutId) {
        super(context, data, layoutId);
    }

    public CalendarAdapter(Context context, List<IViewType> data, QuickMultiSupport<IViewType> support) {
        super(context, data, support);
    }

    @Override
    protected void convert(QuickViewHolder holder, IViewType item, int position) {

        if (holder.getView(R.id.p_chart) != null) {
            pieChart = holder.getView(R.id.p_chart);
            Legend l = pieChart.getLegend();
            pieChart.animateXY(1400,1400);
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
        for (int i = 0; i < 4; i++) {
            entryList.add(new PieEntry((i + 1) * 10f, "test " + i));
        }
        pieDataSet = new PieDataSet(entryList, "");
        colors.add(0xFF888888);
        colors.add(0xFF888800);
        colors.add(0xFF880088);
        colors.add(0xFF008888);
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.CYAN);
        pieData.setValueTextSize(15f);


        Description description = new Description();
        description.setText("日统计");
        description.setEnabled(false);
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(60f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleAlpha(0);
//        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelTextSize(25f);
        pieChart.setEntryLabelTypeface(Typeface.SANS_SERIF);
        pieChart.invalidate();
    }

    private void drawMonthStatics() {

    }

    private void drawWeekStatics() {

    }
}
