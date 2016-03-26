package net.learn2develop.AndroidViews;
 
import net.learn2develop.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
 
public class DepoziteAutoComplete extends Activity
{
    String[] depozite = 
    {
            "TIMISOARA",
            "DEVA",
            "BUCURESTI",
            "CLUJ",
            "BAIA MARE",
            "TARGU MURES",
            "BACAU",
            "BRASOV",
            "CONSTANTA",
            "SUCEAVA",
            "CRAIOVA"
    };
 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete);
 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, depozite);
 
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.txtCountries);
        textView.setThreshold(1);
        textView.setAdapter(adapter);
        
    }
}