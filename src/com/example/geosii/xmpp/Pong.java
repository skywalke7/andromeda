package com.example.geosii.xmpp;

import org.jivesoftware.smack.packet.IQ;

public class Pong extends IQ {

	public Pong(){
		
		this.setType(Type.RESULT);
		
	}
	
	@Override
	public String getChildElementXML() {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer();
		sb.append("<ping xmlns='urn:xmpp:ping'/>");
		/*sb.append("<error type='cancel'>");
		sb.append("service-unavailable xmlns='urn:ietf:params:xml:ns:xmpp-stanzas'/");
		sb.append("<error type='cancel'/>");*/
		
		return sb.toString();
	}

}
