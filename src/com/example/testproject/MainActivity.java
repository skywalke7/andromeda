package com.example.testproject;

import com.example.geosii.authentication.AuthenticationManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private static String TAG;
	static NotificationManager mNotificationManager;
	
	// Activity methods

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		TAG = this.getApplication().getClass().getName();
		setContentView(R.layout.activity_main);
		validateLoginForm();
		
	}

	@Override
	public void onPause(){
		
		super.onPause();
		notificationBar();
		
	}
	
	@Override
	public void onResume(){
		
		super.onResume();
		clearNotification(1);
		
	}
	
	@Override
	public void onDestroy(){
		
		super.onDestroy();

		
	}
	
	// end activity methods
	
	/**
	 * Method to obtain a object TelephonyManager
	 * 
	 * return the object TelephonyManager
	 * 
	 * */
	
	private TelephonyManager getDataPhone(){
		
		TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		
		return tm;
	}
	
	/**
	 * Method to validate the login form and check empty fields, this also invokes the
	 * asyncTask for to do the authentication process
	 * */
	
	private void validateLoginForm(){
		
		Button login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText adjuster = (EditText) findViewById(R.id.adjuster);
				EditText phoneNumber = (EditText) findViewById(R.id.phnumber);
				
				if(adjuster.getText().toString().equals("") || phoneNumber.getText().toString().equals("")){
					
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage(R.string.invalidFieldsLogin).setCancelable(false)
					.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int id) {                  
							
							dialog.cancel();
							dialog.dismiss();
							
						}
						
					});
					
					AlertDialog alert = builder.create();
					alert.show();
					
				}else{
					
					Log.i(TAG, "Invoke AuthenticationManager ...");
					
					new AuthenticationManager(MainActivity.this).new AuthenticationUser().execute(adjuster.getText().toString(), 
							phoneNumber.getText().toString(), getDataPhone().getDeviceId());
					
				}
				
			}
			
		});
		
	}
	
	/**
	 * Method to show the Notification bar when the application is background
	 * and the user isn't logged
	 * */
	
	private void notificationBar(){
		
		Resources res = getResources();
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.icon_bar)
		.setContentTitle(res.getString(R.string.geosii))
		.setContentText(res.getString(R.string.started));
		
		Intent resultIntent = new Intent(this, MainActivity.class);
		resultIntent.setAction(Intent.ACTION_MAIN);
		resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		
		mBuilder.setContentIntent(resultPendingIntent);
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
		
	}
	
	/**
	 * Method to clear the object notification
	 * */
	
	private void clearNotification(int id){
		
		if(mNotificationManager != null)
			mNotificationManager.cancel(id);
		
	}
	
}
		
