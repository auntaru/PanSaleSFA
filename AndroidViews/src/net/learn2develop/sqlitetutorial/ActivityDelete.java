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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityDelete extends Activity 
{
	private Button demoAddButton;
	private Button demoSearchButton;
	private Button demoDeleteButton;
	private Button demoUpdateButton;
	private Button deleteButton;
	private EditText nameEditText;
	private DatabaseHelper dbHelper = new DatabaseHelper(ActivityDelete.this,"example",2);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        
        demoSearchButton =(Button)findViewById(R.id.demoSearchButton);
        demoAddButton =(Button)findViewById(R.id.demoAddButton);
        demoDeleteButton =(Button)findViewById(R.id.demoDeleteButton);
        demoUpdateButton =(Button)findViewById(R.id.demoUpdateButton);
        deleteButton =(Button)findViewById(R.id.deleteButton);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        
        demoSearchButton.setOnClickListener(new DemoSearchButtonListener());
        demoAddButton.setOnClickListener(new DemoAddButtonListener());
        demoDeleteButton.setOnClickListener(new DemoDeleteButtonListener());
        demoUpdateButton.setOnClickListener(new DemoUpdateButtonListener());
        deleteButton.setOnClickListener(new DeleteButtonListener());
        
        demoDeleteButton.setClickable(false);
    }
    
    class DeleteButtonListener implements OnClickListener, android.view.View.OnClickListener
   	{
   		public void onClick(View v) 
   		{
   			int flag = 0;
   			SQLiteDatabase db = dbHelper.getWritableDatabase();
  			flag = db.delete("my_table", "name=?", new String[]{""+nameEditText.getText().toString().trim()});
  			if(flag != 0)
  			{
  				Toast toast = Toast.makeText(ActivityDelete.this, "Delete successful!", Toast.LENGTH_LONG); 
    			toast.show();
    			db.close();
    			return;
  			}
  			else
  			{
  				Toast toast = Toast.makeText(ActivityDelete.this, "Delete failed! Please try again!", Toast.LENGTH_LONG); 
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
    
    class DemoSearchButtonListener implements OnClickListener, android.view.View.OnClickListener
	{
		public void onClick(View v) 
		{
			 Intent intent = new Intent();
             intent.setClass(ActivityDelete.this, ActivityMain.class);
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
            intent.setClass(ActivityDelete.this, ActivityInsert.class);
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
            intent.setClass(ActivityDelete.this, ActivityDelete.class);
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
            intent.setClass(ActivityDelete.this, ActivityUpdate.class);
            startActivityForResult(intent, 0);
		}

		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub	
		}
	}
}

