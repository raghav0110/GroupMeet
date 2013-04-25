package com.example.lab6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

public class DatabaseFillerTask extends AsyncTask<Object, Object, Object> {

	private DBAdapter db;
	public DatabaseFillerTask(Context c){
		db = new DBAdapter(c);
		db = db.open();
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		String events[]=getFromServer();
		return null;
	}

	private String[]  getFromServer()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/readInvites.php";
    	String email = "user1@purdue.edu";
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		String events[]=null;
		
		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	
        	
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {

			JSONObject json_data = new JSONObject(result);
			JSONArray array;
			array = json_data.getJSONArray("events");
			events = new String[array.length()];
			for(int i = 0 ; i < array.length(); i++)
			{
				//System.out.println(array.getJSONObject(i).toString());
			    StringTokenizer stk = new StringTokenizer(array.getJSONObject(i).toString(), "\"");
			    for (int j = 0; j <= stk.countTokens()+1; j++) 
			    {
			    	
			    	if(j == stk.countTokens()+1)
			    	{
			    		events[i]=stk.nextToken();
			    		//db.insertEntry(events[i]);
			    	}
			    	else
			    		stk.nextToken();       
			    }
			   
			}
			
			//'events' array has the result received from the server
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return events;
		
	}
	
}
