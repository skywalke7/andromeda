package com.example.testproject;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class HomeActivity extends Activity{
	
	private String TAG;
	
	// Activity methods
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		 
		 super.onCreate(savedInstanceState);
		 
		 TAG = getClass().getName();
		 //MainActivity.mNotificationManager.cancel(1);
		 Log.i(TAG, "Oncreate");
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
			
		return true;
	}
	
	// end activity methods

}
