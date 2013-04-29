package com.example.lab6;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class CalendarView extends Activity {
	private TextView data;
	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	//public DBAdapter dbadapter;
	public ArrayList<String> items; // container to store some random calendar items
	private SharedPreferences mPrefs;
	private String email;
	
	HashMap<String,String> map = new HashMap<String,String>();
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.calendar);
	    month = Calendar.getInstance();
	    onNewIntent(getIntent());
	    
	    
	    mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		email = mPrefs.getString("UserName", null);
	    getConfirms(email);
	    
	    
	    data = (TextView) findViewById(R.id.data);
	    data.setText("");
	    data.setVisibility(TextView.INVISIBLE);
	    items = new ArrayList<String>();
	    adapter = new CalendarAdapter(this, month);
	  //  dbadapter = new DBAdapter(this);
	  //  dbadapter = dbadapter.open();
	    //dbadapter.insertEntry2("Raggy's fuck party", "2013-04-24 23:00:00");
	    //dbadapter.insertEntry2("Clyde's fuck party", "2013-04-24 24:00:00");
	    //dbadapter.insertEntry2("Eric's fuck party", "2013-04-24 20:00:00");
	    
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(adapter);
	    
	    handler = new Handler();
	    handler.post(calendarUpdater);
	    
	    final TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMinimum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
				}
				refreshCalendar();
				
			}
		});
	    
		gridview.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	data.setVisibility(TextView.VISIBLE);
		    	TextView t = (TextView)v.findViewById(R.id.date);
		    	String mymonth = (String)android.text.format.DateFormat.format("MM", month);
		    	String myyear = (String) android.text.format.DateFormat.format("yyyy", month);
		    	String mydate = t.getText().toString();
		    	if(mydate.length() == 1)
		    	{
		    		mydate = "0"+mydate;
		    	}
		    	String dateresult = myyear+"-"+mymonth+"-"+mydate;
		    	Iterator it = map.entrySet().iterator();
		    	String op = "";
		        while (it.hasNext()) {
		            Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
		            if(pairs.getValue().contains(dateresult))
		            	op+=pairs.getKey() + "-" + pairs.getValue()+"\n";
		            //it.remove(); // avoids a ConcurrentModificationException
		        }
		    	
		    	
		    	
		    	
		    	data.setText(op);
		    	//dbadapter.searchforConfirmedDB(dateresult);
		    	//Toast.makeText(getApplicationContext(), dbadapter.searchforConfirmedDB(dateresult), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}
	
	
	public void getConfirms(String email){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/readConfirms.php";
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		

		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	Log.d("GroupMeet",result + " : result");
        	
        	
        	if(result != null)
        	{
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
						    		//Log.d("GroupMeet",stk.nextToken() + " : result122");
						    		String temp[] = stk.nextToken().split(",");
						    		//dbadapter.insertConfirmed(temp[0], temp[1]);
						    		map.put(temp[0], temp[1]);
						    		//stk.nextToken();
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
		}catch(Exception e){
			
		}
	}
	
	public void onNewIntent(Intent intent) {
		String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
	}
	
	public Runnable calendarUpdater = new Runnable() {
		
		@Override
		public void run() {
			
			adapter.notifyDataSetChanged();
		}
	};
}
