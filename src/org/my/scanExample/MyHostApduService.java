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
		Bundle b=intent.getExtras();
		cardInfo = b.getString("resultStr");
		Log.i(TAG, "HostAdpuService Started!!!");
		byte[] apdu = cardInfo.getBytes();
		
		//This function send data from MyHostApduService to Android Device 
		sendResponseApdu(apdu);
		return super.onStartCommand(intent, flags, startId);
	}

	

	//This function receive response from Android Device
	@Override
	public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
			
		String response = commandApdu.toString();
		Log.i(TAG, response);
		
		return commandApdu;
	
	}

	
	
	@Override
	public void onDeactivated(int reason) {
		
		Log.i(TAG, "Deactivated: " + reason);
		
	}
	
	

}
