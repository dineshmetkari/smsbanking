package com.nimbleteam.smsbanking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsSentBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent i) {
	Log.i(getClass().getSimpleName(), i.getAction());
    }
}
