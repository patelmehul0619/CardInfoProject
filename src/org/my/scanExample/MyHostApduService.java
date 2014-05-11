package org.my.scanExample;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class MyHostApduService extends HostApduService { 
	
	final static String TAG = MyHostApduService.class.getSimpleName();
	String cardInfo = null;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle b=intent.getExtras();
		cardInfo = b.getString("resultStr");
		Log.i(TAG, "HostAdpuService Started!!!");
		byte[] apdu = cardInfo.getBytes();
		sendResponseApdu(apdu);
		return super.onStartCommand(intent, flags, startId);
	}

	

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
