package com.example.geosii.xmpp;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;

public class PingPacketFilter implements PacketFilter {
	
	private String TAG = getClass().getName();

	@Override
	public boolean accept(Packet packet) {
		// TODO Auto-generated method stub
		if(packet instanceof Ping){
			
			Log.i(TAG, "ping response received");
			
			return true;
			
		}
		
		Log.i(TAG, "ping response not received");
		return false;
	}

}
