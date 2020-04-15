package com.example.timer.Interfaces;

import android.content.Context;
import android.view.View;

public interface ViewPagerHolder<T> {
    View createView(Context context);

    void onBind(Context context, int position, T data);
}
