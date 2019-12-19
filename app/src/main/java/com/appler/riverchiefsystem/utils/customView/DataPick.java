package com.appler.riverchiefsystem.utils.customView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPick {
    private Activity mContext;
    private TextView textView;
    private LayoutInflater inflater;
    private View view;
    private String title;
    private AlertDialog dialog;

    public DataPick(Activity mContext, String title, TextView textView) {
        this.mContext = mContext;
        this.textView = textView;
        this.title = title;

        inflater = LayoutInflater.from(mContext);

        showDataDailog();

    }

    private void showDataDailog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        view = inflater.inflate(R.layout.dialog_datepicker, null);
        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setSelectedDate(new Date());
        calendarView.setSelectionColor(mContext.getResources().getColor(R.color.blue));
        TextView tv_time_title = view.findViewById(R.id.tv_time_title);
        tv_time_title.setText(title);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                try {
                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getDate());
                    textView.setText(format);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
            }
        });
        builder.setView(view);
        dialog = builder.show();
    }

}
