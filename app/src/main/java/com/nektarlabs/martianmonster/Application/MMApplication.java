package com.nektarlabs.martianmonster.Application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.nektarlabs.martianmonster.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.UUID;

import io.fabric.sdk.android.Fabric;

/**
 * Created by viktordenic on 11/6/15.
 */
public class MMApplication extends Application {

    private boolean testing;

    @Override public void onCreate() {
        super.onCreate();
        testing = true;

        Fabric.with(this, new Crashlytics());

        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_secret));

        saveUserToParse();
    }

    private void saveUserToParse() {
        if (ParseUser.getCurrentUser() == null) {
            String uniqueString = UUID.randomUUID().toString().substring(0, 5);

            ParseUser user = new ParseUser();

            if (testing == false) {
                user.setUsername(uniqueString);
                user.setPassword(uniqueString);
            }
            else {
                user.setUsername("android_test_user");
                user.setPassword("password");
            }

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {

                    } else {

                    }
                }
            });
        }
    }
}
