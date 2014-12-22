package com.witgeeks.broadcast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomingCallReceiver extends BroadcastReceiver {

	private static MediaRecorder myAudioRecorder;
	
	private static AudioManager audioManager;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			String state = extras.getString(TelephonyManager.EXTRA_STATE);

			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				Log.w("MY_DEBUG_TAG", phoneNumber);
			} else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				Log.w("MY_DEBUG_TAG", TelephonyManager.EXTRA_STATE_OFFHOOK);

				if (myAudioRecorder == null) {
					startMedia(context);
				}
			} else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
				Log.w("MY_DEBUG_TAG", TelephonyManager.EXTRA_STATE_IDLE);

				if (myAudioRecorder != null) {
					myAudioRecorder.stop();
					myAudioRecorder.release();

					myAudioRecorder = null;
					Log.w("DEBUG", "Now its fine.");
				} else {
					Log.w("DEBUG", "There was an error.");
				}

			}
		}
	}
	
	private void startMedia(Context context){
		//turn on speaker
	     audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	     //audioManager.setSpeakerphoneOn(true);
	    //increase Volume
	     audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL)-1, 0);
		
		myAudioRecorder = new MediaRecorder();
        
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss",Locale.US);
        
        String format = s.format(new Date());
        format = format.replaceAll(" ", "_").replaceAll(":", "-");
        String outputFile = Environment.getExternalStorageDirectory().
        	      getAbsolutePath() + "/"+format+".3gp";
        
        String storeLoc = Environment.getExternalStorageDirectory() .getAbsolutePath() + "/MyRecordingsRheti/";
        File path = new File(storeLoc);
        if(!path.exists())
        	path.mkdir();
        
        try {
			File audioFile = File.createTempFile(format, ".3gp", path);
			
	        myAudioRecorder.setOutputFile(audioFile.getAbsolutePath());     
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
			myAudioRecorder.prepare();
			myAudioRecorder.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
	        Log.w(getClass().getSimpleName(),
	                "Exception in stopping recorder", e);
	          // can fail if start() failed for some reason 
		}
        
	}
}
