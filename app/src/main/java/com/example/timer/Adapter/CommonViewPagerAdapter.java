package com.example.timer.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import java.util.List;

public class CommonViewPagerAdapter<T extends View> extends PagerAdapter {

    private List<T> mData;

    public CommonViewPagerAdapter(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //       View view = getView(position,null,container);
        container.addView(mData.get(position));
        return mData.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mData.get(position));
    }

    //    private View getView(int position,View view ,ViewGroup container){
//
//        ViewPagerHolder holder =null;
//        if(view == null){
//            //创建Holder
//            holder = mCreator.createViewHolder();
//            view = holder.createView(container.getContext());
////            view.setTag(R.id.common_view_pager_item_tag,holder);
////        }else{
////            holder = (ViewPagerHolder) view.getTag(R.id.common_view_pager_item_tag);
//        }
//        if(holder!=null && mData!=null && mData.size()>0){
//            // 数据绑定
//            holder.onBind(container.getContext(),position,mData.get(position));
//        }
//
//        return view;
//    }
}
