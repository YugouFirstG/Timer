package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.Model.MultiBean;
import com.example.timer.Adapter.QuickAdapter;
import com.example.timer.Adapter.QuickViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ListExampleActivity extends AppCompatActivity {
    private List<IViewType> mData = new ArrayList<>();
    private QuickMultiSupport<IViewType> mQuickSupport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_example);
        initData();
        initViews();
    }

    public interface IViewType{
        int getItemType();
    }

    private void initData(){
        for(int i=0;i<199;i++){
                MultiBean bean = new MultiBean();
                bean.name = "mData----"+i;
                mData.add(bean);
        }
        mQuickSupport = new QuickMultiSupport<IViewType>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getLayoutId(IViewType data) {
                if(data instanceof MultiBean){
                    return R.layout.item_list1;
                }

                //返回其他布局
                return 0;
            }

            @Override
            public int getItemViewType(IViewType data) {
                return data.getItemType();
            }

            @Override
            public boolean isSpan(IViewType data) {
                if(data instanceof MultiBean){
                    return false;
                }
                return false;
            }
        };
    }

    private void initViews(){
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new CommAdapter(this,mData,mQuickSupport));
    }

    class CommAdapter extends QuickAdapter<IViewType>{

        public CommAdapter(Context context, List<IViewType> data, int layoutId) {
            super(context, data, layoutId);
        }

        public CommAdapter(Context context, List<IViewType> data, QuickMultiSupport<IViewType> support) {
            super(context, data, support);
        }

        @Override
        protected void convert(QuickViewHolder holder, IViewType item, final int position) {
            holder.setText(R.id.tv,item.toString());
            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                }
            });
        }
    }
}
