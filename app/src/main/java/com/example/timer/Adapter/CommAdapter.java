package com.example.timer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;

import com.example.timer.Model.RecordBean;
import com.example.timer.R;

import java.util.List;

public class CommAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<RecordBean> data;
    private boolean finish = false;
    private boolean noOver = false;
    private final int TITLE = 0;
    private final int CONTENT = 1;
    public CommAdapter(Context context, List<RecordBean> data) {
        this.context = context;
        this.data = data;
        RecordBean recordBean = new RecordBean();
        recordBean.setTitle(null);
        for(int i=0;i<data.size();i++){
            if(!data.get(i).isComplete() && !noOver){
                recordBean.setTitle("- 未完成 -");
                data.add(i,recordBean);
                noOver = true;
                i++;
            }else if (data.get(i).isComplete() && !finish){
                recordBean.setTitle("- 已完成 -");
                data.add(i,recordBean);
                finish = true;
                i++;
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TITLE:
                holder = new CommAdapterHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false));
                break;
            case CONTENT:
                holder = new CommAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        switch (getItemViewType(i)){
            case TITLE:
                CommAdapterHolder2 holder2 = (CommAdapterHolder2) viewHolder;
                holder2.textView.setText(data.get(i).getTitle());
                break;
            case CONTENT:
                CommAdapterHolder holder = (CommAdapterHolder) viewHolder;
                holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
                if(data.size()!=0) {
                    holder.title.setText(data.get(i).getTitle());
                    holder.theme.setText("类型："+data.get(i).getType());
                    holder.time.setText("花费时间："+data.get(i).getCostTime()+"s");
                    if(data.get(i).isComplete()){
                        holder.button.setText("查看");
                    }else {
                        holder.button.setText("开始");
                    }
                }
                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.remove(i);
                        notifyDataSetChanged();
                    }
                });
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

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getType() == null) {
            return TITLE;
        } else {
            return CONTENT;
        }
    }

    public static class CommAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SwipeLayout swipeLayout;
        private ImageButton imageButton;
        private TextView title;
        private TextView theme;
        private TextView time;
        private Button button;
        public CommAdapterHolder(View itemView) {
            super(itemView);
            swipeLayout=(SwipeLayout)itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setSwipeEnabled(false);
            swipeLayout.setOnClickListener(this);
            imageButton=(ImageButton)itemView.findViewById(R.id.delete);
            title=(TextView)itemView.findViewById(R.id.item_title);
            theme = (TextView) itemView.findViewById(R.id.item_theme);
            time = (TextView) itemView.findViewById(R.id.notification_time);
            button = (Button) itemView.findViewById(R.id.card_bottom);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.swipe_layout){
                if(swipeLayout.getOpenStatus()== SwipeLayout.Status.Close){
                    swipeLayout.open(true);
                }else {
                    swipeLayout.close(true);
                }

            }
        }
    }

    public static class CommAdapterHolder2 extends RecyclerView.ViewHolder {
        TextView textView;
        public CommAdapterHolder2(@NonNull View itemView) {
            super(itemView);
            textView =(TextView)itemView.findViewById(R.id.exam_twoweeks);
        }
    }
}
