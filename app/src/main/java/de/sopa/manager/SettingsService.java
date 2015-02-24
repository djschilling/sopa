package de.sopa.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SettingsService {

    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";
    private String MUTE = "mute";

    public SettingsService(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public boolean isFirstTime() {
        String my_first_time = "my_first_time";
        if(sharedPreferences.getBoolean(my_first_time, true)) {
            sharedPreferences.edit().putBoolean(my_first_time, false).apply();
            return true;
        }
        return false;
    }

    public boolean isMute(){
        return sharedPreferences.getBoolean(MUTE, true);
    }

    public void switchMute() {
        sharedPreferences.edit().putBoolean(MUTE, !sharedPreferences.getBoolean(MUTE, true)).apply();
    }
}
