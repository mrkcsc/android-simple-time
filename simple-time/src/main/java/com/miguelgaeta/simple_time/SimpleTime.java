package com.miguelgaeta.simple_time;

import android.annotation.SuppressLint;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Miguel Gaeta on 1/22/16.
 */
public class SimpleTime {

    private final SimpleDateFormat parser;
    private final SimpleDateFormat formatter;

    private final PrettyTime prettyTime = new PrettyTime(Locale.getDefault());

    public SimpleTime(final String template) {

        if (template == null) {

            throw new AssertionError("Template must not be null.");
        }

        @SuppressLint("SimpleDateFormat")
        final SimpleDateFormat formatter = new SimpleDateFormat(template);

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        this.formatter = formatter;
        this.parser = new SimpleDateFormat(template, Locale.US);
    }

    public static SimpleTime getDefault() {

        return Default.defaultInstance;
    }

    public long currentTimeMillis() {

        return System.currentTimeMillis();
    }

    public String toUTCDateString() {

        return toUTCDateString(currentTimeMillis());
    }

    public String toUTCDateString(Long dateMillis) {

        if (dateMillis == null) {

            return null;
        }

        return formatter.format(new Date(dateMillis));
    }

    public long toMilliseconds(Long snowflake) {

        if (snowflake == null) {
            snowflake = 0L;
        }

        return (snowflake >> 22) + 1420070400000L;
    }

    public long toMilliseconds(String dateTime) {

        if (dateTime == null) {

            return 0L;
        }

        try {

            return parser.parse(dateTime).getTime();

        } catch (ParseException e) {

            return 0L;
        }
    }

    public String toRelativeTime(final Long dateMillis) {

        if (dateMillis == null) {

            throw new AssertionError("Date must not be null.");
        }

        prettyTime.setReference(dateMillis > currentTimeMillis() ?
            new Date(dateMillis) :
            new Date());

        return prettyTime.format(new Date(dateMillis));
    }

    private static class Default {

        static final String template = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ";

        static SimpleTime defaultInstance = new SimpleTime(template);
    }
}
