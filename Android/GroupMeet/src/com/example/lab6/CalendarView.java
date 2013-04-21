package com.example.lab6;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;


public class CalendarView extends Activity {
	private TextView data;
	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> items; // container to store some random calendar items
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.calendar);
	    month = Calendar.getInstance();
	    onNewIntent(getIntent());
	    
	    data = (TextView) findViewById(R.id.data);
	    data.setText("");
	    data.setVisibility(TextView.INVISIBLE);
	    items = new ArrayList<String>();
	    adapter = new CalendarAdapter(this, month);
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(adapter);
	    
	    handler = new Handler();
	    handler.post(calendarUpdater);
	    
	    TextView title  = (TextView) findViewById(R.id.title);
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
		    	data.setText("Event1\nEvent2\nEvent3\nEvent5");
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
