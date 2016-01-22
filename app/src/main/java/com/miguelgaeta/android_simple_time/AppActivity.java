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

        String testTime = "2016-01-21T17:07:18.134000+00:00";
        Long millis = SimpleTime.getDefault().toMilliseconds(testTime);
        String testTimeAgain = SimpleTime.getDefault().toUTCDateString(millis);

        Log.i("SimpleTime", "Simple time: " + testTimeAgain);
        Log.i("SimpleTime", "Simple time: " + SimpleTime.getDefault().toRelativeTime(SimpleTime.getDefault().currentTimeMillis()));
        Log.i("SimpleTime", "Simple time: " + SimpleTime.getDefault().toRelativeTime(millis));

        Log.i("SimpleTime", "Current time: " + SimpleTime.getDefault().toUTCDateString());
    }
}
