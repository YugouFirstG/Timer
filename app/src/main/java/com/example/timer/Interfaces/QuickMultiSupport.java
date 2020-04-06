package com.example.timer.Interfaces;

public interface QuickMultiSupport<T> {
    int getViewTypeCount();
    int getLayoutId(T data);

    int getItemViewType(T data);

    boolean isSpan(T data);
}
