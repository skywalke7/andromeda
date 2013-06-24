package com.example.geosii.services;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;

import com.example.geosii.authentication.AuthenticationManager;
import com.example.geosii.xmpp.ConnectionXMPPListener;
import com.example.geosii.xmpp.HandlerPresence;
import com.example.geosii.xmpp.PingPacketFilter;
import com.example.geosii.xmpp.PingPacketListener;
import com.example.geosii.xmpp.PingProvider;
import com.example.geosii.xmpp.ReconnectionXMPP;
import com.example.testproject.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class XMPPConnectionService{
	
	private XMPPConnection connection;
	
	public static final int STATUS_AVAILABLE = 1;
	public static final int STATUS_OFFLINE = 4;

	public class StartConnectionXMPP extends AsyncTask<Void,Void,Boolean>{

		private String domain;
		private int port;
		private String userName;
		private String password;
		private String resource;
		private ConnectionConfiguration config;
		private String TAG = getClass().getName();
		private HandlerPresence presence;
		private Context context;
		private Thread verifyStatus;
		private final int timeOut = 50000;
		
		public StartConnectionXMPP(Context context){
			
			this.context = context;
			this.domain = "test.bancoinbursa.com";
			this.port = 5222;
			this.userName = "999991";
			this.password = "999991";
			
		}	

		@Override
		protected Boolean doInBackground(Void... params) {

			Log.i(TAG,"connecting ...");
			Log.i(TAG,"connecting [domain->] " + domain);
			Log.i(TAG,"connecting [port->] " + port);
			Log.i(TAG,"connecting [userName->] " + userName);
			
			try {
				
				connection.connect();
				SASLAuthentication.supportSASLMechanism("PLAIN", 0);
				Log.i(TAG,"connected to: " + connection.getHost());
			
			} catch (XMPPException e) {
				
				Log.i(TAG, "failed to connect to: " + connection.getHost());
				e.printStackTrace();
				
				return false;
				
			}
			
			try {
				
				Log.i(TAG, "connected? " + connection.isConnected());
				connection.login(userName, password);
				Log.i(TAG, "logged in host [user->] " + connection.getUser());
				
				return true;
				
			} catch (XMPPException e) {
				
				Log.e(TAG, "failed to log in host [user->] " + connection.getUser());
				e.printStackTrace();
				
				return false;
				
			}catch(Exception e){
				
				Log.e(TAG, "Exception failed to log in host [user->] " + connection.getUser());
				e.printStackTrace();
				
				return false;
				
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			
			presence = new HandlerPresence(context);
			
			if(result){
				
				Log.i(TAG, "connected!");
				connection.addConnectionListener(new ConnectionXMPPListener(context,connection));
				presence.showStatusNotification(STATUS_AVAILABLE);
				connection.addPacketListener(new PingPacketListener(connection), new PingPacketFilter());
				ProviderManager.getInstance().addIQProvider("ping", "urn:xmpp:ping", new PingProvider());
				ReconnectionXMPP.validateTimeOut(connection,timeOut,this);
				
				
			}else{
				
				Log.i(TAG, "connection refused");
				presence.showStatusNotification(STATUS_OFFLINE);
				ReconnectionXMPP.reconnect(connection,context);
				
				
			}
			
			this.cancel(true);
			super.onPostExecute(result);
			Log.i(TAG,"canceled? " + this.isCancelled());
			
		}

		@Override
		protected void onPreExecute() {
						
			config = new ConnectionConfiguration(domain,port);
		    config.setDebuggerEnabled(true);
		    config.setRosterLoadedAtLogin(false);
		    config.setSASLAuthenticationEnabled(true);
			connection = new XMPPConnection(config);
			SmackConfiguration.setKeepAliveInterval(-1);
			 	
			super.onPreExecute();
		}
	
	}
}
