package com.miguelgaeta.simple_time;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Miguel Gaeta on 1/22/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class SimpleTime {

    private final DateTimeFormatter formatter;

    private final Locale locale;
    private final Calendar calendar;
    private final ZoneId zoneId;

    private final DateTimeFormatter formatterTime;
    private final DateTimeFormatter formatterDateTime;
    private final DateTimeFormatter formatterDate;

    /**
     * Initialize {@link AndroidThreeTen} backport.
     */
    public static void init(final Application application) {
        AndroidThreeTen.init(application);
    }

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

        this.formatter = DateTimeFormatter.ofPattern(template);
        this.locale = locale;
        this.calendar = Calendar.getInstance(locale);
        this.zoneId = DateTimeUtils.toZoneId(calendar.getTimeZone());

        this.formatterTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale);
        this.formatterDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale);
        this.formatterDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale(locale);
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

        return Instant.now().toEpochMilli();
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
    public long parseUTCDate(final String dateTime) {

        if (dateTime == null) {

            return 0L;
        }

        try {

            return LocalDateTime.parse(dateTime, formatter).toInstant(ZoneOffset.UTC).toEpochMilli();

        } catch (ArrayIndexOutOfBoundsException e) {
            return 0L;
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public String toReadableTimeString(final Long unixTimeMillis) {

        if (unixTimeMillis == null) {
            return null;
        }

        final ZonedDateTime zonedDateTime = getZonedDateTime(unixTimeMillis);

        calendar.setTimeInMillis(currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final boolean englishLocale = locale.getLanguage().equals("en");

        if (!englishLocale) {
            if (unixTimeMillis > calendar.getTimeInMillis()) {
                return formatterTime.format(zonedDateTime);
            } else {
                return formatterDateTime.format(zonedDateTime);
            }
        }

        if (unixTimeMillis > calendar.getTimeInMillis()) {
            return "Today at " + formatterTime.format(zonedDateTime);
        }

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        if (unixTimeMillis > calendar.getTimeInMillis()) {
            return "Yesterday at " + formatterTime.format(zonedDateTime);
        }

        return formatterDateTime.format(zonedDateTime);
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

        return formatter.format(getZonedDateTime(unixTimeMillis, ZoneOffset.UTC));
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

        return formatterDate.format(getZonedDateTime(unixTimeMillis));
    }

    private ZonedDateTime getZonedDateTime(final long unixTimeMillis, final ZoneId zoneId) {

        return Instant.ofEpochMilli(unixTimeMillis).atZone(zoneId);
    }

    private ZonedDateTime getZonedDateTime(final long unixTimeMillis) {

        return getZonedDateTime(unixTimeMillis, zoneId);
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
