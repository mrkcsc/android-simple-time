package com.miguelgaeta.android_simple_time;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.miguelgaeta.simple_time.SimpleTime;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_activity);

        final String sampleDate_ISO_8601 = "2016-06-03T18:24:51.967000+00:00";
        final Long sampleSnowflake = 140124670098800641L;

        final Long millis = SimpleTime.getDefault().parseUTCDate(sampleDate_ISO_8601);

        //final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ");
        //final LocalDateTime localDateTime = LocalDateTime.parse(sampleDate_ISO_8601, dateTimeFormatter);

        //String iso8061 = "2016-06-03T18:24:51.967000+00:00";
        //ZonedDateTime zdt = ZonedDateTime.parse(iso8061);

        //long millis2 = zdt.toInstant().toEpochMilli();

        //Log.e("SimpleTime", "l" + localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() + " | " + millis2);

        Log.e("SimpleTime", "Sample date_ISO_8601: " + sampleDate_ISO_8601 + ", as milliseconds: " + millis);
        Log.e("SimpleTime", "Sample date milliseconds reformatted: " + SimpleTime.getDefault().toUTCDateString(millis));

        Log.e("SimpleTime", "Simple time now: " + SimpleTime.getDefault().toReadableTimeString(SimpleTime.getDefault().currentTimeMillis()));
        Log.e("SimpleTime", "Simple time sam: " + SimpleTime.getDefault().toReadableTimeString(millis));

        Log.e("SimpleTime", "Current time: " + SimpleTime.getDefault().currentTimeUTCDateString());

        Log.e("SimpleTime", "Snowflake parse: " + SimpleTime.getDefault().parseSnowflake(sampleSnowflake));

        Log.e("SimpleTime", "Calendar parse:" + SimpleTime.getDefault().toCalendar(millis).getTimeInMillis());
        Log.e("SimpleTime", "Custom template: " + SimpleTime.getDefault().toDateString(millis));

        Log.e("SimpleTime", "Future time: " + SimpleTime.getDefault().toReadableTimeString(SimpleTime.getDefault().currentTimeMillis() + 1000));
        Log.e("SimpleTime", "Future time blocked: " + SimpleTime.getDefault().toReadableTimeString(SimpleTime.getDefault().currentTimeMillis()));
    }
}
