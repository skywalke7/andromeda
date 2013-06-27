package com.example.geosii.files;

import java.io.File;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import android.os.Environment;
import android.util.Log;

public class FileTransfer {
	
	
	public static void sentFile(XMPPConnection connection,String host){
		

		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Download");
		
		String urlFile = dir+"/prueba.txt";
		
		Log.i("URL", urlFile);
		Log.i("CONNECTION", ""+connection);
		
		ServiceDiscoveryManager sdm = new ServiceDiscoveryManager(connection);
		
		
		sdm.addFeature("http://jabber.org/protocol/disco#info");
        sdm.addFeature("jabber:iq:privacy");
        
		
		FileTransferManager manager = new FileTransferManager(connection);
		FileTransferNegotiator.setServiceEnabled(connection, true);
		OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(host);
		
		File file = new File(urlFile);
		
		try {
		   transfer.sendFile(file, "test_file");
		
		} catch (XMPPException e) {
		   e.printStackTrace();
		}
		Log.i("STATUS", ""+ transfer.getStatus());
		while(!transfer.isDone()) {
			
			Log.i("STATUS 2", ""+transfer.getStatus());
		   if(transfer.getStatus().equals(Status.error)) {
		      System.out.println("ERROR!!! " + transfer.getError());
		   } else if (transfer.getStatus().equals(Status.cancelled)
		                    || transfer.getStatus().equals(Status.refused)) {
		      System.out.println("Cancelled!!! " + transfer.getError());
		   }
		   try {
		      Thread.sleep(1000L);
		   } catch (InterruptedException e) {
		      e.printStackTrace();
		   }
		}
		
		Log.i("----------->", "" + transfer.getStatus());
		if(transfer.getStatus().equals(Status.refused) || transfer.getStatus().equals(Status.error)
		 || transfer.getStatus().equals(Status.cancelled)){
		   System.out.println("refused cancelled error " + transfer.getError());
		} else {
		   Log.i("ENVIADO","Success");
		}
		
	}

}
