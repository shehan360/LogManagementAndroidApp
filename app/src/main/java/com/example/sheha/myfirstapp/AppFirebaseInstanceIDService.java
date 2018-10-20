package com.example.sheha.myfirstapp;

import android.app.Service;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sheha on 12/11/2017.
 */

public class AppFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private  final static String TAG="FCM Token";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token){
        // TODO: Implement this method to send any registration to app's server.
    }
}
