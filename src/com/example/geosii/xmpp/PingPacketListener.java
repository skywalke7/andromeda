package com.example.geosii.xmpp;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;

public class PingPacketListener implements PacketListener {

	private Connection connection;
	public static long lastSuccessfulContact = -1;
	private String TAG = getClass().getName();
	
	
	
	public PingPacketListener(){}
	public PingPacketListener(Connection connection2){
		
		this.connection = connection2;
		
	}
	
	@Override
	public void processPacket(Packet packet) {
		
		if(packet == null){
			
			Log.i(TAG, "null packet");
			
		}else{
				
			Log.i(TAG, "send response to server");
			Pong pong = new Pong();
			pong.setFrom(pong.getTo());
			pong.setTo(pong.getFrom());
			connection.sendPacket(pong);

		}
		
		lastSuccessfulContact = System.currentTimeMillis();
		
	}
	
	public static long getTimeSinceLastContact() {
		
	       if (lastSuccessfulContact == -1)
	           return lastSuccessfulContact;
	       long delta = System.currentTimeMillis() - lastSuccessfulContact;
	       
	       return (delta < 0) ? 0 : delta;
	       
	}

}
