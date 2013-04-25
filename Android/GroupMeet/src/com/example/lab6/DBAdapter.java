package com.example.lab6;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	static final String DATABASE_NAME = "groupMeet.db";
	static final String DATABASE_TABLE = "Pending_Meetings";
	static final String DATABASE_TABLE2= "Confirmed_Meetings";
	static final String DATABASE_TABLE3= "Invited_Meetings";
	static final String KEY_EVENT = "Event_names";
	static final String KEY_ANSWERED = "Answered";
	
	static final String KEY_ID = "id";
	static final String CONFIRMED_EVENT = "Event_names";
	static final String CONFIRMED_TIME = "Confirmed_time";
	static final String KEY_TASK = "Meeting_names";
	static final String KEY_INT = "Intersections";
	
	static final String DATABASE_CREATE = "CREATE TABLE "+
										DATABASE_TABLE+" ("+
										KEY_TASK+" TEXT NOT NULL PRIMARY KEY, "+
										KEY_INT+" TEXT NOT NULL);";
	static final String DATABASE_CREATE2 = "CREATE TABLE "+
			DATABASE_TABLE2+" ("+
			CONFIRMED_EVENT+" TEXT NOT NULL PRIMARY KEY, "+
			CONFIRMED_TIME+" TEXT NOT NULL);";
	
	static final String DATABASE_CREATE3 = "CREATE TABLE "+
			DATABASE_TABLE3+" ("+
			KEY_EVENT+" TEXT NOT NULL PRIMARY KEY, "+
			KEY_ANSWERED+" TEXT NOT NULL);";
	
	public SQLiteDatabase db;
	private final Context context;
	private DBHelper dbHelper;
	public DBAdapter(Context _context)
	{
		context = _context;
		dbHelper = new DBHelper(context, DATABASE_NAME);
	}
	public DBAdapter open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		db.close();
	}
	
	public SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}
	public void insertInvited(String meeting_names, String answered)
	{
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_EVENT, meeting_names);
		newValues.put(KEY_ANSWERED, answered);
		db.insert(DATABASE_TABLE3, null, newValues);
	}
	public ArrayList<String> getAllInvitedEvents()
	{
		Cursor c = db.query(DATABASE_TABLE3, new String[]{KEY_EVENT, KEY_ANSWERED} ,null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iEvent = c.getColumnIndex(KEY_EVENT);
		int iANS = c.getColumnIndex(KEY_ANSWERED);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{
			if(c.getString(iANS).equals("true"))
				list.add(new String(c.getString(iEvent)));
		}
		return list;
	}
	
	
	public void insertPending(String meeting_names, String intersections)
	{
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_TASK, meeting_names);
		newValues.put(KEY_INT,  intersections);
		db.insert(DATABASE_TABLE, null, newValues);
		
	}
	public ArrayList<StringPair> getAllPendingDB()
	{
		Cursor c = db.query(DATABASE_TABLE, new String[]{KEY_TASK, KEY_INT},null, null, null, null, null);
		ArrayList<StringPair> list = new ArrayList<StringPair>();
		
		
		int iTask = c.getColumnIndex(KEY_TASK);
		int iInt = c.getColumnIndex(KEY_INT);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{
			list.add(new StringPair(c.getString(iTask), c.getString(iInt)));
		}
		
		return list;
	}
	public String getIntesections(String src)
	{
		Cursor c = db.query(DATABASE_TABLE, new String[]{KEY_TASK, KEY_INT},KEY_TASK+"="+src, null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String intersection = c.getString(1);
			return intersection;
		}
		return null;
		
	}
	
	public String getHasAnswerd(String src)
	{
		Cursor c = db.query(DATABASE_TABLE, new String[]{KEY_EVENT, KEY_ANSWERED},KEY_EVENT+"="+src, null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String answered = c.getString(1);
			return answered;
			
		}
		return null;
		
	}
	public String searchforConfirmedDB(String src)
	{
		Cursor c = db.query(DATABASE_TABLE2, new String[]{CONFIRMED_EVENT, CONFIRMED_TIME}, null, null, null, null, null);
		String result = "";
		
		int iEvent = c.getColumnIndex(CONFIRMED_EVENT);
		int iTime = c.getColumnIndex(CONFIRMED_TIME);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{
			if(c.getString(iTime).indexOf(src)!=-1)
			{
				result = result + c.getString(iEvent)+"\t\t"+c.getString(iTime)+"\n";
			}
		}
		return result;
	}
	public void insertConfirmed(String eventname, String confirmedtime)
	{
		ContentValues newValues = new ContentValues();
		newValues.put(CONFIRMED_EVENT, eventname);
		newValues.put(CONFIRMED_TIME, confirmedtime);
		db.insert(DATABASE_TABLE2, null, newValues);
	}
	
	public boolean removePending(String event)
	{
		return db.delete(DATABASE_TABLE , KEY_TASK+"="+event,null)>0;
	}
	public boolean removeInvited(String event)
	{
		return db.delete(DATABASE_TABLE2, CONFIRMED_EVENT+"="+event,null)>0;
	}
	
}
