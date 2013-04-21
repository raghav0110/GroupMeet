package com.example.lab6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public class CreateMeetingActivity extends Activity {

	private EditText meetingName;
	private MultiAutoCompleteTextView friendInvites;
	private Button createMeeting;
	private String[] COUNTRIES={"Raggy","Romain","Radical","RagMode"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createmeeting);
		setWidgets();

	}
	private void setWidgets(){
		
		meetingName = (EditText) findViewById(R.id.meeting_name);
		friendInvites = (MultiAutoCompleteTextView) findViewById(R.id.invites);

		friendInvites.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES));
		friendInvites.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		createMeeting = (Button) findViewById(R.id.create_meeting_button);		
		createMeeting.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				String mName = meetingName.getText().toString();
				String invites = friendInvites.getText().toString();
				if(mName.length() == 0 && invites.length() == 0)
					Toast.makeText(CreateMeetingActivity.this, "Please Fill Up All Empty Fields", Toast.LENGTH_SHORT).show();
				else{
					
					Intent i = new Intent(CreateMeetingActivity.this, HoursGridViewActivity.class);
					startActivity(i);
				}
				
			}
			
		});
	}
}
