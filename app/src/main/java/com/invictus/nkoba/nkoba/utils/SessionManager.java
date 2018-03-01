package com.invictus.nkoba.nkoba.utils;

import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by invictus on 2/26/18.
 */

public class SessionManager {
    private SharedPreferences preferences;

    public SessionManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setLogin(String token, boolean detailsProvided) {
        // to allow automated logging out
        long time_log = new Date().getTime();

        preferences.edit()
                .putBoolean(AppConstants.KEY_IS_LOGGEDIN, true)
                .putBoolean(AppConstants.KEY_DETAILS_PROVIDED, detailsProvided)
                .putString(AppConstants.KEY_TOKEN, token)
                .putLong(AppConstants.KEY_TIME, time_log)
                .apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(AppConstants.KEY_IS_LOGGEDIN, false);
    }

    public boolean getIsDetailsProvided() {
        return preferences.getBoolean(AppConstants.KEY_DETAILS_PROVIDED, false);
    }

    public void setDetailsProvided(boolean provided) {
        preferences.edit()
                .putBoolean(AppConstants.KEY_DETAILS_PROVIDED, provided)
                .apply();
    }

    public String getAuthToken() {
        return preferences.getString(AppConstants.KEY_TOKEN, "");
    }

    public void logout() {
        preferences.edit()
                .putBoolean(AppConstants.KEY_IS_LOGGEDIN, false)
                .putString(AppConstants.KEY_TOKEN, "")
                .putLong(AppConstants.KEY_TIME, 0)
                .apply();
    }

    public boolean shouldLogout() {

        long diffMSec = new Date().getTime() - preferences.getLong(AppConstants.KEY_TIME, 0);
        int diffHours = (int) (diffMSec / (1000 * 60 * 60));


        // 30 days == 720hrs
        // log out at 700hrs
        // maybe refresh token or logout
        if (diffHours >= 700) {
            preferences.edit()
                    .putBoolean(AppConstants.KEY_IS_LOGGEDIN, false)
                    .putString(AppConstants.KEY_TOKEN, "")
                    .putLong(AppConstants.KEY_TIME, 0)
                    .apply();

            return true;
        } else {
            return false;
        }
    }

    public void setOnBoardingShown(boolean show) {
        preferences.edit()
                .putBoolean(AppConstants.KEY_ONBOARDING, show)
                .apply();
    }

    public boolean wasOnBoardingShown() {
        return preferences.getBoolean(AppConstants.KEY_ONBOARDING, false);
    }
}
