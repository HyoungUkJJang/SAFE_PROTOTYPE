package com.example.win.safe;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService{
    private static final String TAG = "MyFireBaseIDService";

    @Override
    public void onTokenRefresh() {
    String token = FirebaseInstanceId.getInstance().getToken();
    if(token==null)
    {
        Log.d(TAG,"Already Token");
    }
    else
        {
        Log.d(TAG, token);
    }
    sendRegistrationServer(token);
    }
    public void sendRegistrationServer(String token)
    {

    }
}
