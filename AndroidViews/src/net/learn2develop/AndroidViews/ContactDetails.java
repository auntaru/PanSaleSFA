
/*
   http://computersight.com/software/android-and-sqlite-using-database-part-five/
*/


package net.learn2develop.AndroidViews;

import net.learn2develop.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactDetails extends Activity{
	private Button cancelButton;
	private Button saveContactButton;
	
	private static EditText nameField; 
	private static EditText mobilePhoneField;
	private static EditText emailField;
	
	private static String operationType;
	static final int RESULT_MODIFY_USER = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactdetails);
		
		Bundle incomingActionType = getIntent().getExtras();
		operationType = incomingActionType.getString("mod_type");
		
		if (operationType.equals("addPerson")){
			
			nameField = (EditText) findViewById(R.id.contactName); 
			mobilePhoneField = (EditText) findViewById(R.id.contactmobile);
			emailField = (EditText) findViewById(R.id.contactemail);
			
		}
		
		else if (operationType.equals("modifyPerson")){
			
			String modifyFirstName = incomingActionType.getString("cFirstName");
			nameField = (EditText) findViewById(R.id.contactName);
			nameField.setText(modifyFirstName);
			
		
			String modifyPhoneNumber = incomingActionType.getString("cMobilePhone");
			mobilePhoneField = (EditText) findViewById(R.id.contactmobile);
			mobilePhoneField.setText(modifyPhoneNumber);
			
			String modifyEmail = incomingActionType.getString("cEmail");
			emailField = (EditText) findViewById(R.id.contactemail);
			emailField.setText(modifyEmail);	
		}
					
	
	cancelButton = (Button) findViewById(R.id.cancelcontactbutton);
	saveContactButton = (Button) findViewById(R.id.savecontactbutton);

	saveContactButton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent resultIntent = new Intent();
			Bundle contactData = new Bundle();

				if (nameField.getText().toString().length() == 0){
					new AlertDialog.Builder(ContactDetails.this).setTitle("Error").setMessage("First name field cannot be empty!").setNeutralButton("Close", null).show();
				} else if (mobilePhoneField.getText().toString().length() == 0){
					new AlertDialog.Builder(ContactDetails.this).setTitle("Error").setMessage("Mobile Phone field cannot be empty!").setNeutralButton("Close", null).show();
				} else if (emailField.getText().toString().length() == 0){
					new AlertDialog.Builder(ContactDetails.this).setTitle("Error").setMessage("Email field cannot be empty!").setNeutralButton("Close", null).show();
				} else{
					contactData.putString("contactFirstName", nameField.getText().toString());
					contactData.putString("contactMobilePhone", mobilePhoneField.getText().toString());
					contactData.putString("contactEmail", emailField.getText().toString());
		
					resultIntent.putExtra("contactData", contactData);
					
					if (operationType.equals("addPerson")){
						setResult(RESULT_FIRST_USER, resultIntent);
					}else if (operationType.equals("modifyPerson")){
						setResult(RESULT_MODIFY_USER, resultIntent);
					}				
					finish();
				}
		}
	});
	
	cancelButton.setOnClickListener(new View.OnClickListener(){
		

		public void onClick(View v) {
			Intent resultIntent = new Intent();
			setResult(RESULT_CANCELED, resultIntent);
            finish();
			
			
		}
	});

	}
}
