package com.example.lab6;

import java.util.Calendar;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
	private int monthcounter,weekcounter =0,datecounter;
	private Button submitTimes;
	public boolean isSelected[] = new boolean[133];
	
	private TextView weekText,next,previous;
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
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hoursview);
		//myActivity .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		for(int i=0; i<isSelected.length; i++)
			isSelected[i]=false;
		
			
		Calendar cal = Calendar.getInstance();
		monthcounter = cal.get(Calendar.MONTH)+1;
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

			}
			
		});
		
		next = (TextView) findViewById(R.id.next_week);
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HoursGridViewActivity.this, "Pressed", Toast.LENGTH_SHORT).show();
				previous.setClickable(true);
				weekcounter = 7;
				setWeekText();
			}
			
		});
		
		HoursAdapter adapter = new HoursAdapter(this,isSelected,data);
 
		hoursView.setAdapter(adapter);
		hoursView.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(HoursGridViewActivity.this, "" + position, Toast.LENGTH_LONG).show();
				if(position >6){
					v.setBackgroundColor(Color.GREEN);
					isSelected[position] = true;
				}
			}
		});
		
		submitTimes = (Button) findViewById(R.id.submit_times);
		submitTimes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HoursGridViewActivity.this, MainPageActivity.class);
				startActivity(i);
			}
			
		});
		
		
	}
	
	private void setWeekText(){
		Calendar cal = Calendar.getInstance();
		//int month = cal.get(Calendar.MONTH);
		String m ;
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
	}
}
