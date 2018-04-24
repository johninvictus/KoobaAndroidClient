package com.invictus.nkoba.nkoba.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by invictus on 4/24/18.
 */

public class GmsDeviceId extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String device_token = FirebaseInstanceId.getInstance().getToken();
        sendToServer(device_token);
    }


    private void sendToServer(String token) {
        //send the generated token to server
        // update the existing device
        // since it requires login, this method wont be used for now
    }
}
