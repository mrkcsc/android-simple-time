package com.miguelgaeta.simple_time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Miguel Gaeta on 1/22/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class SimpleTime {

    private final SimpleDateFormat formatter;

    private final Locale locale;

    private final DateFormat formatterTime;
    private final DateFormat formatterDateTime;
    private final DateFormat formatterDate;

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

        final SimpleDateFormat formatter = new SimpleDateFormat(template, locale);

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        this.formatter = formatter;
        this.locale = locale;

        this.formatterTime = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
        this.formatterDate = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        this.formatterDateTime = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
    }

    /**
     * Return default simple time instance.
     *
     * @return Default instance.
     */
    public static SimpleTime getDefault() {

        return Default.threadLocal.get();
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
    public long parseUTCDate(String dateTime) {

        if (dateTime == null) {

            return 0L;
        }

        try {

            return formatter.parse(dateTime).getTime();

        } catch (ParseException e) {
            return 0L;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0L;
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public String toReadableTimeString(final Long unixTimeMillis) {

        final Calendar calendar = toCalendar(currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final boolean englishLocale = locale.getLanguage().equals("en");

        if (!englishLocale) {
            if (unixTimeMillis > calendar.getTimeInMillis()) {
                return formatterTime.format(unixTimeMillis);
            } else {
                return formatterDateTime.format(unixTimeMillis);
            }
        }

        if (unixTimeMillis > calendar.getTimeInMillis()) {
            return "Today at " + formatterTime.format(unixTimeMillis);
        }

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        if (unixTimeMillis > calendar.getTimeInMillis()) {
            return "Yesterday at " + formatterTime.format(unixTimeMillis);
        }

        return formatterDateTime.format(unixTimeMillis);
    }

    /**
     * Converts a Epoch time in milliseconds to a UTC
     * date string.
     *
     * @param unixTimeMillis Unix time in milliseconds.
     *
     * @return Date string represented as a UTC string in full ISO 8601 format.
     */
    public String toUTCDateString(Long unixTimeMillis) {

        if (unixTimeMillis == null) {

            return null;
        }

        return formatter.format(new Date(unixTimeMillis));
    }

    /**
     * Converts a Epoch time in milliseconds to a
     * date string.
     *
     * @param unixTimeMillis Unix time in milliseconds.
     *
     * @return Date string represented as a UTC string in full ISO 8601 format.
     */
    public String toDateString(final Long unixTimeMillis) {

        if (unixTimeMillis == null) {

            return null;
        }

        return formatterDate.format(unixTimeMillis);
    }

    /**
     * Converts a unix time in milliseconds to a calendar
     * instance configured to the provided locale.
     *
     * @param unixTimeMillis Unix time in milliseconds.
     *
     * @return Calendar representing the provided time.
     */
    public Calendar toCalendar(final Long unixTimeMillis) {

        final Calendar calendar = Calendar.getInstance(locale);

        calendar.setTimeInMillis(unixTimeMillis);

        return calendar;
    }

    /**
     * Default simple time instance uses the most robust version of
     * the ISO 8601 format as the template.
     */
    static class Default {

        private static final String template = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ";

        final static ThreadLocal<SimpleTime> threadLocal = new ThreadLocal<SimpleTime>() {
            protected SimpleTime initialValue() {
                return new SimpleTime(template, Locale.getDefault());
            }
        };
    }
}
