package com.example.geosii.xmpp;


import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.Connection;

import android.util.Log;

import com.example.geosii.services.XMPPConnectionService;

public class ReconnectionXMPP {
	
	public static Timer timer;
	public static boolean flag;
	private static String TAG = ReconnectionXMPP.class.getName();
	
	public static void reconnect(Connection connection){
		
		Log.i(TAG, "reconnect connection object -> " + connection);
		XMPPConnectionService.reconnecting = true;
		
		Thread reconnectionThread = new Thread(){
				
			@Override
			public void run(){
					
				try {
						
					Thread.sleep(18000);
					
					Log.i(TAG, "init connection again");
					
					XMPPConnectionService.intentConnect();
						
				} catch (InterruptedException e) {
						
					e.printStackTrace();
				}
					
			}
				
		};
			
		reconnectionThread.setName("Reconnection thread");
		reconnectionThread.setDaemon(true);
		reconnectionThread.start();

	}
	
	public static void validateTimeOut(final Connection connection,final int time){
		
		Log.i(TAG, "validate timeOut");
		
		timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			 
			 @Override
			 public void run(){
				
				 Log.i(TAG, "finished time! flag -> " + flag);

				 Log.i(TAG, "checking last server comunication . . ." + PingPacketListener.getTimeSinceLastContact());
					
				 if(flag && PingPacketListener.getTimeSinceLastContact() == -1){
					 
					 if(connection != null){
						 
						 Log.i(TAG, "disconnecting");
							
						 connection.disconnect();
						 
					 }
	 
				 }else if(PingPacketListener.getTimeSinceLastContact() > time){
					 
					 Log.i(TAG, "connection lost -> " + "[" + PingPacketListener.getTimeSinceLastContact() + "]");
					 
					 if(connection != null){
						 
						 Log.i(TAG, "disconnecting");
							
						 connection.disconnect();
						 
					 }						 
						 
				 }else{
					 
					 flag = true;
					 
				 }
				 
			 }
			 
		 }, 0, time);
		
	}
	
}
