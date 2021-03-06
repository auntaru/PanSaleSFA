
/* 
 * http://www.edumobile.org/android/android-development/use-of-sqlite/
 * 
 */

package net.learn2develop.AndroidViews;

// import android.R;
import net.learn2develop.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class DataSample2 extends Activity implements OnClickListener {
	
	private DataManipulator dm;	
	// DataManipulator dm;
	
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maindb2);
		View button1Click = findViewById(R.id.button1);
		button1Click.setOnClickListener(this);
		View button2Click = findViewById(R.id.button2);
		button2Click.setOnClickListener(this);        
		View button3Click = findViewById(R.id.button3);
		button3Click.setOnClickListener(this);        
		
	}

	// @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){

		case R.id.button1:
			Intent i = new Intent(this, GetProducts02.class);  
			startActivity(i);
			break;
		case R.id.button2:
			Intent i1 = new Intent(this, DataCheck.class);  
			startActivity(i1);
			break;
		case R.id.button3:
			// this.dm.db.close();
        	this.dm = new DataManipulator(this);
        	this.dm.deleteAllProducts();
    		if (this.dm != null) {
    			this.dm.close();
    		}
			break;
		}
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (dm != null) {
			dm.close();
		}
	}

}

