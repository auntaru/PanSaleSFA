package net.learn2develop.AndroidViews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.provider.BaseColumns._ID;

public class DbCreate extends SQLiteOpenHelper{
	private static final String DB_NAME = "contactTest1.db";
	private static final int DB_VERSION = 1;
	
	/** Create a helper object for the Events database */
	   public DbCreate(Context context) { 
	      super(context, DB_NAME, null, DB_VERSION);
	   }

	   @Override
	   public void onCreate(SQLiteDatabase db) { 
	      db.execSQL("CREATE TABLE " + DbConstants.TABLE_NAME + " " +
	      		"(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	      			  DbConstants.NAME + " TEXT NOT NULL," + 
	      			  DbConstants.PHONE + " TEXT NOT NULL," +
	      			  DbConstants.EMAIL + ");");
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, 
	         int newVersion) {
	      db.execSQL("DROP TABLE IF EXISTS " + DbConstants.TABLE_NAME);
	      onCreate(db);
	   }
}
