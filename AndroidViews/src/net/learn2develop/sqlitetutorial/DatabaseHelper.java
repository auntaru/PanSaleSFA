/**
 * 
 * Author: Fang Dong
 * Date: Aug 25, 2011
 * Email: dongfang@mogoo.org
 * License: GNU GPL 
 * This is an example application for course AG2426 Mobile GIS, KTH
 * 
 * **/
package net.learn2develop.sqlitetutorial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper 
{
	private static final int VERSION = 1;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) 
	{	
		super(context, name, factory, version);
	}
	public DatabaseHelper(Context context,String name)
	{
		this(context,name,VERSION);
	}
	public DatabaseHelper(Context context,String name,int version)
	{
		this(context, name,null,version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		//do nothing
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// do nothing
	}
}
