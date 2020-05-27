package com.example.timer.Adapter;

        import android.content.Context;
        import android.os.Build;
        import android.os.Handler;
        import android.os.Message;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;

        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;

        import androidx.cardview.widget.CardView;

        import java.lang.String;
        import com.example.timer.Interfaces.QuickMultiSupport;
        import com.example.timer.R;

        import java.util.List;
        import java.util.Observable;
        import java.util.Observer;

public class ThemeLabAdapter extends QuickAdapter<String>  {
   private Context mContext;
   private MyThemeObservable observable;

   public class MyThemeObservable extends Observable{
      public void notifyChanged(Object obj){
           this.setChanged();
           this.notifyObservers(obj);
       }
   }

    public ThemeLabAdapter(Context context, List<String> data, int layoutId) {
        super(context, data, layoutId);
    }

    public ThemeLabAdapter(Context context, List<String> data, QuickMultiSupport<String> support) {
        super(context, data, support);
        this.mContext = context;
        observable = new MyThemeObservable();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            observable.notifyChanged(msg);
        }
    };

    public void addObserver(Observer observer){
        observable.addObserver(observer);
    }

    @Override
    protected void convert(QuickViewHolder holder, final String item, final int position) {

        final EditText tv = holder.getView(R.id.theme_n);
        CardView cardView = holder.getView(R.id.cd_t);
        ImageView v = holder.getView(R.id.delete_t);
        if(tv!=null)
        {
            tv.setText(item);
//            tv.setEnabled(false);

            if(item.equals("addFlag")){

                v.setVisibility(View.INVISIBLE);
                v.setClickable(false);
                tv.setText("+");
                tv.setEnabled(false);
                tv.setTextSize(24);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    tv.setEnabled(!tv.isEnabled());
                        handler.sendMessage(handler.obtainMessage(2,position));
                    }
                });
            }else {
                v.setVisibility(View.VISIBLE);
                v.setClickable(true);
                tv.setEnabled(true);
                tv.setTextSize(16);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   Toast.makeText(mContext,"Card",Toast.LENGTH_SHORT).show();
//                    tv.setEnabled(!tv.isEnabled());
                        handler.sendMessage(handler.obtainMessage(0,position));
                    }
                });
                holder.getView(R.id.delete_t).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    Toast.makeText(mContext,"Delete",Toast.LENGTH_SHORT).show();
                        remove(position);
                        handler.sendMessage(handler.obtainMessage(1,position));
                    }
                });
            }
            tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Toast.makeText(mContext,"Changed",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    handler.sendMessage(handler.obtainMessage(3,position));
                }
            });
        }
    }
}
