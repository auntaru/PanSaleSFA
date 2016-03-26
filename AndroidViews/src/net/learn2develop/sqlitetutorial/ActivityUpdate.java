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

import net.learn2develop.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityUpdate extends Activity 
{
	private Button demoAddButton;
	private Button demoSearchButton;
	private Button demoDeleteButton;
	private Button demoUpdateButton;
	private Button loadButton;
	private Button updateButton;
	private EditText nameEditText;
	private EditText name2EditText;
	private EditText addressEditText;
	private long idG = 0;
	private DatabaseHelper dbHelper = new DatabaseHelper(ActivityUpdate.this,"example",2);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        
        demoSearchButton =(Button)findViewById(R.id.demoSearchButton);
        demoAddButton =(Button)findViewById(R.id.demoAddButton);
        demoDeleteButton =(Button)findViewById(R.id.demoDeleteButton);
        demoUpdateButton =(Button)findViewById(R.id.demoUpdateButton);
        updateButton =(Button)findViewById(R.id.updateButton);
        loadButton =(Button)findViewById(R.id.loadButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
    	name2EditText = (EditText) findViewById(R.id.name2EditText);
    	addressEditText = (EditText) findViewById(R.id.addressEditText);;
    	
        demoSearchButton.setOnClickListener(new DemoSearchButtonListener());
        demoAddButton.setOnClickListener(new DemoAddButtonListener());
        demoDeleteButton.setOnClickListener(new DemoDeleteButtonListener());
        demoUpdateButton.setOnClickListener(new DemoUpdateButtonListener());
        updateButton.setOnClickListener(new UpdateButtonListener());
        loadButton.setOnClickListener(new LoadButtonListener());
        
        demoUpdateButton.setClickable(false);
    }
    
    class UpdateButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			long flag = 0;
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
   			values.put("ID", idG);
  			values.put("name", name2EditText.getText().toString());
  			values.put("address", addressEditText.getText().toString());
   			flag = db.update("my_table",values,"ID=?",new String[]{""+idG});
  			if(flag != -1)
  			{
  				Toast toast = Toast.makeText(ActivityUpdate.this, "Data is updated successfully!", Toast.LENGTH_LONG); 
    			toast.show();
    			db.close();
    			return;
  			}
  			else
  			{
  				Toast toast = Toast.makeText(ActivityUpdate.this, "Sorry, update dailed! Please try again!", Toast.LENGTH_LONG); 
    			toast.show();
    			db.close();
    			return;
  			}
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
    
    class LoadButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			if("".equals(nameEditText.getText().toString()))
			{
				Toast toast = Toast.makeText(ActivityUpdate.this, "Load failed, you haven't input the name!", Toast.LENGTH_LONG); 
    			toast.show();
    			return;
			}
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.query("my_table", new String[]{"ID","name","address"}, "name=?", new String[]{nameEditText.getText().toString()}, null, null, null);
			if (!cursor.moveToNext())
			{
				Toast toast = Toast.makeText(ActivityUpdate.this, "Sorry, can't find the match result, please try again!", Toast.LENGTH_LONG); 
    			toast.show();
    			return;
			}
				cursor.moveToPrevious();
				while(cursor.moveToNext())
				{
					int ID = cursor.getInt(cursor.getColumnIndex("ID"));
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String address = cursor.getString(cursor.getColumnIndex("address"));
					name2EditText.setText(name);
					addressEditText.setText(address);
					idG = ID;
				}
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
			 Intent intent = new Intent();
             intent.setClass(ActivityUpdate.this, ActivityMain.class);
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
            intent.setClass(ActivityUpdate.this, ActivityInsert.class);
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
            intent.setClass(ActivityUpdate.this, ActivityDelete.class);
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
            intent.setClass(ActivityUpdate.this, ActivityUpdate.class);
            startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
}

