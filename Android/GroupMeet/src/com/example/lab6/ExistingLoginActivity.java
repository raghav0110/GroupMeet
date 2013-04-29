package com.example.lab6;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExistingLoginActivity extends Activity{

	private EditText text_email;
	private EditText text_passwd;
	private Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.existing_login);
		text_email = (EditText)findViewById(R.id.existing_email);
		text_passwd = (EditText)findViewById(R.id.exisitng_passwd);
		b = (Button)findViewById(R.id.existing_login_button);
		b.setOnClickListener(new OnClickListener(){

	

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(confirm(text_email.getText().toString(),text_passwd.getText().toString())){
			     SharedPreferences mPrefs;
				mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
				SharedPreferences.Editor editor = mPrefs.edit();
				editor.putString("UserName", text_email.getText().toString());
				editor.putString("PassWord", text_passwd.getText().toString());
				editor.commit();
			
				Intent i = new Intent(ExistingLoginActivity.this,MainPageActivity.class);
				startActivity(i);
				finish();
			}else{
				Toast.makeText(ExistingLoginActivity.this, "Invalid user password", Toast.LENGTH_SHORT).show();
			}
		}
		});
	
		
		
	}
	
	public boolean confirm(String email,String pwd){
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	String url = "http://data.cs.purdue.edu:12345/isUser.php";
    	//event = "Group_Meeting";
    	HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		HttpResponse response;
		

		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("user", email));
        	postParameters.add(new BasicNameValuePair("password", pwd));
        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        	response = httpclient.execute(httppost);
        	HttpEntity entity = response.getEntity();
        	result = EntityUtils.toString(entity);
        	if(result != null && result.contains("\"PASSED\":1"))
        		return true;
        	else 
        		return false;
		}catch(Exception e){
			return false;
		}
	}
	
}
