package com.nektarlabs.martianmonster.Application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.nektarlabs.martianmonster.R;
import com.parse.Parse;

import io.fabric.sdk.android.Fabric;

/**
 * Created by viktordenic on 11/6/15.
 */
public class MMApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_secret));
    }
}
