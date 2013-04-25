package com.groupmeet;


import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class LoginActivity extends Activity {
	
	EditText text_username, text_email, text_passwd, text_pwdconfirm;
	Button createAccount, fbButton;
	LoginButton loginButton;
    
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		text_username = (EditText)findViewById(R.id.username);
		text_email = (EditText)findViewById(R.id.email);
		text_passwd = (EditText)findViewById(R.id.passwd);
		text_pwdconfirm = (EditText)findViewById(R.id.pwdconfirm);
		createAccount = (Button)findViewById(R.id.create_button);
		loginButton = (LoginButton)findViewById(R.id.login_button);
		Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("email"));
		Session.getActiveSession().requestNewReadPermissions(newPermissionsRequest);
		loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
			
			@Override
			public void onUserInfoFetched(GraphUser user) {
				// TODO Auto-generated method stub
				if(user!=null)
				{
					Toast.makeText(getApplicationContext(), user.getProperty("email").toString(), Toast.LENGTH_LONG).show();
					
					//save user email to database and move to next activity
				}
			}
		});
	
		//General create account function
		createAccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
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
					
					Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_LONG).show();
					
					// move to next activity here
					
					
				}
				
				
				
			}
		});
		
		/*//facebook login
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				// TODO Auto-generated method stub
				if(session.isOpened())
				{
					
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
						
						@Override
						public void onCompleted(GraphUser user, Response response) {
							// TODO Auto-generated method stub
							if(user != null)
							{
								GraphObject graphObject = response.getGraphObject();
								Toast.makeText(getApplicationContext(), graphObject.getProperty("email").toString(), Toast.LENGTH_LONG).show();
								
							}
						}

					
					});
					
					
				}
				
			}
		})*/;
	}
	 /*protected void onDestroy(){
			super.onDestroy();
			
			//database connection close here
	}*/

	
	 protected void onDestroy(){
			super.onDestroy();
			
			//database connection close here
	}
	 
	 
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
