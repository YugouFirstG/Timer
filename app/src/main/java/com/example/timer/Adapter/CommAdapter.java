package com.example.timer.Adapter;

import android.content.Context;
import android.view.View;

import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;

import com.example.timer.R;

import java.util.List;

public class CommAdapter extends QuickAdapter<IViewType> {

    public CommAdapter(Context context, List<IViewType> data, int layoutId) {
        super(context, data, layoutId);
    }

    public CommAdapter(Context context, List<IViewType> data, QuickMultiSupport<IViewType> support) {
        super(context, data, support);
    }

    @Override
    protected void convert(QuickViewHolder holder, IViewType item, final int position) {
        holder.setText(R.id.item_title, item.toString());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
    }
}
