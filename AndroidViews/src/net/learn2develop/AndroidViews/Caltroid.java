/**
 *   
 *   http://www.heikkitoivonen.net/blog/2009/02/15/multicolumn-listview-in-android/
 *   http://svn.heikkitoivonen.net/caltroid/trunk/src/net/heikkitoivonen/android/caltroid/Caltroid.java
 *   http://mylifewithandroid.blogspot.com/2008/03/my-first-meeting-with-simpleadapter.html
 *   
*/

package net.learn2develop.AndroidViews;

import java.util.ArrayList;
import java.util.List;

import net.learn2develop.R;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Spinner;

public class Caltroid extends Activity implements Runnable {
    public static final String PREFS_NAME = "CaltroidPrefsFile";
    public static final String PREFS_DAY = "day";
    public static final String PREFS_DIRECTION = "direction";
    public static final String PREFS_FROM = "fromStation";
    public static final String PREFS_TO = "toStation";
    public static final String PREFS_ALL_TRAINS = "allTrains";
    
    public static final String DEFAULT_FROM = "Gilroy";
    public static final String DEFAULT_TO = "San Francisco";

    protected Button mDay;
    protected Button mDirection;
    protected CheckBox mAll;
    protected Spinner mFrom;
    protected Spinner mTo;
    
    protected String mLastFrom;
    protected String mLastTo;
    
    protected ProgressDialog mProgress;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore day, direction, fromStation, toStation
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        
        String day = settings.getString(PREFS_DAY, getString(R.string.weekday));
        String direction = settings.getString(PREFS_DIRECTION, getString(R.string.northbound));
        String fromStation = settings.getString(PREFS_FROM, DEFAULT_FROM);
        String toStation = settings.getString(PREFS_TO, DEFAULT_TO);
        Boolean all = settings.getBoolean(PREFS_ALL_TRAINS, false);

        mLastFrom = fromStation;
        mLastTo = toStation;
        
        setContentView(R.layout.caltroid);

