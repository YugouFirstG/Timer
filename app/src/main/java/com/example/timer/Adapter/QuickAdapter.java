package com.example.timer.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.timer.Interfaces.QuickMultiSupport;

import java.util.List;

public abstract class QuickAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mData;
    private int mLayoutId;
    private QuickMultiSupport<T> mSupport;
    private boolean isRecycler;
    private int mPosition;

    public QuickAdapter(Context context,List<T> data, int layoutId){
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    public QuickAdapter(Context context,List<T> data, QuickMultiSupport<T> support){
        this(context,data,0);
        this.mSupport = support;
    }

    public void addAt(T elem,int p){
        mData.add(p,elem);
        notifyData();
    }

    public void add(T elem){
        mData.add(elem);
        notifyData();
    }
    public void addAll(List<T> data){
        mData.addAll(data);
        notifyData();
    }
    public void addFirst(T elem){
        mData.add(0,elem);
        notifyData();
    }

    public void set(T oldElem,T newElem){
        set(mData.indexOf(oldElem),newElem);
        notifyData();
    }

    public void set(int index, T elem){
        mData.set(index,elem);
        notify();
    }

    public void remove(int index){
        mData.remove(index);
        notifyData();
    }


    public void replaceALl(List<T> elem){
        mData.clear();
        mData.addAll(elem);
        notifyData();
    }

    public void clear(){
        mData.clear();
        notifyData();
    }

    public void notifyData(){
        if(isRecycler){
            notifyDataSetChanged();
        }else {
            notifyListDataSetChanged();
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        isRecycler = true;
        View view;
        if(mSupport!=null){
            int layoutId = mSupport.getLayoutId(mData.get(mPosition));
            view = LayoutInflater.from(mContext).inflate(layoutId,parent,false);
        }else {
            view = LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        }
        QuickViewHolder holder = new QuickViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof QuickViewHolder){
            convert((QuickViewHolder) holder,mData.get(position),position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mSupport == null || recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            // 如果设置合并单元格就占用SpanCount那个多个位置
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mSupport.isSpan(mData.get(position))) {
                        return gridLayoutManager.getSpanCount();
                    } else if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (mSupport == null) {
            return;
        }
        int position = holder.getLayoutPosition();
        // 如果设置合并单元格
        if (mSupport.isSpan(mData.get(position))) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuickViewHolder holder;
        if(convertView == null){
            int layoutId = mLayoutId;
            if(mSupport!=null){
                layoutId = mSupport.getLayoutId(mData.get(position));
            }
            holder =  createListHolder(parent,layoutId);

        }else {
            holder = (QuickViewHolder) convertView.getTag();
            if(mSupport!=null){
                int layoutId = mSupport.getLayoutId(mData.get(position));
                if(layoutId!=holder.getLayoutId()){
                    holder = createListHolder(parent,layoutId);
                }
            }
        }
        convert(holder,mData.get(position),position);
        return holder.itemView;
    }

    protected abstract void convert(QuickViewHolder holder,T item,int position);

    private QuickViewHolder createListHolder(ViewGroup parent, int layoutId) {
        QuickViewHolder holder;
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        holder = new QuickViewHolder(itemView, layoutId);
        itemView.setTag(holder);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        mPosition = position;
        if(mSupport!=null){
            return mSupport.getItemViewType(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        if(mSupport!=null){
            return mSupport.getViewTypeCount()+super.getViewTypeCount();
        }
        return super.getViewTypeCount();
    }


    public List<T> getData(){
        return mData;
    }

}
