package com.example.lab6;

import java.util.Calendar;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


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
