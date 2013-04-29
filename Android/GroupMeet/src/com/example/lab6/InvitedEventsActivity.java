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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class InvitedEventsActivity extends Activity{
	String[] events;
	private SharedPreferences mPrefs;
	private String email;
	ArrayList<String>blank = new ArrayList<String>();
	boolean isEvent = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invited_events);
		//GetData to populate events List
		mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		email = mPrefs.getString("UserName", null);
		
		events = getPendingEvents();
		//events.add("Event1");
		ListView l = (ListView)findViewById(R.id.invitedList);
		ArrayAdapter<String> adapter;
		try{
		 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);
		}catch(Exception e){
			isEvent = false;
			blank.add("No Events Available");
			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,blank);	
		}
		l.setAdapter(adapter);
		l.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(isEvent){
				SharedPreferences mPrefs;
				mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
				Intent i = new Intent(InvitedEventsActivity.this,HoursGridViewActivity.class);
				i.putExtra("email", mPrefs.getString("UserName", ""));
				i.putExtra("event", events[arg2]);
				i.putExtra("invites", "invites");
				startActivity(i);
				}
			}
			
		});
	}
	
	private String[] getPendingEvents()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/readInvites.php";
    	
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
			    		
			    		try{
			    			
			    		}catch(Exception e){
			    			
			    		}
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
