package code.messager.dochie.network;

import java.net.Socket;

import core.messager.dochie.helper.DochieJavaMailHelper;

public class DochieNetworkProbe {

	private boolean connectSocket = false;
	
	public DochieNetworkProbe() {
		super();
	}
	
	
	public void cekSocket(){
		try {
			Socket s = new Socket(DochieJavaMailHelper.mailhost, DochieJavaMailHelper.portServerimap);
			setConnectSocket(s.isConnected());	
		} catch (Exception e) {
			setConnectSocket(false);
		}
	}


	public boolean isConnectSocket() {
		return connectSocket;
	}

	public void setConnectSocket(boolean connectSocket) {
		this.connectSocket = connectSocket;
	}
	
	
}
