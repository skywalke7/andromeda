package com.example.geosii.authentication;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.geosii.consumeRS.RegisterService;
import com.example.geosii.parser.RegistrationResult;
import com.example.geosii.parser.UserRegistration;
import com.example.geosii.services.XMPPConnectionService;
import com.example.geosii.util.Constants;
import com.example.geosii.xmpp.HandlerPresence;
import com.example.testproject.HomeActivity;
import com.example.testproject.MainActivity;
import com.example.testproject.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AuthenticationManager{ 
	
	private static String TAG;
	private Context context;
	
	/**
	 * Class constructor
	 * 
	 * @param context to be used in the asyncTask and alertDialog 
	 */
	
	public AuthenticationManager(Context context){
		this.context = context;
	}
	
	
	/**
	 * AsyncTask for the authentication process, this invokes the 
	 * register service manager and handles error messages triggered on the same stage
	 */
	
	public class AuthenticationUser  extends AsyncTask<String, Void, UserRegistration> {
		
		private RegisterService registerService;
		private UserRegistration userRegistration;
		private ProgressDialog progressDialog;
		private int codeError;
		private Toast message;
		private HandlerPresence presence;
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "", "Iniciando", true, false);			
		}
		
		@Override
		protected UserRegistration doInBackground(String... params) {
			TAG = this.getClass().getName();
			String adjuster = params[0]; 
			String phoneNumber = params[1];
			String deviceId = params[2];
			Map<String, String> values = new LinkedHashMap<String, String>();
			values.put("NoAjustador", adjuster);
			values.put("Identificador", android.os.Build.ID);
			values.put("Descripcion", phoneNumber);
			values.put("IMEI", deviceId);
			values.put("RegistrationID", "");
			values.put("TipoMensaje", "XMPP");			
			Log.i(TAG,"Invoking the RS to validate user existence");			
			/* invoked to consume the response from RS about the authenticate user */
			registerService = new RegisterService(context.getResources().getString(R.string.nameMethodRegisterService),
							  context.getResources().getString(R.string.urlRegisterService), values, 
							  Integer.parseInt(context.getResources().getString(R.string.timeOutRegisterService)));
			Log.i(TAG, "Success consume RS");
				
			try {					 
				 String jsonData = (String) registerService.consume().toString(); 
				 ObjectMapper mapper = new ObjectMapper();
				 RegistrationResult result = mapper.readValue(jsonData, RegistrationResult.class);
				 userRegistration = (UserRegistration) result.getUserRegistration();					 
			} catch (Exception e) {
				 Log.e("EXCEPTION", e.toString());					 
				 codeError = Constants.ERROR_SERVICE_CONSUMPTION;	/* error code for the service consumption */
				 e.printStackTrace();					  
			}
			
			return userRegistration;

		}
		
		@Override
		protected void onPostExecute(UserRegistration userRegistration) {			
			progressDialog.dismiss();
			presence = new HandlerPresence(context);
			
			if (userRegistration != null && userRegistration.getError().getNo().equals("0")) {				
				Log.i(TAG, "User registered ... start home");
				Log.i(TAG, "start connection xmpp ...");
				MainActivity.clearNotification(Constants.ID_MAIN_NOTIFICATION);
				presence.showStatusNotification(XMPPConnectionService.STATUS_OFFLINE);		/* presence update to show in notification bar after authentication  */
				context.startActivity(new Intent(context,HomeActivity.class));
				context.startService(new Intent(context,XMPPConnectionService.class));
			} else if (codeError == Constants.ERROR_SERVICE_CONSUMPTION) {					/* show connection error message */
				message = Toast.makeText(context, R.string.serverError, Toast.LENGTH_SHORT);
				message.show();			
			} else {																		/* show user not registered message */				
				message = Toast.makeText(context, R.string.unregisteredAdjuster, Toast.LENGTH_SHORT);
				message.show();				
			}						
		}		
	}
}
