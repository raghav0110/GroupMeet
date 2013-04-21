package com.groupmeet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

@SuppressWarnings("deprecation")
public class FacebookConnect extends Activity{
	Button facebookBT;
	private static String app_id = "321412014655127";
	
	private Facebook facebook;
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		facebookBT = (Button)findViewById(R.id.createAccount_button);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		facebook = new Facebook(app_id);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		facebookBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginToFacebook();
				
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void loginToFacebook()
	{
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		
		if(access_token != null)
		{
			facebook.setAccessToken(access_token);
		}
		if(expires != 0)
		{
			facebook.setAccessExpires(expires);
		}
		if(!facebook.isSessionValid())
		{
			facebook.authorize(this, new String[] {"email", "publish_stream"}, new DialogListener()
			{
				@Override
				public void onCancel()
				{
					
				}
				@Override
				public void onComplete(Bundle values)
				{
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires", facebook.getAccessExpires());
					editor.commit();
				}
				
				@Override
				public void onError(DialogError error)
				{
					
				}
				
				@Override
				public void onFacebookError(FacebookError fberror)
				{
					
				}
				
			});
			
		}
	}
}
