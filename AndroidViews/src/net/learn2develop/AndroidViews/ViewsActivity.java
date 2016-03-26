package net.learn2develop.AndroidViews;
 
import net.learn2develop.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
 
public class ViewsActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
 
        //---load the BasicViewsExample activity---
        // startActivity(new Intent(this, BasicViewsExample.class));
        
        // startActivity(new Intent(this, AutoCompleteExample.class));
        // startActivity(new Intent(this, DepoziteAutoComplete.class));
        startActivity(new Intent(this, LateOrdersByZone.class));
        // startActivity(new Intent(this, ListViewExample.class));
        
        
    }
}
