package com.example.geosii.consumeRS;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RegisterService {
	
	private String methodName;
	private String url;
	private Map<String,String> params;
	private int timeOut;
	private String TAG;
	
	/**
	 * Constructor class
	 * 
	 * @param methodName method name that will be invoked on the service
	 * @param url an absolute URL giving the base location of the service
	 * @param params the values passed to the service«s url
	 * @param timeOut defined time to interrupt the call to service
	 */
	
	public RegisterService(String methodName, String url, Map<String, String> params, int timeOut ){
		
		this.methodName = methodName;
		this.url = url;
		this.params = params;
		this.timeOut = timeOut;
		TAG = getClass().getName();
		
	}
	
	/**
	 * Method that uses the RS for the user verification through 
	 * the parameters passed
	 * 
	 * @return a object of response from RS
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public Object consume() throws ClientProtocolException, IOException{
		
		Object response = null;
		String key;
		

		HttpParams httpParams = new BasicHttpParams();
	    Iterator<Entry<String,String>> iteracion = params.entrySet().iterator();
        String url_rest = url + "?method=" + methodName;

        while(iteracion.hasNext()){
	        	
        	Map.Entry<String, String> ob = (Map.Entry<String, String>)iteracion.next();
        	key = ob.getKey();
        	key = key.replaceAll(" ", "");
	        	
        	if(!key.equalsIgnoreCase("WSUsuario") && !key.equalsIgnoreCase("WSPassword") && !key.equalsIgnoreCase("JSONUbicacion")){

        		String text;
        		String paramss;
	        		
        		if(ob.getKey().equals("Texto")){
	        			
        			text = URLEncoder.encode(ob.getValue(),"ISO-8859-2");
        			paramss = "&"+ob.getKey()+"="+text;
	        			
        		}else{
	        			
        			paramss = "&"+ob.getKey()+"="+ob.getValue();
	        			
        		}
	        		
        		url_rest = url_rest + paramss;
	        				
        	}
	        	
        }

        HttpGet request = new HttpGet(url_rest);
        HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
        HttpConnectionParams.setSoTimeout(httpParams, timeOut);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpResponse my_response = httpClient.execute(request);
		response = EntityUtils.toString(my_response.getEntity());
		
		Log.i(TAG, "Response: " + response);
		return response;
		
	}

}
