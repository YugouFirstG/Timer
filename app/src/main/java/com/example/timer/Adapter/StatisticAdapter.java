package com.example.timer.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdapter extends QuickAdapter<IViewType> {
    private PieChart pieChart;

    public StatisticAdapter(Context context, List<IViewType> data, int layoutId) {
        super(context, data, layoutId);
    }

    public StatisticAdapter(Context context, List<IViewType> data, QuickMultiSupport<IViewType> support) {
        super(context, data, support);
    }

    @Override
    protected void convert(QuickViewHolder holder, IViewType item, int position) {
        switch (item.getItemType()) {
            case 0:
                pieChart = holder.getView(R.id.p_chart);
                drawDailyStatics(pieChart);
                break;
            case 1:
                break;
            default:
                break;
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
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.CYAN);
        pieData.setValueTextSize(15f);

        Description description = new Description();
        description.setText("日统计");

        pieChart.setDescription(description);
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(60f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(25f);
        pieChart.setEntryLabelTypeface(Typeface.SANS_SERIF);
        pieChart.invalidate();
    }

    private void drawMonthStatics() {

    }

    private void drawWeekStatics() {

    }
}
