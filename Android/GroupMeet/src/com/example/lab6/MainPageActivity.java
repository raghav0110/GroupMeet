package com.example.lab6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainPageActivity extends Activity {

	private Button create, calendar;
	SharedPreferences mPrefs;
	DBAdapter dbadapter;
	String pendingDB;
	String confirmedDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);
		create = (Button) findViewById(R.id.create_meeting);
		calendar = (Button) findViewById(R.id.calendar);
		
		//mPrefs.getString("UserName", null); gives the user name.
		mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		
		//Communication with DB
		dbadapter = new DBAdapter(this);
		dbadapter = dbadapter.open();
		//dbadapter.db.execSQL("DROP TABLE IF EXISTS "+DBAdapter.DATABASE_TABLE2);
		//dbadapter.db.execSQL("DROP TABLE IF EXISTS "+DBAdapter.DATABASE_TABLE);
		pendingDB = DBAdapter.DATABASE_TABLE;
		confirmedDB = DBAdapter.DATABASE_TABLE2;
		
		dbadapter.insertEntry2("FUCK You","And you too");
		dbadapter.insertEntry("HAHA U LOSER");
		//dbadapter.removeEntry(2);
		//System.out.println(dbadapter.getEntry(confirmedDB, 1));
		//Cursor a = dbadapter.getAllEntries(confirmedDB);
		//System.out.println(a.getCount());
		//a.moveToLast();
		//System.out.println(a.getString(1));
		//System.out.println(dbadapter.getEntry(pendingDB, 2));
		
		setWidgets();
		// call GET method here
		//getFromServer();
		//getIntersections();
		
		
		final Context context=this;
	    LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v=inflater.inflate(R.layout.pending_meeting, null, true);



	    final ListView lv=(ListView) v.findViewById(R.id.listView1);

	    SlidingMenu menu=new SlidingMenu(this);
	    menu.setMode(SlidingMenu.LEFT);
	    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	    menu.setShadowWidth(15);
	    
	    menu.setFadeDegree(0.0f);
	    menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	    menu.setBehindWidth(250);
	    menu.setMenu(v);
	    menu.setOnOpenListener(new OnOpenListener() {

	        @Override
	        public void onOpen() {
	            MenuAdapter ma=new MenuAdapter(context);
	            lv.setAdapter(ma);
	        }
	    });

		/*
		setBehindContentView(R.layout.pending_meeting_list);
		ListView l = (ListView) findViewById(R.id.pending_list);
		String[] menu = {"Meeting1","Meeting2","Meeting3","Meeting4","Meeting5","Meeting6","Meeting7","Meeting8","Meeting8","Meeting9","Meeting10"};
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    R.layout.pending_meeting_list, R.id.item, menu);
	    l.setAdapter(adapter);
		
		getSlidingMenu().setBehindOffset(100);
		SlidingMenu m=new SlidingMenu(this);
		*/

	}
	
	private void getIntersections()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/intersection.php";
    	String email = "user1@purdue.edu";
    	String event = "Event1";
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		String intersections[];

		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	postParameters.add(new BasicNameValuePair("event", event));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	
        	if(result != null)
        	{
        		if(result.indexOf("Final") != -1)
        		{
        			System.out.println(result);
        			try {
						JSONObject json_data = new JSONObject(result);
						JSONArray array;
						array=json_data.getJSONArray("times");
						intersections = new String[array.length()];
						for(int i = 0 ; i < array.length(); i++)
						{
							StringTokenizer stk = new StringTokenizer(array.getJSONObject(i).toString(), "\"");
						    for (int j = 0; j <= stk.countTokens()+1; j++) 
						    {
						    	
						    	if(j == stk.countTokens()+1)
						    	{
						    		intersections[i]=stk.nextToken();
						    		System.out.println(intersections[i]);
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
	@Override
	public void onDestroy()
	{
		dbadapter.close();
		super.onDestroy();
	}
	
}
