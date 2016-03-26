/*
 * 
 * http://mobiforge.com/designing/story/understanding-user-interface-android-part-2-views
 * 
 */

package net.learn2develop.AndroidViews;
 
import net.learn2develop.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
 
public class BasicViewsExample extends Activity 
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicviews);
 
        //---Button view---
        Button btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), 
                        "You have clicked the Open button", 
                        Toast.LENGTH_SHORT).show();
            }
        });
 
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) {
                DisplayToast("You have clicked the Save button");
            }
        });
 
        //---CheckBox---
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkAutosave);
        checkBox.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) 
                    DisplayToast("CheckBox is checked");
                else
                    DisplayToast("CheckBox is unchecked");            
            }
        });
 
        //---RadioButton---
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdbGp1);        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //---displays the ID of the RadioButton that is checked---
                DisplayToast(Integer.toString(checkedId));
            }
        });
 
        //---ToggleButton---
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle1);
        toggleButton.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) {
               if (((ToggleButton)v).isChecked())
                    DisplayToast("Toggle button is On");
               else
                   DisplayToast("Toggle button is Off");
            }
        });
    }
 
    private void DisplayToast(String msg)
    {
         Toast.makeText(getBaseContext(), msg, 
                 Toast.LENGTH_SHORT).show();        
    }    
}