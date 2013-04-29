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

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainPageActivity extends Activity {

	GroupMeet appState = ((GroupMeet)this.getApplication());
	ArrayList<String>intersections = new ArrayList<String>();

	private Button create, calendar, invitedEvents;
	ArrayList<String> events = new ArrayList<String>();
	SharedPreferences mPrefs;
	DBAdapter dbadapter;
	String[] pendingEvents ;
	String pendingDB;
	String confirmedDB;
	String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);
		create = (Button) findViewById(R.id.create_meeting);
		calendar = (Button) findViewById(R.id.calendar);
		
		
		mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		email = mPrefs.getString("UserName", null);
		Toast.makeText(MainPageActivity.this, "Welcome " + email, Toast.LENGTH_SHORT).show();
	//	appState.myEvents = mPrefs.getString("myEvents", null);
		//DatabaseFillerTask d = new DatabaseFillerTask(this,mPrefs.getString("UserName", ""));
		//d.execute();
	//	getPendingEvents();
		//Communication with DB
		//dbadapter = new DBAdapter(this);
		//dbadapter = dbadapter.open();
		//dbadapter.db.execSQL("DROP TABLE IF EXISTS "+DBAdapter.DATABASE_TABLE2);
		//dbadapter.db.execSQL("DROP TABLE IF EXISTS "+DBAdapter.DATABASE_TABLE);
	//	pendingDB = DBAdapter.DATABASE_TABLE;
	//	confirmedDB = DBAdapter.DATABASE_TABLE2;
		
		//dbadapter.insertEntry2("FUCK_PARTY","");
		//dbadapter.insertEntry("HAHA U LOSER");
		//dbadapter.removeEntry(pendingDB, 2);
		//dbadapter.removeEntry(confirmedDB,2);
		//System.out.println(dbadapter.getEntry(confirmedDB, 1));
		//Cursor a = dbadapter.getAllEntries(confirmedDB);
		//System.out.println(a.getCount());
		//a.moveToLast();
		//System.out.println(a.getString(1));
		//System.out.println(dbadapter.getEntry(pendingDB, 2));
		
		// [] events= readEvents();//getPendingEvents();
		
	//	String op="";
	//	for(String t: events)
	//		op+=t + ",";
		
		try{
			events = readEvents();
		Log.d("GroupMeet", events.size() + " is length");
		}catch(Exception e){
			
		}
		
		setWidgets();
		final Context context=this;
	    LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v=inflater.inflate(R.layout.pending_meeting, null, true);

	    

	    final ListView lv=(ListView) v.findViewById(R.id.listView1);

	    SlidingMenu menu=new SlidingMenu(this);
	    menu.setMode(SlidingMenu.LEFT);
	    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	    menu.setShadowWidth(5);
	    menu.setFadeDegree(0.0f);
	    menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	    menu.setBehindWidth(100);
	    menu.setMenu(v);
	    menu.setOnOpenListener(new OnOpenListener() {

	        @Override
	        public void onOpen() {
	        	events.clear();
	        	events = readEvents();
	        	if(events.size()==0){
	        		events.add("No Events");
	        	}
	            MenuAdapter ma=new MenuAdapter(context,events);
	            lv.setAdapter(ma);
	        }
	    });
	    lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			if(events.get(0).equals("No Events")){
				
			}
			else{
				intersections.clear();
				getIntersections(events.get(arg2));
				//Toast.makeText("", text, duration)
				if(intersections.size() == 0)
					Toast.makeText(MainPageActivity.this, "Times are not Available", Toast.LENGTH_LONG).show();
				else{
					Intent i = new Intent(MainPageActivity.this,ConfirmActivity.class);
					String op="";
					for(String s: intersections)
						op+=s+",";
					i.putExtra("string", op);
					i.putExtra("event", events.get(arg2));
				//	events.remove(arg2);
					startActivity(i);
				}
			}
			}
	    	
	    });

	}
		
		
		// call GET method here
	//	getPendingEvents();
		//getIntersections();
		


	
	private void getIntersections(String event)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/intersection.php";
    	//event = "Group_Meeting";
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		

		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	postParameters.add(new BasicNameValuePair("event", event));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	Log.d("GroupMeet",response + " intersection");
        	
        	if(result != null)
        	{
        		if(result.indexOf("Final") != -1)
        		{
        			System.out.println(result);
        			try {
						JSONObject json_data = new JSONObject(result);
						JSONArray array;
						array=json_data.getJSONArray("times");
					//	intersections = new String[array.length()];
						for(int i = 0 ; i < array.length(); i++)
						{
							StringTokenizer stk = new StringTokenizer(array.getJSONObject(i).toString(), "\"");
						    for (int j = 0; j <= stk.countTokens()+1; j++) 
						    {
						    	
						    	if(j == stk.countTokens()+1)
						    	{
						    		intersections.add(stk.nextToken());
						    		//System.out.println(intersections[i]);
						    	}
						    	else
						    		stk.nextToken();       
						    }
						   
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        		}
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
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
	
	
	private ArrayList<String> readEvents()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/readEvents.php";
    	
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		//String events[]=null;
		
		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	Log.d("GroupMeet", result + " is the result strng");
        	
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {	

			JSONObject json_data = new JSONObject(result);
			JSONArray array;
			int success = json_data.getInt("PASSED");
			Log.d("GroupMeet", success + " : Success");
			array = json_data.getJSONArray("events");
			//events = new String[array.length()];
			for(int i = 0 ; i < array.length(); i++)
			{
				//System.out.println(array.getJSONObject(i).toString());
			    StringTokenizer stk = new StringTokenizer(array.getJSONObject(i).toString(), "\"");
			    for (int j = 0; j <= stk.countTokens()+1; j++) 
			    {
			    	
			    	if(j == stk.countTokens()+1)
			    	{	
						//Log.d("GroupMeet", stk.nextToken() + " : token");
			    		events.add(stk.nextToken());
			    		
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
	
	
	/*private String[] readEvents()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/readEvents.php";
    	
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
			
			
			events = new String[1];
			events[0] = array.toString();
			return events;
			/*
			    for (int j = 0; j < array.length(); j++) 
			    {
			    	JSONObject o = array.getJSONObject(j);
			    	events[j] = o.getString("EventName");
			    	
			    }
			
			//'events' array has the result received from the server
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return events;
		
	}*/
	
	private void setWidgets(){
		invitedEvents = (Button) findViewById(R.id.invitedEvents);
		invitedEvents.setOnClickListener(new OnClickListener(){

			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPageActivity.this,InvitedEventsActivity.class);
				startActivity(i);
			}
			
		});
		create.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPageActivity.this,CreateMeetingActivity.class);
				startActivity(i);
			}
			
		});
		
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
	@Override
	public void onDestroy()
	{
		dbadapter.close();
		super.onDestroy();
	}
	
	
	
}
