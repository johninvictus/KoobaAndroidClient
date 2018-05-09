package com.invictus.nkoba.nkoba.utils;

/**
 * Created by invictus on 2/24/18.
 */

public class AppConstants {
    /**
     * Connection timeout duration
     */
    public static final int CONNECT_TIMEOUT = 60 * 1000;
    /**
     * Connection Read timeout duration
     */
    public static final int READ_TIMEOUT = 60 * 1000;
    /**
     * Connection write timeout duration
     */
    public static final int WRITE_TIMEOUT = 60 * 1000;
    /**
     * Endpoint
     */
    public static final String BASE_URL = "http://192.168.43.160:5000/api/";

    public static final String TOKEN_KEY_ENTRY = "api_key";

    public static final String KEY_IS_LOGGEDIN = "logged_key";
    public static final String KEY_TOKEN = "token_pref_key";
    public static final String KEY_TIME = "time_key";
    public static final String KEY_DETAILS_PROVIDED = "details_provided_key";

    public static final String KEY_ONBOARDING = "onboarding_key";
}