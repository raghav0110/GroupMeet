package com.example.lab6;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Session;
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
		final Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("email"));
		
		loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {

			@Override
			public void onUserInfoFetched(GraphUser user) {
				// TODO Auto-generated method stub
				if(user!=null)
				{
					Session.getActiveSession().requestNewReadPermissions(newPermissionsRequest);
					Toast.makeText(getApplicationContext(), "Facebook account successfully logged in", Toast.LENGTH_LONG).show();
					
					sendToServer(user.getProperty("email").toString(), "facebook");
					Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
					startActivity(i);
					//finish();
					
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
					
					sendToServer(email,pwd);
					
					
					Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
					startActivity(i);
					finish();
				

				}



			}
		});

		
	}
	

    
    protected void sendToServer(String email, String password)
    {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost("http://sslab00.cs.purdue.edu:1234/insertUser.php");
    	
    
    	try {
    		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("email", email));
        	postParameters.add(new BasicNameValuePair("password", password));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	
        	HttpResponse response = httpclient.execute(httppost);
       
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

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