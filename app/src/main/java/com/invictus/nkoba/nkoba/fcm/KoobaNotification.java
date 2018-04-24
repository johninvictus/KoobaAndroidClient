package com.invictus.nkoba.nkoba.fcm;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by invictus on 4/24/18.
 */

public class KoobaNotification extends FirebaseMessagingService {
    private static final String LOG_TAG = KoobaNotification.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // read the bundle from the notification
        Map<String, String> data = remoteMessage.getData();

        if (data.size() > 0) {
            insertToDb(data);
            notifyUser(data);
        }

    }

    private void insertToDb(Map<String, String> data) {
        // add the data values to room database

    }

    private void notifyUser(Map<String, String> data) {
        Log.e(LOG_TAG, "data :: " + data);
        //show user notification
        // maybe use shared preference to hide notification when user is on MainActivity
    }
}
