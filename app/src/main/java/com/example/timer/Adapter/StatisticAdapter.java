package com.example.timer.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.Model.RecordBean;
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

    private NumberProgressBar progressBar;
    private int dur = 1;

    public void setDur(int dur) {
        this.dur = dur;
    }

    public StatisticAdapter(Context context, List<IViewType> data, int layoutId) {
        super(context, data, layoutId);
    }

    public StatisticAdapter(Context context, List<IViewType> data, QuickMultiSupport<IViewType> support) {
        super(context, data, support);
    }

    public int getDur() {
        return dur;
    }

    @Override
    protected void convert(QuickViewHolder holder, IViewType item, final int position) {
        switch (item.getItemType()) {
            case 1:
                if (holder.getView(R.id.item_head) != null) {
                    TextView tv = holder.getView(R.id.item_head);
                    TextView detail = holder.getView(R.id.detail);
                    if(item instanceof RecordBean){
                        detail.setText(((RecordBean) item).getCostTime()+"min");
                        tv.setText(((RecordBean) item).getTitle());
                        progressBar = holder.getView(R.id.item_progress);

                        progressBar.setProgress(((RecordBean) item).getCostTime()*100/(24*dur*60));
                    }

                }
                break;
            case 0:
                break;
            default:
                break;
        }
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
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
