package com.example.lab6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
	static int DATABASE_VERSION = 3;
	public DBHelper(Context context, String dbname)
	{
		super(context, dbname, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{		
		db.execSQL(DBAdapter.DATABASE_CREATE);
		db.execSQL(DBAdapter.DATABASE_CREATE2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + DBAdapter.DATABASE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBAdapter.DATABASE_TABLE2);
		onCreate(db);
	}
}
