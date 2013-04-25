package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	static final String DATABASE_NAME = "groupMeet.db";
	static final String DATABASE_TABLE = "Pending_Meetings";
	static final String DATABASE_TABLE2= "Confirmed_Meetings";
	static final String KEY_ID = "id";
	static final String CONFIRMED_EVENT = "Event_names";
	static final String CONFIRMED_TIME = "Confirmed_time";
	static final String KEY_TASK = "Meeting_names";
	
	static final String DATABASE_CREATE = "CREATE TABLE "+
										DATABASE_TABLE+" ("+
										KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
										KEY_TASK+" TEXT NOT NULL);";
	static final String DATABASE_CREATE2 = "CREATE TABLE "+
			DATABASE_TABLE2+" ("+
			KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
			CONFIRMED_EVENT+" TEXT NOT NULL, "+
			CONFIRMED_TIME+" TEXT NOT NULL);";
	
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
	
	public void insertEntry(String meeting_names)
	{
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_TASK, meeting_names);
		db.insert(DATABASE_TABLE, null, newValues);
		
	}
	public void insertEntry2(String eventname, String confirmedtime)
	{
		ContentValues newValues = new ContentValues();
		newValues.put(CONFIRMED_EVENT, eventname);
		newValues.put(CONFIRMED_TIME, confirmedtime);
		db.insert(DATABASE_TABLE2, null, newValues);
	}
	public boolean removeEntry(String table, int index)
	{
		return db.delete(table , KEY_ID+"="+index,null)>0;
	}
	public Cursor getAllEntries(String table)
	{
		return db.query(table, new String[]{KEY_ID,KEY_TASK}, null, null, null, null, null);
	}
	public String getEntry(String table, int index) throws SQLException
	{
		Cursor cursor = db.query(true, table, new String[]{KEY_ID,KEY_TASK},KEY_ID+"="+index,null,null,null,null,null);
		if((cursor.getCount() == 0 || !cursor.moveToFirst()))
		{
			throw new SQLException("No item found for row: "+index);
		}
		String item = cursor.getString(1);
		return item;
	}
	

}
