package com.witgeeks.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OutgoingCallReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		Log.w("TAG OUT", "PASO");
		Log.d(OutgoingCallReceiver.class.getSimpleName(), intent.toString() + ", call to: " + phoneNumber);
		Toast.makeText(context, "Outgoing call catched: " + phoneNumber, Toast.LENGTH_LONG).show();
	}

}
