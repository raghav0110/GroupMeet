package com.example.lab6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class HoursGridViewActivity extends Activity {

	private GridView hoursView;
	private static HashMap<String, boolean[]> dateMap = new HashMap<String, boolean[]>();
	private int monthcounter,weekcounter =0,datecounter,yearcounter;
	private Button submitTimes;
	//public boolean isSelected[] = new boolean[133];
	ArrayList<boolean[]> isSelected = new ArrayList<boolean[]>();
	String dates="";
	ArrayList<View []> views = new ArrayList<View []>();
	View vi;
	GroupMeet gm ;
	boolean prev = false;
	Random r = new Random();
	int tracker =1;
	HoursAdapter adapter;
	Stack<DateObject> s = new Stack<DateObject>();
	private TextView weekText,next,previous;
	ArrayList<boolean[]> selectedTimes = new ArrayList<boolean[]>();
	private String data[] = {"M", "T","W","TH","F","SAT","SUN",
							"6 AM","6 AM","6 AM","6 AM","6 AM","6 AM","6 AM",
							"7 AM","7 AM","7 AM","7 AM","7 AM","7 AM","7 AM",
							"8 AM","8 AM","8 AM","8 AM","8 AM","8 AM","8 AM",
							"9 AM","9 AM","9 AM","9 AM","9 AM","9 AM","9 AM",
							"10 AM","10 AM","10 AM","10 AM","10 AM","10 AM","10 AM",
							"11 AM","11 AM","11 AM","11 AM","11 AM","11 AM","11 AM",
							"12 PM","12 PM","12 PM","12 PM","12 PM","12 PM","12 PM",
							"1 PM","1 PM","1 PM","1 PM","1 PM","1 PM","1 PM",
							"2 PM","2 PM","2 PM","2 PM","2 PM","2 PM","2 PM",
							"3 PM","3 PM","3 PM","3 PM","3 PM","3 PM","3 PM",
							"4 PM","4 PM","4 PM","4 PM","4 PM","4 PM","4 PM",
							"5 PM","5 PM","5 PM","5 PM","5 PM","5 PM","5 PM",
							"6 PM","6 PM","6 PM","6 PM","6 PM","6 PM","6 PM",
							"7 PM","7 PM","7 PM","7 PM","7 PM","7 PM","7 PM",
							"8 PM","8 PM","8 PM","8 PM","8 PM","8 PM","8 PM",
							"9 PM","9 PM","9 PM","9 PM","9 PM","9 PM","9 PM",
							"10 PM","10 PM","10 PM","10 PM","10 PM","10 PM","10 PM",
							"11 PM","11 PM","11 PM","11 PM","11 PM","11 PM","11 PM"};
	
	private String times[] = {"6:00:00", "7:00:00", "8:00:00", "9:00:00", "10:00:00", "11:00:00", "12:00:00","13:00:00","14:00:00","15:00:00","16:00:00","17:00:00","18:00:00","19:00:00","20:00:00","21:00:00","22:00:00","23:00:00"};
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hoursview);
		//myActivity .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		boolean t [] = new boolean[133];
		for(int i=0; i<133;i++)
			t[i]=false;
		isSelected.add(t);
		
			
		Calendar cal = Calendar.getInstance();
		monthcounter = cal.get(Calendar.MONTH)+1;
		yearcounter = cal.get(Calendar.YEAR);
		datecounter = (cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK)) +2;
		setWidgets();
		
		
	}
	private void setWidgets(){
		hoursView = (GridView) findViewById(R.id.hours_gridView);
		weekText = (TextView) findViewById(R.id.week_text);
		setWeekText();
		
		previous = (TextView) findViewById(R.id.previous_week);
		previous.setClickable(false);
		previous.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tracker !=1){
					weekcounter = -7;
					setWeekText();
					//vi.setBackgroundColor(Color.GRAY);
					tracker--;
					if(isSelected.size() < tracker){
						boolean t [] = new boolean[133];
						for(int i=0; i<133;i++)
							t[i]=false;
						isSelected.add(t);
						Log.d("GroupMeet", "Added array");
					}
					
					create();
					hoursView.setAdapter(adapter);
					Log.d("GroupMeet", isSelected.size()+","+tracker);
					prev = false;
				}
			}
			
		});
		 adapter = new HoursAdapter(this,isSelected.get(tracker-1),data,gm);
		next = (TextView) findViewById(R.id.next_week);
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Toast.makeText(HoursGridViewActivity.this, "Pressed", Toast.LENGTH_SHORT).show();
				previous.setClickable(true);
				weekcounter = 7;
				setWeekText();
				//vi.setBackgroundColor(Color.GRAY);
				tracker++;
				if(isSelected.size() < tracker){
					boolean t [] = new boolean[133];
					for(int i=0; i<133;i++)
						t[i]=false;
					isSelected.add(t);
					Log.d("GroupMeet", "Added array");
				}
				
				create();
				//adapter.notifyDataSetChanged();
				//hoursView.invalidateViews();
				hoursView.setAdapter(adapter);
				Log.d("GroupMeet", isSelected.size()+","+tracker);
				prev = true;
				
			}
			
		});
		
		
 
		
		hoursView.setAdapter(adapter);
		
		//Toast.makeText(HoursGridViewActivity.this,"", Toast.LENGTH_LONG).show();
		hoursView.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub

				vi = v;
				//Toast.makeText(HoursGridViewActivity.this, "" + position, Toast.LENGTH_LONG).show();
				if(position >6){
					String month = monthcounter+"";
					if(month.length() == 1)
						month = 0 + month;

					
					if( isSelected.get(tracker-1)[position]){
						v.setBackgroundColor(Color.WHITE);
						boolean [] temp = isSelected.get(tracker-1);
						temp[position] = false;
						//isSelected.add(tracker-1,temp);
						isSelected.set(tracker-1, temp);
						boolean [] b =dateMap.get(month +"/"+datecounter + "/"+yearcounter);
						b[position%7] = false;
						dateMap.put(month +"/"+datecounter + "/"+yearcounter, b);
						
						
					}
					else{
						v.setBackgroundColor(Color.GREEN);
						boolean temp[] =isSelected.get(tracker-1);temp[position] = true;
						//isSelected.add(tracker-1,temp);
						isSelected.set(tracker-1, temp);
					
					if(dateMap.containsKey(month +"/"+datecounter + "/"+yearcounter)){
						boolean [] b =dateMap.get(month +"/"+datecounter + "/"+yearcounter);
						b[position%7] = true;
						dateMap.put(month +"/"+datecounter + "/"+yearcounter, b);
					}else{
						boolean b[] = new boolean[7];
						for(int i=0; i<7;i++)
							b[i]=false;
						b[position%7] = true;
						dateMap.put(month +"/"+datecounter + "/"+yearcounter, b);
					}
					}
					
				}
			}
		});
		
		submitTimes = (Button) findViewById(R.id.submit_times);
		submitTimes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				dates = getDates();
				dates=dates.substring(0,dates.length()-1);
				Toast.makeText(HoursGridViewActivity.this, dates, Toast.LENGTH_LONG).show();
				
				sendToServer("Clydes_Vasectomy"+r.nextInt(100000),"byrdc@purdue.edu","user1@purdue.edu,user2@purdue.edu");
				
				Intent i = new Intent(HoursGridViewActivity.this, MainPageActivity.class);

				startActivity(i);
			}
			
		});
		
		
	}
	
	private void setWeekText(){
		Calendar cal = Calendar.getInstance();
		//int month = cal.get(Calendar.MONTH);
		String m ;
		
		if(weekcounter == -7){
			if(prev)
			s.pop();
			DateObject d=s.pop();
			weekText.setText(d.text);
			monthcounter = d.month;
			datecounter = d.date;
			return ;
		}
		
		int month = monthcounter;
		if(month == 1)
			m = "January";
		else if(month == 2)
			m = "February";
		else if(month == 3)
			m = "March";
		else if(month == 4)
			m = "April";
		else if(month == 5)
			m = "May";
		else if(month == 6)
			m = "June";
		else if(month == 7)
			m = "July";
		else if(month == 8)
			m = "August";
		else if(month == 9)
			m = "September";
		else if(month == 10)
			m = "October";
		else if(month == 11)
			m = "November";
		else
			m = "December";
	
		
		//int date =  (cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK)) +2 ;
		int date = datecounter;
		
		//Log.d("GroupMeet","Month: " + month + " date: " + date + " weekcounter = " + weekcounter );
		if((month <= 7 && month % 2 == 1) || (month >=8 && month%2 == 0)){//31 days
			if(date+weekcounter > 31){
				date = (date+weekcounter)%31;
				weekcounter = 0;
				monthcounter++;
			}
			else 
				date = date + weekcounter;
		}
		else if(month == 2){
			if(date + weekcounter > 28){
				date = (date+weekcounter)%28;
				weekcounter =0;
				monthcounter++;

			}
			else
				date+=weekcounter;
		}
		else{
			if(date + weekcounter > 30){
				date = (date + weekcounter)%30;
				weekcounter =0;
				monthcounter++;
			}
			else
				date+=weekcounter;
		}
		if(monthcounter > 12){
			monthcounter = 1;
			m = "January";
		}
		
		month = monthcounter;
		if(month == 1)
			m = "January";
		else if(month == 2)
			m = "February";
		else if(month == 3)
			m = "March";
		else if(month == 4)
			m = "April";
		else if(month == 5)
			m = "May";
		else if(month == 6)
			m = "June";
		else if(month == 7)
			m = "July";
		else if(month == 8)
			m = "August";
		else if(month == 9)
			m = "September";
		else if(month == 10)
			m = "October";
		else if(month == 11)
			m = "November";
		else
			m = "December";
					
		datecounter = date;
		weekcounter = 0;
		
		Log.d("GroupMeet","Month: " + month + " date: " + date + " weekcounter = " + weekcounter );
		weekText.setText("Week of " + m + " " + datecounter);
		DateObject d = new DateObject("Week of " + m + " " + datecounter,monthcounter,datecounter);
		s.push(d);
	}
	public void create(){
		adapter = new HoursAdapter(this,isSelected.get(tracker-1),data,gm);
	}
	
	public String getDates(){
		
		String dates="";
		Calendar cal = Calendar.getInstance();
	    int date;
		 int currDay  =  cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 2;// getting the current day	     
	     int year = cal.get(Calendar.YEAR);
	     int month = cal.get(Calendar.MONTH)+1;
	     int counter =0;
			int time;
			int tmonth=month,tyear=year;
			for(int i=0; i<isSelected.size();i++){
				boolean temp[] = isSelected.get(i);
				for(int j=0; j<133;j++){
					
					
						tmonth = month;
						tyear = year;
					
					if(temp[j]){
						counter = j%7;
						time = (j/7)-1;
						
						Calendar mycal = new GregorianCalendar(year, month-1, 1);
						
						// Get the number of days in that month
						int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
						if(daysInMonth < currDay + counter){
							date = (currDay+counter)%daysInMonth;
							if(tmonth == 12){
								tmonth =0;
								tyear++;
							}
							tmonth++;
						}
						else{
							date= currDay +counter;
						}
						String mString,dString;
						if(month <10)
							mString = "0"+tmonth;
						else
							mString = tmonth+"";
						if(date < 10)
							dString = "0" + date;
						else
							dString = date + "";
						
						dates+= tyear +"-"+mString +"-"+dString+" "+times[time]+",";
					
					}
						
				}
				Calendar mycal = new GregorianCalendar(year, month-1, 1);
				int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				if(currDay+7 > daysInMonth){
					currDay= (currDay+7)%daysInMonth;
					if(month == 12){
						month=0;
						year++;
					}
					month++;
				}
				else
					currDay+=7;
					
			}
		
		
		
		return dates;
	}
	
	
	protected void sendToServer(String eventName, String userName, String invites)
    {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost("http://data.cs.purdue.edu:12345/insertEvents.php");
    	
    
    	try {
    		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("owner", userName));
        	postParameters.add(new BasicNameValuePair("event", eventName));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	HttpResponse response = httpclient.execute(httppost);
			Log.d("GroupMeet", EntityUtils.toString(response.getEntity()));

        	   	
			 httpclient = new DefaultHttpClient();
	    	 httppost = new HttpPost("http://data.cs.purdue.edu:12345/insertInvites.php");
        	if(!invites.contains(",")){
        		postParameters.clear();
        		postParameters.add(new BasicNameValuePair("invitee", invites));
            	postParameters.add(new BasicNameValuePair("event", eventName));
            	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
            	response = httpclient.execute(httppost);
            	Log.d("GroupMeet", EntityUtils.toString(response.getEntity()));
        	}
        	else{
        	String users[] = invites.split(",");
        	
        	for(int i=0; i<users.length;i++){
        		Log.d("GroupMeet", "The email is" + users[i]);
        		postParameters.clear();
        		postParameters.add(new BasicNameValuePair("invitee", users[i]));
            	postParameters.add(new BasicNameValuePair("event", eventName));
            	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
            	response = httpclient.execute(httppost);
            	Log.d("GroupMeet", EntityUtils.toString(response.getEntity()));
        	}
        	}
        	 httpclient = new DefaultHttpClient();
	    	 httppost = new HttpPost("http://data.cs.purdue.edu:12345/insertTimes.php");
        	if(!dates.contains(",")){
        		postParameters.clear();
        		postParameters.add(new BasicNameValuePair("user", userName));
            	postParameters.add(new BasicNameValuePair("event", eventName));
            	postParameters.add(new BasicNameValuePair("time", dates));

            	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
            	response = httpclient.execute(httppost);
            	Log.d("GroupMeet", EntityUtils.toString(response.getEntity()));
        	}else{
        		String times[] = dates.split(",");
            	
            	for(int i=0; i<times.length;i++){
            		postParameters.clear();
            		postParameters.add(new BasicNameValuePair("user", userName));
                	postParameters.add(new BasicNameValuePair("event", eventName));
                	postParameters.add(new BasicNameValuePair("time", times[i]));
                	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                	response = httpclient.execute(httppost);
                	Log.d("GroupMeet", EntityUtils.toString(response.getEntity()));
            	}
        	}
       
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("GroupMeet", e.getMessage()+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("GroupMeet", e.getMessage()+"");

		}
    	
    }
	
}
