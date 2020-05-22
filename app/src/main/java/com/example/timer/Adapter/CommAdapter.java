package com.example.timer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;

import com.example.timer.R;

import java.util.List;

public class CommAdapter extends RecyclerSwipeAdapter<CommAdapter.CommAdapterHolder> {

    private Context context;
    private List<IViewType> data;
    public CommAdapter(Context context, List<IViewType> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public CommAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new CommAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(CommAdapterHolder commAdapterHolder, final int position) {
        commAdapterHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        commAdapterHolder.title.setText(data.get(position).toString());
        commAdapterHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return position;
    }

    public static class CommAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SwipeLayout swipeLayout;
        private ImageButton imageButton;
        private TextView title;
        public CommAdapterHolder(View itemView) {
            super(itemView);
            swipeLayout=(SwipeLayout)itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setSwipeEnabled(false);
            swipeLayout.setOnClickListener(this);
            imageButton=(ImageButton)itemView.findViewById(R.id.delete);
            title=(TextView)itemView.findViewById(R.id.item_title);
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
}
