package com.example.timer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Model.RecordBean;
import com.example.timer.R;

import java.util.List;

public class HabitAdapter extends RecyclerSwipeAdapter<HabitAdapter.HabitAdapterHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<RecordBean> data;

    public HabitAdapter(Context context, List<RecordBean> data) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public HabitAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_habit, parent, false);
        return new HabitAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(HabitAdapterHolder habitAdapterHolder, final int position) {
        if(data.size()!=0) {
            habitAdapterHolder.title.setText(data.get(position).getTitle());
            habitAdapterHolder.theme.setText("类型："+data.get(position).getType());
            habitAdapterHolder.time.setText("花费时间："+data.get(position).getCostTime()+"s");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    public static class HabitAdapterHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView title;
        private TextView theme;
        private TextView time;

        public HabitAdapterHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.habit_layout);
            title = (TextView) itemView.findViewById(R.id.item_title);
            theme = (TextView) itemView.findViewById(R.id.item_theme);
            time = (TextView) itemView.findViewById(R.id.notification_time);
        }
    }
}
