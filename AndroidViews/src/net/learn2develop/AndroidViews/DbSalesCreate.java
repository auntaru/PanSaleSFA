package net.learn2develop.AndroidViews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.provider.BaseColumns._ID;

public class DbSalesCreate extends SQLiteOpenHelper{
	private static final String DB_NAME = "easySales.db";
	private static final int DB_VERSION = 1;
	
	/** Create a helper object for the Events database */
	   public DbSalesCreate(Context context) { 
	      super(context, DB_NAME, null, DB_VERSION);
	   }

	   @Override
	   public void onCreate(SQLiteDatabase db) { 
	      db.execSQL("CREATE TABLE " + DbSalesConstants.TABLE_NAME + " " +
	      		DbSalesConstants.NAME + " TEXT NOT NULL," + 
	      		DbSalesConstants.ID + " TEXT NOT NULL," +
	      		DbSalesConstants.SYMBOL + " TEXT NOT NULL," +
	      		DbSalesConstants.PRICE + " TEXT NOT NULL" + ");");
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, 
	         int newVersion) {
	      db.execSQL("DROP TABLE IF EXISTS " + DbConstants.TABLE_NAME);
	      onCreate(db);
	   }
}
