
/* 
 * http://www.edumobile.org/android/android-development/use-of-sqlite/
 * 
 */

package net.learn2develop.AndroidViews;

import net.learn2develop.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DataSave extends Activity implements OnClickListener {  
	private DataManipulator dh;     
	static final int DIALOG_ID = 0;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.save);
		setContentView(R.layout.salesdetails);
        View add = findViewById(R.id.savebutton);
		add.setOnClickListener(this);
		View home = findViewById(R.id.cancelbutton);
		home.setOnClickListener(this);           
	}

	public void onClick(View v){
		switch(v.getId()){

		case R.id.cancelbutton:
			Intent i = new Intent(this, DataSample.class);
			startActivity(i);
			break;

		case R.id.savebutton:
			View editText1 = (EditText) findViewById(R.id.clientName);
			View editText2 = (EditText) findViewById(R.id.productName);
			View editText3 = (EditText) findViewById(R.id.price);
			View editText4 = (EditText) findViewById(R.id.discount);	
			String myEditText1=((TextView) editText1).getText().toString();
			String myEditText2=((TextView) editText2).getText().toString();
			String myEditText3=((TextView) editText3).getText().toString();
			String myEditText4=((TextView) editText4).getText().toString();

			this.dh = new DataManipulator(this);
			this.dh.insert(myEditText1,myEditText2,myEditText3,myEditText4);

			showDialog(DIALOG_ID);
            break;

		}
	}  
	protected final Dialog onCreateDialog(final int id) {
		Dialog dialog = null;
		switch(id) {
		case DIALOG_ID:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Information saved successfully ! Add Another Info?")
			.setCancelable(false)
			.setPositiveButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					DataSave.this.finish();

              }
			})
			.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create(); 
			dialog = alert;
			break;

		default:

		}
		return dialog;
	}



}

