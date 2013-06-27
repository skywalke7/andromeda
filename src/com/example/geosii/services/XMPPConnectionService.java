package com.example.geosii.services;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;

import com.example.geosii.files.FileTransfer;
import com.example.geosii.util.Constants;
import com.example.geosii.xmpp.ConnectionXMPPListener;
import com.example.geosii.xmpp.HandlerPresence;
import com.example.geosii.xmpp.PingPacketFilter;
import com.example.geosii.xmpp.PingPacketListener;
import com.example.geosii.xmpp.PingProvider;
import com.example.geosii.xmpp.ReconnectionXMPP;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class XMPPConnectionService extends Service {
		
	public static boolean reconnecting;
	public static final int STATUS_AVAILABLE = 1;
	public static final int STATUS_OFFLINE = 4;
	private static XMPPConnection connection;
	private static String domain;
	private static int port;
	private static String userName;
	private static String password;
	private static String resource;
	private static ConnectionConfiguration config;
	private static String TAG = XMPPConnectionService.class.getName();
	private static HandlerPresence presence;
	private static Context context;
	
	public XMPPConnectionService() {
		Log.i(TAG, "class constructor");		
		domain = "test.bancoinbursa.com";
		port = 5222;
		userName = "999991";
		password = "999991";		
	}	

	/**
	 * Makes a attempt to connect to OpenFire server
	 */
	
	public static void intentConnect() {
		Log.i(TAG,"connecting ...");
		Log.i(TAG,"connecting [domain->] " + domain);
		Log.i(TAG,"connecting [port->] " + port);
		Log.i(TAG,"connecting [userName->] " + userName);		
		
		if (config == null || connection == null) {
			initConfigurations();
		}
		
		try {
			
			connection.connect();
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			Log.i(TAG, "connected in host [host->] " + connection.getHost());			
			connection.login(userName, password);
			reconnecting = false;		
			Log.i(TAG, "logged in host [user->] " + connection.getUser());			
			validateResult(true);			
		} catch (XMPPException e) {			
			Log.e(TAG, "failed to log in host [user->] " + connection.getUser());			
			e.printStackTrace();
			reconnecting = false;
			validateReconnection();			
		} catch(Exception e) {			
			Log.e(TAG, e.toString());			
			e.printStackTrace();
			reconnecting = false;
			validateReconnection();			
		}

	}

	private static void validateResult(Boolean result) {
		presence = new HandlerPresence(context);
		
		if (result) {			
			Log.i(TAG, "connected!");									
			connection.addConnectionListener(new ConnectionXMPPListener(context,connection));			/* a listener to identify the possible deconnection */
			HandlerPresence.clearNotification(Constants.ID_HANDLER_NOTIFICATION);
			presence.showStatusNotification(STATUS_AVAILABLE);
			connection.addPacketListener(new PingPacketListener(connection), new PingPacketFilter());	/* a listener to receive the server ping and respond to this */
			ProviderManager.getInstance().addIQProvider("ping", "urn:xmpp:ping", new PingProvider());			
		} 		
	}


	private static void initConfigurations() {		
		Log.i(TAG, "initConfigurations");					
		config = new ConnectionConfiguration(domain,port);
	    config.setDebuggerEnabled(true);
	    config.setRosterLoadedAtLogin(false);
	    config.setSASLAuthenticationEnabled(true);
		connection = new XMPPConnection(config);
		SmackConfiguration.setKeepAliveInterval(-1);		 	
	}
	
	/**
	 * 
	 * method to validate the status reconnection 
	 * */
	
	private static void validateReconnection() {		
		Log.i(TAG, "there reconnection in process? ->  ... " + reconnecting);		
		if(!reconnecting){
			HandlerPresence.clearNotification(Constants.ID_HANDLER_NOTIFICATION);
			presence.showStatusNotification(STATUS_OFFLINE);
			ReconnectionXMPP.reconnect(connection);			
		}		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {		
		Log.i(TAG, "onCreate");
		context = getApplicationContext();
		intentConnect();		
	}
}
