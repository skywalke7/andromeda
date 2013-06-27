	package com.example.geosii.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.provider.ProviderManager;

import android.content.Context;
import android.util.Log;

import com.example.geosii.services.XMPPConnectionService;

public class ConnectionXMPPListener implements ConnectionListener {
	
	private HandlerPresence presence;
	private String TAG;
	private XMPPConnection connection;
	private static final int timeOut = 50000;
	
	public ConnectionXMPPListener(Context context, XMPPConnection connection) {		
		this.connection = connection;
		this.presence = new HandlerPresence(context);
		TAG = getClass().getName();
		ReconnectionXMPP.validateTimeOut(connection,timeOut); /* adding a timeout to check the response time server */		
	}

	@Override
	public void connectionClosed() {		
		Log.i(TAG, "disconnected");
		presence.showStatusNotification(XMPPConnectionService.STATUS_OFFLINE);
		removeDataConnection();		
	}

	@Override
	public void connectionClosedOnError(Exception arg0) {		
		Log.i(TAG, "disconnected for error");
		presence.showStatusNotification(XMPPConnectionService.STATUS_OFFLINE);
		removeDataConnection();		
	}

	@Override
	public void reconnectingIn(int arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void reconnectionSuccessful() {
		// TODO Auto-generated method stub		
	}
	
	public void removeDataConnection() {		
		Log.i(TAG, "removing old connection ...");		
		ReconnectionXMPP.timer.cancel();
		ReconnectionXMPP.flag = false;
		PingPacketListener.lastSuccessfulContact = -1;
		connection.removePacketListener(new PingPacketListener());
		ProviderManager.getInstance().removeIQProvider("ping", "urn:xmpp:ping");
		connection.removeConnectionListener(this);
		connection.disconnect();
		connection = null;
		System.gc();	
		reconnection();		
	}
	
	public void reconnection() {				
		XMPPConnectionService.intentConnect();			
	}

}
