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

        final String sampleDate_ISO_8601 = "2016-01-21T17:07:18.134000+00:00";
        final Long sampleSnowflake = 140124670098800641L;

        final Long millis = SimpleTime.getDefault().parseDate(sampleDate_ISO_8601);

        Log.i("SimpleTime", "Sample date_ISO_8601: " + sampleDate_ISO_8601 + ", as milliseconds: " + millis);
        Log.i("SimpleTime", "Sample date milliseconds reformatted: " + SimpleTime.getDefault().toUTCDateString(millis));

        Log.i("SimpleTime", "Simple time: " + SimpleTime.getDefault().toRelativeTime(SimpleTime.getDefault().currentTimeMillis()));
        Log.i("SimpleTime", "Simple time: " + SimpleTime.getDefault().toRelativeTime(millis));

        Log.i("SimpleTime", "Current time: " + SimpleTime.getDefault().currentTimeUTCDateString());

        Log.i("SimpleTime", "Snowflake parse: " + SimpleTime.getDefault().parseSnowflake(sampleSnowflake));

        Log.i("SimpleTime", "Calendar parse:" + SimpleTime.getDefault().toCalendar(millis).getTimeInMillis());
        Log.i("SimpleTime", "Custom template: " + SimpleTime.getDefault().toStringFormat(millis, "d MMMM, yyyy"));
    }
}