        // Set button listeners
        this.mDay = (Button) findViewById(R.id.DAY);
        mDay.setText(day);
        mDay.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (mDay.getText() == getString(R.string.weekday)) {
                    mDay.setText(getString(R.string.weekend));
                } else {
                    mDay.setText(getString(R.string.weekday));
                }
                updateSchedule(false, false);
            }
        });
        
        this.mDirection = (Button) findViewById(R.id.DIRECTION);
        mDirection.setText(direction);
        mDirection.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (mDirection.getText() == getString(R.string.northbound)) {
                    mDirection.setText(getString(R.string.southbound));
                } else {
                    mDirection.setText(getString(R.string.northbound));
                }
                updateSchedule(true, false);
            }
        });

        Button location = (Button) findViewById(R.id.LOCATE);
        location.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                locate();
            }
        });

        // Set checkbox listener
        mAll = (CheckBox) findViewById(R.id.ALL);
        mAll.setChecked(all);
        mAll.setOnClickListener(new CheckBox.OnClickListener() {
            public void onClick(View v) {
                // XXX could probably do this using a filter on the grid's adapter more efficiently
                updateSchedule(false, true);
            }
        });

        // Set spinner listeners
        this.mFrom = (Spinner) findViewById(R.id.FROM);
        mFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View v, int position, long id) {
                // XXX Kind of stupid hack around selection events that come even without any user action 
                if (!mLastFrom.equals((String)mFrom.getSelectedItem())) {
                    updateSchedule(false, true);
                }
            }
            
            public void onNothingSelected(AdapterView parent) {
                // nothing
            }
        });
        
        this.mTo = (Spinner) findViewById(R.id.TO);
        mTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View v, int position, long id) {
                // XXX Kind of stupid hack around selection events that come even without any user action 
                if (!mLastTo.equals((String)mTo.getSelectedItem())) {
                    updateSchedule(false, true);
                }
            }
            
            public void onNothingSelected(AdapterView parent) {
                // nothing
            }
        });

        updateSchedule(day, direction, fromStation, toStation, false);
    }

    /**
     * Pop up an indeterminate and cancelable progress dialog while we locate
     * the nearest Caltrain station.
     */
    public void locate() {
        mProgress = ProgressDialog.show(this, getString(R.string.wait), 
                                        getString(R.string.locating), 
                                        true, true);
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void run() {
        String fromStation = Stations.nearestStation(this);
        String toStation, direction;
        
        String oldFrom = (String)mFrom.getSelectedItem();
        String oldTo = (String)mTo.getSelectedItem();
        String oldDirection = mDirection.getText().toString();
        
        if (fromStation.equals(oldFrom)) {
            // No changes if from station did not change
            toStation = oldTo;
            direction = oldDirection;
        } else if (fromStation.equals(oldTo)) {
            // If new from station is same as old to station, just switch the stations and direction
            toStation = oldFrom;
            if (oldDirection.equals(getString(R.string.northbound))) {
                direction = getString(R.string.southbound);
            } else {
                direction = getString(R.string.northbound);
            }
        } else {
            // Now we have to rely on some heuristics. Let's make it simple.
            if (fromStation.equals(DEFAULT_TO)) {
                toStation = DEFAULT_FROM;
                direction = getString(R.string.southbound);
            } else {
                toStation = DEFAULT_TO;
                direction = getString(R.string.northbound);
            }
        }

        Message msg = new Message();
        Bundle bundle = msg.getData();
        bundle.putString(PREFS_DAY, mDay.getText().toString()); // XXX Would be nice to get the real day, taking Caltrain holiday schedules into account
        bundle.putString(PREFS_DIRECTION, direction);
        bundle.putString(PREFS_FROM, fromStation);
        bundle.putString(PREFS_TO, toStation);
        bundle.putBoolean(PREFS_ALL_TRAINS, mAll.isChecked());
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mProgress.dismiss();
 
                Bundle bundle = msg.getData();
                String day = bundle.getString(PREFS_DAY);
                String direction = bundle.getString(PREFS_DIRECTION);
                String fromStation = bundle.getString(PREFS_FROM);
                String toStation = bundle.getString(PREFS_TO);
                Boolean all = bundle.getBoolean(PREFS_ALL_TRAINS);
                
                mAll.setChecked(all);
                mDirection.setText(direction);
                // All the others will be taken care of in updateSchedule
                
                updateSchedule(day, direction, fromStation, toStation, false);
            }
        };

    /**
     * Update the schedule after some change in state has happened.
     * 
     * @param swapStations
     */
    public void updateSchedule(boolean swapStations, boolean stationChangeOnly) {
        String day = mDay.getText().toString();
        String direction = mDirection.getText().toString();
        String fromStation = (String)mFrom.getSelectedItem();
        String toStation = (String)mTo.getSelectedItem();

        if (swapStations) {
            String temp = fromStation;
            fromStation = toStation;
            toStation = temp;
        }
        
        updateSchedule(day, direction, fromStation, toStation, stationChangeOnly);
    }
    
    /**
     * Update the schedule after some change in state has happened.
     * 
     * @param day
     * @param direction
     * @param fromStation
     * @param toStation
     */
    public void updateSchedule(String day, String direction, String fromStation, String toStation, boolean stationChangeOnly) {
        // XXX It is quite wasteful to recreate the adapters each time, would
        // XXX be nice if I could update the data and notify of change.
        
        // Get station lists
        int fromIndex = -1, toIndex = -1; 
        JSONArray timetable = getTable(day, direction);
        List<String> fromStations = new ArrayList<String>();
        try {
            for (int i=1; i < timetable.length(); i++) {// 1st is Train No.
                String station = timetable.getJSONArray(i).getString(0);
                fromStations.add(station);
                if (station.equals(fromStation)) {
                    fromIndex = i - 1;
                }
            }
        } catch (JSONException e) {}

        // Reverse stations for destination
        // XXX Would be nice if I could just reverse fromStations somehow
        List<String> toStations = new ArrayList<String>();
        try {
            for (int i=timetable.length() - 1; i > 0; i--) {// 1st is Train No.
                String station = timetable.getJSONArray(i).getString(0);
                toStations.add(station);
                if (station.equals(toStation)) {
                    toIndex = timetable.length() - i - 1;
                }
            }
        } catch (JSONException e) {}
        
        // if we just used the spinners to change stations, we don't need to rebuild them
        if (!stationChangeOnly) {
            // Create from/to selectors
            ArrayAdapter<String> stations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromStations);
            stations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Shows radio buttons
            mFrom.setAdapter(stations);
            fromIndex = fromIndex != -1 ? fromIndex : 0;
            mFrom.setSelection(fromIndex);
            fromStation = fromStations.get(fromIndex); // may have changed due to day change
    
            ArrayAdapter<String> stations2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toStations);
            stations2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTo.setAdapter(stations2);
            toIndex = toIndex != -1 ? toIndex : 0;
            mTo.setSelection(toIndex);
            toStation = toStations.get(toIndex); // may have changed due to day change
        }
        
        mLastFrom = (String)mFrom.getSelectedItem();
        mLastTo = (String)mTo.getSelectedItem();

        // Fill in the actual schedule
        List<String> schedule = new ArrayList<String>();
        try {
            JSONArray trains = timetable.getJSONArray(0); // start from 1, 1st is "Train No."
            JSONArray fromTimes = null, toTimes = null;
            for (int i = 1; i < timetable.length(); i++) {
                String station = timetable.getJSONArray(i).getString(0);
                if (station.equals(fromStation)) {
                    fromTimes = timetable.getJSONArray(i);
                } else if (station.equals(toStation)) {
                    toTimes = timetable.getJSONArray(i);
                }
                if (fromTimes != null && toTimes != null) {
                    break;
                }
            }
            
            Boolean all = mAll.isChecked();
            
            for (int i = 1; i < trains.length(); i++) { // 1st is station name or "Train No."
                String f = fromTimes.getString(i);
                String t = toTimes.getString(i);
                
                if (all || (f != "" && t != "")) {
                    schedule.add(trains.getString(i));
                    schedule.add(f);
                    schedule.add(t);
                }
            }
            
        } catch (JSONException e) {
            // XXX should maybe do something about this...
        }
        
        // XXX I'd really like to have a list box with multiple columns, GridView forces everything to same size
        GridView grid = (GridView) findViewById(R.id.SCHEDULE);
        // http://code.google.com/android/reference/android/R.layout.html
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, schedule);// XXX R.layout.cell for custom TextView does not work
        grid.setAdapter(adapter);
    }

    /**
     * Get the schedule for the day and direction as a JSONArray.
     *  
     * @param day
     * @param direction
     * @return
     */
    public JSONArray getTable(String day, String direction) {
        JSONArray timetables = Schedule.getJSONArray();
        JSONArray table = null;
        try {
            if (day.equals(getString(R.string.weekday))) {
                if (direction.equals(getString(R.string.northbound))) {
                    table = timetables.getJSONArray(0);
                } else {
                    table = timetables.getJSONArray(1);
                }
            } else {
                if (direction.equals(getString(R.string.northbound))) {
                    table = timetables.getJSONArray(2);
                } else {
                    table = timetables.getJSONArray(3);
                }
            }
        } catch (JSONException e) {
            // XXX should maybe do something about this
        }
        return table;
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        
        editor.putString(PREFS_DAY, mDay.getText().toString());
        editor.putString(PREFS_DIRECTION, mDirection.getText().toString());

        String fromStation = (String)mFrom.getSelectedItem();
        editor.putString(PREFS_FROM, fromStation);

        String toStation = (String)mTo.getSelectedItem();
        editor.putString(PREFS_TO, toStation);

        Boolean all = mAll.isChecked();
        editor.putBoolean(PREFS_ALL_TRAINS, all);

        editor.commit();        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, R.string.about);
        // XXX See http://www.anddev.org/google_driving_directions_-_mapview_overlayed-t826.html
        //menu.add(0, 1, 0, "Directions to departure station"); XXX TODO - Google Map plus directions from location to station
        //menu.add(0, 2, 0, "System map"); XXX TODO - Google Map + stations colored by zone, linked by lines
        //menu.add(0, 3, 0, "Fares"); XXX TODO - Show fares for current selections at top, all fares below
        //menu.add(0, 4, 0, "Help"); XXX TODO - Show help page that explains buttons and symbols
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
        case 0:
            // XXX customize layout to get multiple rows and maybe clickable project home url
            PackageManager pm = getPackageManager();
            String versionName = "";
            try {
                versionName = pm.getPackageInfo("net.heikkitoivonen.android.caltroid", 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {}
            
            String version = getString(R.string.version);
            version = java.text.MessageFormat.format(version, versionName);
            
            new AlertDialog.Builder(Caltroid.this)
            .setTitle(R.string.about)
            .setMessage(getString(R.string.app_name) + "\n" + version  + "\n" + getString(R.string.timetable_date) + "\n(C) Heikki Toivonen 2008-2009\nheikkitoivonen.net/android/caltroid")
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })
            .show();
            return true;
        }
        return false;
    }
}
