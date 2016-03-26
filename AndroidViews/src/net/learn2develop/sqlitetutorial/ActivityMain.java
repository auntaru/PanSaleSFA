/**
 * 
 * Author: Fang Dong
 * Date: Aug 25, 2011
 * Email: dongfang@mogoo.org
 * License: GNU GPL 
 * 
 * http://www.youtube.com/watch?v=9LvRN0OlVQg
 * 
 **/

package net.learn2develop.sqlitetutorial;

import net.learn2develop.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMain extends Activity 
{
	private Button demoAddButton;
	private Button demoSearchButton;
	private Button demoDeleteButton;
	private Button demoUpdateButton;
	private Button searchButton;
	private TextView addTextView;
	private DatabaseHelper dbHelper = new DatabaseHelper(ActivityMain.this,"example",2);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmogoo);
        /** find view element by id **/
        demoSearchButton =(Button)findViewById(R.id.demoSearchButton);
        demoAddButton =(Button)findViewById(R.id.demoAddButton);
        demoDeleteButton =(Button)findViewById(R.id.demoDeleteButton);
        demoUpdateButton =(Button)findViewById(R.id.demoUpdateButton);
        searchButton =(Button)findViewById(R.id.searchButton);
        addTextView =(TextView)findViewById(R.id.displayTextView);
        /** binding each button with button listener **/
        demoSearchButton.setOnClickListener(new DemoSearchButtonListener());
        demoAddButton.setOnClickListener(new DemoAddButtonListener());
        demoDeleteButton.setOnClickListener(new DemoDeleteButtonListener());
        demoUpdateButton.setOnClickListener(new DemoUpdateButtonListener());
        searchButton.setOnClickListener(new searchButtonListener());
        /** disable the button which represents the current activity **/
        demoSearchButton.setClickable(false);
        /** create the database if it dosen't exist in your phone **/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
		try
		{
			db.execSQL("create table my_table(ID integer, name varchar(90), address varchar(90));");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    /** this is a listener, when button Show Table is pressed, the following code will be executed **/
    class searchButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			String display = "";
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			/** the result will be loaded in cursor **/
			Cursor cursor = db.query("my_table", new String[]{"ID","name","address"}, null, null, null, null, null);
			/** check if the table is empty **/
			if (!cursor.moveToNext())
			{
				addTextView.setText("No data to display, please make sure you have already inserted data!");
				db.close();
				return;
			}
			cursor.moveToPrevious();
			/** if the table is not empty, read the result into a string named display **/
			while(cursor.moveToNext())
			{
				int ID = cursor.getInt(cursor.getColumnIndex("ID"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String address = cursor.getString(cursor.getColumnIndex("address"));
				display = display + "\n"+"No."+ID+", Name: "+name+", Address: "+address+"\n";
			}
			/** display the result on the phone **/
			addTextView.setText(display);
			db.close();
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
    
    class DemoSearchButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			 /** the code here is for jumping between two different Activities **/
			 Intent intent = new Intent();
             intent.setClass(ActivityMain.this, ActivityMain.class);
             startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
    class DemoAddButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			Intent intent = new Intent();
            intent.setClass(ActivityMain.this, ActivityInsert.class);
            startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}

	}
    class DemoDeleteButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			Intent intent = new Intent();
            intent.setClass(ActivityMain.this, ActivityDelete.class);
            startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
    class DemoUpdateButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			Intent intent = new Intent();
            intent.setClass(ActivityMain.this, ActivityUpdate.class);
            startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
}