package com.example.geosii.xmpp;

import org.jivesoftware.smack.packet.IQ;

public class Ping extends IQ{

	@Override
	public String getChildElementXML() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("<ping xmlns='urn:xmpp:ping'/>");
		
		return sb.toString();
	}
	
	

}
