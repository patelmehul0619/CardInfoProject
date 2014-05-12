package org.my.scanExample;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class MyHostApduService extends HostApduService { 
	
	final static String TAG = MyHostApduService.class.getSimpleName();
	String cardInfo = null;
	
	//This function start the service, collect credit card information passed from MyScanActivity 
	//and pass to the Android Device. 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i(TAG, "HostAdpuService Started!!!");
		
		
		Bundle b=intent.getExtras();
		cardInfo = b.getString("resultStr");
		
		final byte[] apdu = cardInfo.getBytes();
		
		//This thread is created to perform data transfer so that main thread can relieved to perform its tasks.
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				Log.i(TAG, "Inside new Thread!");
				
				//This function send data from MyHostApduService to Android Device 
				sendResponseApdu(apdu);
			}
		}).start();
		
		//return super.onStartCommand(intent, flags, startId);
		
		//re-deliver intent in case service's process get killed after it started
		return START_REDELIVER_INTENT;
	}

	//This function receive response from Android Device
	@Override
	public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
		
		Log.i(TAG, "Inside processCommandApdu!");
			
		String response = commandApdu.toString();
		Log.i(TAG, response);
		
		return commandApdu;
	
	}
	
	@Override
	public void onDeactivated(int reason) {
		
		Log.i(TAG, "Inside onDeactivated!");
		
		Log.i(TAG, "Deactivated: " + reason);
		
	}
}
