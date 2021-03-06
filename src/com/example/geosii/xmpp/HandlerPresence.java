package com.example.geosii.xmpp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.testproject.HomeActivity;
import com.example.testproject.MainActivity;
import com.example.testproject.R;

public class HandlerPresence {
	
	private NotificationCompat.Builder builder;
	private NotificationManager manager;
	private Context context;
	private String TAG;
	
	public HandlerPresence(Context context){
		
		this.context = context;
		TAG = getClass().getName();
		
	}
	
	public void showStatusNotification(int idStatus){
		
		int icon;
		CharSequence status;
		
		if (idStatus == 1) {
			
			Log.i(TAG, "status available");
			
			icon = R.drawable.ic_menu_stavailable;
			status = "Online - Available";
			
		} else if (idStatus == 2) {
			
			Log.i(TAG, "status unavailable");
			
			icon = R.drawable.unavailable;
			status = "Online - Unavailable";
			
		}  else if (idStatus == 3) {
			
			Log.i(TAG, "status occupied");
			
			icon = R.drawable.closed;
			status = "Application - Closed";
			
		}else{
			
			Log.i(TAG, "status dissconnected"); 
			
			icon = R.drawable.ic_menu_stlogout;
			status = "Off line - Dissconnected";
			
		}
		
		notificationBar(icon,status);
	    
	}
	
	/**
	 * Method to show the Notification bar when the application is background
	 * and the user isn't logged
	 * */
	
	private void notificationBar(int icon,CharSequence status){
		
		Resources res = context.getResources();
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
		.setSmallIcon(icon)
		.setContentTitle(res.getString(R.string.geosii))
		.setContentText(status);
		
		Intent resultIntent = new Intent(context, HomeActivity.class);
		resultIntent.setAction(Intent.ACTION_MAIN);
		resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(HomeActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		
		mBuilder.setContentIntent(resultPendingIntent);
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, mBuilder.build());
		
	}

}
