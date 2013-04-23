package com.example.lab6;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText text_username, text_email, text_passwd, text_pwdconfirm;
	Button createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text_username = (EditText)findViewById(R.id.edit_message);
		text_email = (EditText)findViewById(R.id.email_message);
		text_passwd = (EditText)findViewById(R.id.passwd_message);
		text_pwdconfirm = (EditText)findViewById(R.id.pwdconfirm_message);
		createAccount = (Button)findViewById(R.id.createAccount_button);
		createAccount.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) 
			{
				String username = text_username.getText().toString();
				String email = text_email.getText().toString();
				String pwd = text_passwd.getText().toString();
				String pwdconfirm = text_pwdconfirm.getText().toString();
				
				if(username.equals("")||email.equals("")||pwd.equals("")||pwdconfirm.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
				}
				if(email.indexOf('@')==-1)
				{
					Toast.makeText(getApplicationContext(), "Check the Email format", Toast.LENGTH_LONG).show();
					return;
				}
				if(!pwd.equals(pwdconfirm))
				{
					Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					//save to database here
					
					Toast.makeText(getApplicationContext(), "Account Successfully Created", Toast.LENGTH_LONG).show();
					Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
					startActivity(i);
				}
			}
				// TODO Auto-generated method stub
				
		});
		
		
    }
    protected void onDestroy(){
		super.onDestroy();
		
		//database connection close here
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
