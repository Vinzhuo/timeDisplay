package com.vilin.timedisplay;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by vilin on 2018/3/20.
 */

public class TextTimeDisplay extends BaseTime implements Handler.Callback {

    private Handler timerHandler = new Handler(this);
    private String [] weeks;
    private final static int TIMER_RUN_WHAT = 10000;
    private static final int TIMER_PERIOD = 1000;
    private boolean isTimerCanceled = false;
    private TextView timeView;
    private TextView dateView;
    private void updateView() {
        long currentTimeMills = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMills);
        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append(calendar.get(Calendar.HOUR_OF_DAY));
        timeBuilder.append(" : ");
        timeBuilder.append(calendar.get(Calendar.MINUTE));
        timeView.setText(timeBuilder.toString());
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(DateUtils.getDateFormat(currentTimeMills, DateUtils.DATE_FORMAT1));
        dateBuilder.append(" ");
        dateBuilder.append(getWeekString(calendar.get(Calendar.DAY_OF_WEEK)));
        dateView.setText(dateBuilder.toString());
    }

    private String getWeekString(int week) {
       return weeks[week - 1];
    }

    public TextTimeDisplay(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public TextTimeDisplay(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TextTimeDisplay(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        weeks = context.getResources().getStringArray(R.array.day_of_week);
        LayoutInflater.from(context).inflate(R.layout.text_time_linearlayout, this, true);
        setViewAttrs(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timerHandler.removeMessages(TIMER_RUN_WHAT);
        isTimerCanceled = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timerHandler.sendEmptyMessage(TIMER_RUN_WHAT);
        isTimerCanceled = false;
    }

    @Override
    public void start() {
        isTimerCanceled = false;
        timerHandler.removeMessages(TIMER_RUN_WHAT);
        timerHandler.sendEmptyMessage(TIMER_RUN_WHAT);
    }

    @Override
    public void stop() {
        isTimerCanceled = true;
        timerHandler.removeMessages(TIMER_RUN_WHAT);
    }

    private void setViewAttrs(Context context, AttributeSet viewAttrs) {
        if (viewAttrs == null) return;
        timeView = findViewById(R.id.time_txt);
        dateView = findViewById(R.id.date_txt);
        TypedArray typedArray = context.obtainStyledAttributes(viewAttrs, R.styleable.TextTimeDisplay);
        int timeColor = typedArray.getColor(R.styleable.TextTimeDisplay_timeColor, Color.WHITE);
        int dateColor = typedArray.getColor(R.styleable.TextTimeDisplay_timeColor, Color.WHITE);
        timeView.setTextColor(timeColor);
        dateView.setTextColor(dateColor);
        int timeSize = typedArray.getDimensionPixelSize(R.styleable.TextTimeDisplay_timeSize,
                getResources().getDimensionPixelSize(R.dimen.sp_80));
        int dateSize = typedArray.getDimensionPixelSize(R.styleable.TextTimeDisplay_dateSize,
                getResources().getDimensionPixelSize(R.dimen.sp_30));
        timeView.setTextSize(timeSize);
        dateView.setTextSize(dateSize);

        boolean timeBold = typedArray.getBoolean(R.styleable.TextTimeDisplay_timeBold, true);
        timeView.setTypeface(Typeface.defaultFromStyle(timeBold ? Typeface.BOLD : Typeface.NORMAL));
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == TIMER_RUN_WHAT) {
            updateView();
            if (!isTimerCanceled) {
                timerHandler.sendEmptyMessageDelayed(TIMER_RUN_WHAT, TIMER_PERIOD);
            }
        }
        return true;
    }
}
