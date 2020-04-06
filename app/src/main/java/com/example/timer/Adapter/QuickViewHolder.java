package com.example.timer.Adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class QuickViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<WeakReference<View>> mViews;
    private int mLayoutId;
    public QuickViewHolder(@NonNull View itemView) {
        this(itemView,-1);
    }

    public QuickViewHolder(View itemView, int layoutId){
        super(itemView);
        mViews = new SparseArray<>();
        this.mLayoutId = layoutId;
    }
    public int getLayoutId(){
        return mLayoutId;
    }

    public QuickViewHolder setOnClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
        return this;
    }

    public QuickViewHolder setOnLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
        return this;
    }

    public QuickViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public View getView(){
        return itemView;
    }

    public <T extends View> T getView(int viewId){
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if(viewWeakReference==null){
            view = itemView.findViewById(viewId);
            if(view!=null){
                mViews.put(viewId,new WeakReference<View>(view));
            }
        }else{
            view = viewWeakReference.get();
        }
        return (T) view;
    }

    public QuickViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        if (tv != null && !TextUtils.isEmpty(text)) {
            tv.setText(text);
        }
        return this;
    }
    public QuickViewHolder setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(color);
        }
        return this;
    }
    public QuickViewHolder setVisible(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }
    public QuickViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }
    public QuickViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }
    public QuickViewHolder setImageResource(int viewId, int imageResId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(imageResId);
        return this;
    }
    public QuickViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }
}
