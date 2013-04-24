package com.example.lab6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPageActivity extends Activity {

	private Button create, calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);
		create = (Button) findViewById(R.id.create_meeting);
		calendar = (Button) findViewById(R.id.calendar);
		setWidgets();
		// call GET method here
		getFromServer();
		

	}

	private void getFromServer()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/readInvites.php";
    	String email = "fuck@purdue.edu";
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		String events[];
		
		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	
        	if(result != null)
			{
				//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        		//System.out.println(result);
			}
			
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
		
		
		
		
	}
	private void setWidgets(){
		create.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPageActivity.this,CreateMeetingActivity.class);
				startActivity(i);
			}
			
		});
		calendar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPageActivity.this,CalendarView.class);
				Calendar calendar = Calendar.getInstance();
				int date = calendar.get(Calendar.DAY_OF_MONTH);
				int month = calendar.get(Calendar.MONTH);
				int year = calendar.get(Calendar.YEAR);
				
		       
				
				i.putExtra("date", year+"-"+month+"-"+date);
				startActivity(i);
			}
			
		});
	}
}
