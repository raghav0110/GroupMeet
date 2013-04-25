package com.example.lab6;

import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.Toast;


public class CalendarView extends Activity {
	private TextView data;
	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	public DBAdapter dbadapter;
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
	    dbadapter = new DBAdapter(this);
	    dbadapter = dbadapter.open();
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
		    	data.setText(dbadapter.searchforConfirmedDB(dateresult));
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
