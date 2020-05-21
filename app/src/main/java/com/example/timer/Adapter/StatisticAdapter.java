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
import com.example.timer.Model.SimpleInfo;
import com.example.timer.R;
import com.example.timer.Utils.DateUtils;

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
        int t = item.getItemType();
        switch (item.getItemType()) {
            case 5:
                if (holder.getView(R.id.item_head) != null) {
                    TextView tv = holder.getView(R.id.item_head);
                    TextView detail = holder.getView(R.id.detail);
                    if(item instanceof SimpleInfo){
                        tv.setText(((SimpleInfo) item).getType());
                        detail.setText(DateUtils.getFormatTimeFromSeconds(((SimpleInfo) item).getTime()));
                        progressBar = holder.getView(R.id.item_progress);
                        progressBar.setReachedBarColor(((SimpleInfo) item).getColor());
                        progressBar.setProgress(((SimpleInfo) item).getTime()*100/(24*dur*3600));
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

}
