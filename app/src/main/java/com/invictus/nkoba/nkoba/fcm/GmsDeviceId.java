package com.invictus.nkoba.nkoba.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by invictus on 4/24/18.
 */

public class GmsDeviceId extends FirebaseInstanceIdService {
    private static final String LOG_TAG = GmsDeviceId.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String device_token = FirebaseInstanceId.getInstance().getToken();
        Log.e(LOG_TAG, device_token);
        sendToServer(device_token);
    }


    private void sendToServer(String token) {
        //send the generated token to server
        // update the existing device
        // since it requires login, this method wont be used for now
        // send if the user is login if not skip
    }
}
