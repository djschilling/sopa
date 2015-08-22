package de.sopa.manager;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class SettingsService {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String MUTE = "mute";
    private static final String MY_FIRST_TIME = "MY_FIRST_TIME";

    SharedPreferences sharedPreferences;

    public SettingsService(Context context) {

        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public boolean isFirstTime() {

        if (sharedPreferences.getBoolean(MY_FIRST_TIME, true)) {
            sharedPreferences.edit().putBoolean(MY_FIRST_TIME, false).apply();

            return true;
        }

        return false;
    }


    public boolean isMute() {

        return sharedPreferences.getBoolean(MUTE, false);
    }


    public void switchMute() {

        sharedPreferences.edit().putBoolean(MUTE, !isMute()).apply();
    }
}
