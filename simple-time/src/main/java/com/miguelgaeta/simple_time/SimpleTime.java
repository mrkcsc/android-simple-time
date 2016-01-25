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

    /**
     * Create a new simple time instance.
     *
     * @param template The formatting template string for parsing and conversion.
     * @param locale The locale we want user facing times to be in.
     */
    public SimpleTime(final String template, Locale locale) {

        if (template == null || locale == null) {

            throw new AssertionError("Template and locale must not be null.");
        }

        @SuppressLint("SimpleDateFormat")
        final SimpleDateFormat formatter = new SimpleDateFormat(template);

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        this.formatter = formatter;
        this.parser = new SimpleDateFormat(template, locale);
    }

    /**
     * Return default simple time instance.
     *
     * @return Default instance.
     */
    public static SimpleTime getDefault() {

        return Default.defaultInstance;
    }

    /**
     * Get the current local system time.
     *
     * @return Current Epoch time in milliseconds.
     */
    public long currentTimeMillis() {

        return System.currentTimeMillis();
    }

    /**
     * Get the current local system time as a UTC date string.
     *
     * @return Date string represented as a UTC string in full ISO 8601 format.
     */
    public String currentTimeUTCDateString() {

        return toUTCDateString(currentTimeMillis());
    }

    /**
     * Parses a snowflake and returns the corresponding number
     * of milliseconds it represents.
     *
     * @param snowflake A nullable snowflake.
     *
     * @return Epoch time in milliseconds.
     */
    public long parseSnowflake(Long snowflake) {

        if (snowflake == null) {
            snowflake = 0L;
        }

        return (snowflake >> 22) + 1420070400000L;
    }

    /**
     * Parses a date and returns the corresponding number
     * of milliseconds it represents.
     *
     * @param dateTime Date string represented as a UTC string in full ISO 8601 format.
     *
     * @return Epoch time in milliseconds.
     */
    public long parseDate(String dateTime) {

        if (dateTime == null) {

            return 0L;
        }

        try {

            return parser.parse(dateTime).getTime();

        } catch (ParseException e) {

            return 0L;
        }
    }

    /**
     * Converts a Epoch time in milliseconds to a
     * human readable string relative to the current time.
     *
     * @param dateMillis Epoch time in milliseconds.
     *
     * @return Human readable string relative to the current time.
     */
    public String toRelativeTime(final Long dateMillis) {

        if (dateMillis == null) {

            throw new AssertionError("Date must not be null.");
        }

        prettyTime.setReference(dateMillis > currentTimeMillis() ?
            new Date(dateMillis) :
            new Date());

        return prettyTime.format(new Date(dateMillis));
    }

    /**
     * Converts a Epoch time in milliseconds to a UTC
     * date string.
     *
     * @param dateMillis Epoch time in milliseconds.
     *
     * @return Date string represented as a UTC string in full ISO 8601 format.
     */
    public String toUTCDateString(Long dateMillis) {

        if (dateMillis == null) {

            return null;
        }

        return formatter.format(new Date(dateMillis));
    }

    /**
     * Default simple time instance uses the most robust version of
     * the ISO 8601 format as the template.
     */
    private static class Default {

        static final String template = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ";

        static SimpleTime defaultInstance = new SimpleTime(template, Locale.getDefault());
    }
}
