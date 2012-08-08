package core.messager.dochie.service;

import code.messager.dochie.network.DochieNetworkProbe;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DochieServiceNetwork extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.i("thread berjalan"+DochieServiceNetwork.class, "start");
		DochieNetworkProbe dcNetworkHelper = new DochieNetworkProbe();
		while (!dcNetworkHelper.isConnectSocket()) {
			Log.i("on while"+DochieServiceNetwork.class, "start");
			
			try {
				dcNetworkHelper.cekSocket();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				dcNetworkHelper.setConnectSocket(false);
			}
			
			if (dcNetworkHelper.isConnectSocket()==true) {
				Log.i("thread service : "+DochieServiceNetwork.class, "stop");
				stopSelf();
				break;
			}
		}
	}
}
