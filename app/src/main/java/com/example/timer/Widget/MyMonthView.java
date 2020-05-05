package com.example.timer.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class MyMonthView extends MonthView {

    private Paint mTextPaint = new Paint();

    private Paint mSchemeBasicPaint = new Paint();

    private float mRadio;
    private int mPadding;
    private float mSchemeBaseLine;

    public MyMonthView(Context context) {
        super(context);
        mTextPaint.setTextSize(dipToPx(context,8));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);

        mRadio = dipToPx(getContext(),7);
        mPadding = dipToPx(getContext(),4);
        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mRadio - metrics.descent+(metrics.bottom-metrics.top);


    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        mSelectedPaint.setStyle(Paint.Style.FILL);
        mSelectedPaint.setColor(0xff6686ff);
        canvas.drawCircle(x+mItemWidth/2.f,y+mItemHeight/2.f,(mItemHeight-2*mPadding)/2.f,mSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        mSchemeBasicPaint.setColor(calendar.getSchemeColor());
      //  canvas.drawCircle(x+mItemWidth-mPadding-mRadio/2,y+mPadding+mRadio,mRadio,mSchemeBasicPaint);
        canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mRadio, y + mPadding + mSchemeBaseLine, mTextPaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y;

        if (isSelected) {//优先绘制选择的
            int s = mSelectTextPaint.getColor();
            mSelectTextPaint.setColor(0xffffffff);
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    mSelectTextPaint);
            mSelectTextPaint.setColor(s);
            //canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);
        } else if (hasScheme) {//否则绘制具有标记的
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

           // canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
        } else {//最好绘制普通文本
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
//                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
//                            calendar.isCurrentMonth() ? mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
    }

    private static int dipToPx(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale+0.5f);
    }
}
