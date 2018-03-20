package com.vilin.timedisplay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by vilin on 2018/3/20.
 */

public abstract class BaseTime extends FrameLayout {

    public BaseTime(@NonNull Context context) {
        super(context);
    }

    public BaseTime(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTime(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void start();

    public abstract void stop();


}
