package net.learn2develop.AndroidViews;

import net.learn2develop.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteAndroid extends Activity {
	
	EditText inputContent1, inputContent2;
	Button buttonAdd, buttonDeleteAll;
	
	private SQLiteAdapter mySQLiteAdapter;
	ListView listContent;
	
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lite);
        
        inputContent1 = (EditText)findViewById(R.id.content1);
        inputContent2 = (EditText)findViewById(R.id.content2);
        buttonAdd = (Button)findViewById(R.id.add);
        buttonDeleteAll = (Button)findViewById(R.id.deleteall);
        
        listContent = (ListView)findViewById(R.id.contentlist);

        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();

        cursor = mySQLiteAdapter.queueAll();
        String[] from = new String[]{SQLiteAdapter.KEY_ID, SQLiteAdapter.KEY_CONTENT1, SQLiteAdapter.KEY_CONTENT2};
        int[] to = new int[]{R.id.id, R.id.text1, R.id.text2};
        cursorAdapter =
        	new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
        listContent.setAdapter(cursorAdapter);
        listContent.setOnItemClickListener(listContentOnItemClickListener);
        
        buttonAdd.setOnClickListener(buttonAddOnClickListener);
        buttonDeleteAll.setOnClickListener(buttonDeleteAllOnClickListener);
        
    }
    
    Button.OnClickListener buttonAddOnClickListener
    = new Button.OnClickListener(){

		// @Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String data1 = inputContent1.getText().toString();
			String data2 = inputContent2.getText().toString();
			mySQLiteAdapter.insert(data1, data2);
			updateList();
		}
    	
    };
    
    Button.OnClickListener buttonDeleteAllOnClickListener
    = new Button.OnClickListener(){

		// @Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mySQLiteAdapter.deleteAll();
			updateList();
		}
    	
    };
    
    private ListView.OnItemClickListener listContentOnItemClickListener
    = new ListView.OnItemClickListener(){

		// @Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			Cursor cursor = (Cursor) parent.getItemAtPosition(position);
			final int item_id = cursor.getInt(cursor.getColumnIndex(SQLiteAdapter.KEY_ID));
            String item_content1 = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.KEY_CONTENT1));
            String item_content2 = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.KEY_CONTENT2));
            
            AlertDialog.Builder myDialog 
            = new AlertDialog.Builder(SQLiteAndroid.this);
            
            myDialog.setTitle("Delete?");
            
            TextView dialogTxt_id = new TextView(SQLiteAndroid.this);
            LayoutParams dialogTxt_idLayoutParams 
             = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            dialogTxt_id.setLayoutParams(dialogTxt_idLayoutParams);
            dialogTxt_id.setText("#" + String.valueOf(item_id));
            
            TextView dialogC1_id = new TextView(SQLiteAndroid.this);
            LayoutParams dialogC1_idLayoutParams 
             = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            dialogC1_id.setLayoutParams(dialogC1_idLayoutParams);
            dialogC1_id.setText(item_content1);
            
            TextView dialogC2_id = new TextView(SQLiteAndroid.this);
            LayoutParams dialogC2_idLayoutParams 
             = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            dialogC2_id.setLayoutParams(dialogC2_idLayoutParams);
            dialogC2_id.setText(item_content2);
            
            LinearLayout layout = new LinearLayout(SQLiteAndroid.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(dialogTxt_id);
            layout.addView(dialogC1_id);
            layout.addView(dialogC2_id);
            myDialog.setView(layout);
            
            myDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                	mySQLiteAdapter.delete_byID(item_id);
        			updateList();
                 }
                });
            
            myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
         
                 }
                });
            
            myDialog.show();
            
            
		}};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mySQLiteAdapter.close();
	}



	private void updateList(){
		cursor.requery();
    }

}