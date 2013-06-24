package com.example.geosii.xmpp;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.Connection;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.geosii.services.XMPPConnectionService;
import com.example.geosii.services.XMPPConnectionService.StartConnectionXMPP;

public class ReconnectionXMPP {
	
	private static String TAG = ReconnectionXMPP.class.getName();
	public static Count count;
	
	public static void reconnect(Connection connection,final Context context){
		
		Log.i(TAG, "reconnect connection object -> " + connection);
		Log.i(TAG, "reconnect context object -> " + context);
			
		Thread reconnectionThread = new Thread(){
				
			@Override
			public void run(){
					
				try {
						
					Thread.sleep(18000);
					
					Log.i(TAG, "init connection again");
					
					new XMPPConnectionService().new StartConnectionXMPP(context).execute();
						
				} catch (InterruptedException e) {
						
					e.printStackTrace();
				}
					
			}
				
		};
			
		reconnectionThread.setName("Reconnection thread");
		reconnectionThread.setDaemon(true);
		reconnectionThread.start();

	}
	
	public static void validateTimeOut(final Connection connection,final int time, StartConnectionXMPP startConnectionXMPP){
		
		Log.i(TAG, "validate timeOut");
		
		count = new ReconnectionXMPP().new Count(time,1000,connection);
		count.start();
		
	}
	
	class Count extends CountDownTimer{

		Connection connection;
		private long time;
		
		public Count(long millisInFuture, long countDownInterval,Connection connection) {
			super(millisInFuture, countDownInterval);
			this.connection = connection;
			this.time = millisInFuture;
		
		}

		@Override
		public void onFinish() {
			
			Log.i(TAG, "finished time!");

			Log.i(TAG, "checking last server comunication . . ." + PingPacketListener.getTimeSinceLastContact());
			
			if(PingPacketListener.getTimeSinceLastContact() > time || PingPacketListener.getTimeSinceLastContact() == -1){
				
				Log.i(TAG, "connection lost -> " + "[" + PingPacketListener.getTimeSinceLastContact() + "]");
				
				if(connection != null){
					
					Log.i(TAG, "disconnecting");
					
					connection.disconnect();
					
				}

			}else{
				
				Log.i(TAG, "init count");
				count.start();
			}	
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			Log.i(TAG, "remaining time -> " + "["+millisUntilFinished/1000+"]");
			
		}
		
		
		
	}
	
}
