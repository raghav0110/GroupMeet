package com.example.lab6;


import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfirmActivity extends Activity {

	private String times[];
	private Button confirm;
	String event,user;
	private ArrayList<RadioButton> radioButton = new ArrayList<RadioButton>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_intersection);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.selectIntersection);
        Intent i = getIntent();
        
        String temp=i.getStringExtra("string");
        event= i.getStringExtra("event");
       SharedPreferences mPrefs;
        mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		 user = mPrefs.getString("UserName", null);
        times = temp.split(",");
        for (String restaurant : times ) {
            radioButton.add(new RadioButton(getBaseContext()));
            radioButton.get(radioButton.size()-1).setText(restaurant);
            radioGroup.addView(radioButton.get(radioButton.size()-1));
        }
        radioGroup.invalidate();
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				for(int i=0; i<radioButton.size();i++){
					if(radioButton.get(i).isChecked()){
						sendServer(user,event,times[i]);
					}
						
						
				}
				finish();
			}
			
		});
	}
	
	public void sendServer(String user, String event, String date){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost("http://data.cs.purdue.edu:12345/confirm.php");
    	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    	HttpResponse response;
    	 Log.d("GroupMeet", ":response");
    	try {
    		
 
    		
        	postParameters.add(new BasicNameValuePair("user", user));
        	postParameters.add(new BasicNameValuePair("date", date));
        	postParameters.add(new BasicNameValuePair("event", event));

        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	 response = httpclient.execute(httppost);
        	 HttpEntity entity = response.getEntity();
         	String result= EntityUtils.toString(entity);
        	 Log.d("GroupMeet", result + " :response");
			
	}catch(Exception e){
		 Log.d("GroupMeet",   " :catch");
	}
}
}