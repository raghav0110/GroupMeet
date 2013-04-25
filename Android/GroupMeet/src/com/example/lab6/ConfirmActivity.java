package com.example.lab6;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfirmActivity extends Activity {

	private String restaurants[] = new String[]{"2013-04-22 12:00:00","2013-04-22 15:00:00","2013-04-22 17:00:00","2013-04-22 10:00:00"};
	private Button confirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_intersection);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.selectIntersection);
        for (String restaurant : restaurants ) {
            RadioButton radioButton = new RadioButton(getBaseContext());
            radioButton.setText(restaurant);
            radioGroup.addView(radioButton);
        }
        radioGroup.invalidate();
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
	}
	
}
