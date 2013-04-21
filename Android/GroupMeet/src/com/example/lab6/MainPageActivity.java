package com.example.lab6;

import java.util.Calendar;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
