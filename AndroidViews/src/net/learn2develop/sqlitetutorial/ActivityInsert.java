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
import android.widget.TextView;
import android.widget.Toast;

public class ActivityInsert extends Activity 
{
	private Button demoAddButton;
	private Button demoSearchButton;
	private Button demoDeleteButton;
	private Button demoUpdateButton;
	private Button insertButton;
	private TextView nameEditText;
	private TextView addressEditText;
	private DatabaseHelper dbHelper = new DatabaseHelper(ActivityInsert.this,"example",2);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        
        demoSearchButton =(Button)findViewById(R.id.demoSearchButton);
        demoAddButton =(Button)findViewById(R.id.demoAddButton);
        demoDeleteButton =(Button)findViewById(R.id.demoDeleteButton);
        demoUpdateButton =(Button)findViewById(R.id.demoUpdateButton);
        insertButton = (Button)findViewById(R.id.addButton);
        nameEditText = (EditText)findViewById(R.id.name);
        addressEditText = (EditText)findViewById(R.id.address);
        
        demoSearchButton.setOnClickListener(new DemoSearchButtonListener());
        demoAddButton.setOnClickListener(new DemoAddButtonListener());
        demoDeleteButton.setOnClickListener(new DemoDeleteButtonListener());
        demoUpdateButton.setOnClickListener(new DemoUpdateButtonListener());
        insertButton.setOnClickListener(new InsertButtonListener());
        
        demoAddButton.setClickable(false);
    }
    
    class InsertButtonListener implements OnClickListener, android.view.View.OnClickListener
  	{
  		public void onClick(View v) 
  		{
  			if("".equals(nameEditText.getText().toString()) || "".equals(addressEditText.getText().toString()))
  			{
  				Toast toast = Toast.makeText(ActivityInsert.this, "Sorry, you must input both the name and the address!", Toast.LENGTH_LONG); 
    			toast.show();
  			}
  			else
  			{
	  			long flag = 0;
	  			int id = 1;
	  			SQLiteDatabase db = dbHelper.getWritableDatabase();
	  			Cursor cursor = db.query("my_table", new String[]{"count(*) ID"}, null, null, null, null, null);
				while(cursor.moveToNext())
				{
					int idFromDatabase = cursor.getInt(cursor.getColumnIndex("ID"));
					if(idFromDatabase != 0)
					{
						id = 1 + idFromDatabase;
					}
				}
	  			ContentValues values = new ContentValues();
	  			values.put("ID", id);
	  			values.put("name", nameEditText.getText().toString().trim());
	  			values.put("address", addressEditText.getText().toString().trim());
	  			flag = db.insert("my_table", null, values);
	  			if(flag != -1)
	  			{
	  				Toast toast = Toast.makeText(ActivityInsert.this, "You have successful inserted this record into database! ", Toast.LENGTH_LONG); 
	    			toast.show();
	    			db.close();
	    			return;
	  			}
	  			else
	  			{
	  				Toast toast = Toast.makeText(ActivityInsert.this, "An error occured when insert this record into database!", Toast.LENGTH_LONG); 
	    			toast.show();
	    			db.close();
	    			return;
	  			}
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
             intent.setClass(ActivityInsert.this, ActivityMain.class);
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
            intent.setClass(ActivityInsert.this, ActivityInsert.class);
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
            intent.setClass(ActivityInsert.this, ActivityDelete.class);
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
            intent.setClass(ActivityInsert.this, ActivityUpdate.class);
            startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
}
