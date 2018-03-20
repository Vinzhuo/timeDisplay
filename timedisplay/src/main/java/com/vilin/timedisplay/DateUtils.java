package com.vilin.timedisplay;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vilin on 2018/3/20.
 */

public class DateUtils {

    public static final String DATE_FORMAT1 = "yyyy-MM-dd";

    @StringDef({DATE_FORMAT1})
    @Retention(RetentionPolicy.SOURCE)
    @interface DATE_FORMAT {
    }
    final public long getHour(long mills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mills);
        return cal.get(Calendar.HOUR);
    }

    final public long getMinute(long mills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mills);
        return cal.get(Calendar.MINUTE);
    }

    final public long getWeek(long mills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mills);
        return cal.get(Calendar.MINUTE);
    }

    final public static String getDateFormat(long milliseconds, @DATE_FORMAT String dataFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat, Locale.CHINA);
        return dateFormat.format(new Date(milliseconds));
    }




}
