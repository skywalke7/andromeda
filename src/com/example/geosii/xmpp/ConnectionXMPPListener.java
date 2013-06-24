package com.example.geosii.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.provider.ProviderManager;

import android.content.Context;
import android.util.Log;

import com.example.geosii.services.XMPPConnectionService;

public class ConnectionXMPPListener implements ConnectionListener{
	
	private HandlerPresence presence;
	private String TAG;
	private Context context;
	private XMPPConnection connection;
	
	public ConnectionXMPPListener(Context context, XMPPConnection connection) {
		
		this.connection = connection;
		this.context = context;
		this.presence = new HandlerPresence(context);
		TAG = getClass().getName();
		
	}

	@Override
	public void connectionClosed() {
		
		Log.i(TAG, "disconnected");
		removeDataConnection();
		presence.showStatusNotification(XMPPConnectionService.STATUS_OFFLINE);
		
	}

	@Override
	public void connectionClosedOnError(Exception arg0) {
		
		Log.i(TAG, "disconnected for error");
		removeDataConnection();
		presence.showStatusNotification(XMPPConnectionService.STATUS_OFFLINE);
		
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
	
	public void removeDataConnection(){
		
		Log.i(TAG, "removing old connection ...");
		
		ReconnectionXMPP.count.cancel();
		PingPacketListener.lastSuccessfulContact = -1;
		connection.removePacketListener(new PingPacketListener());
		ProviderManager.getInstance().removeIQProvider("ping", "urn:xmpp:ping");
		connection.removeConnectionListener(this);
		connection.disconnect();
		connection = null;
		System.gc();	
		reconnection();
		
	}
	
	public void reconnection(){
			
		new XMPPConnectionService().new StartConnectionXMPP(context).execute();
			
	}

}
