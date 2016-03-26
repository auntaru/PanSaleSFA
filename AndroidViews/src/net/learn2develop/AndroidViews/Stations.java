/**
*/

package net.learn2develop.AndroidViews;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class Stations {
    // Values from Wikipedia, could be retrieved automatically
    // Zones added manually, could maybe get automatically from caltrain.com
    public static String[][] stations = {
        // Station,        latitude,    longitude,     zone
        { "San Francisco", "37.776389", "-122.394444", "Zone 1" },
        { "22nd Street",   "37.757222", "-122.3925",   "Zone 1" },
        { "Bayshore",      "37.7075",   "-122.401944", "Zone 1" },
        { "So. San Francisco", "37.655833", "-122.405", "Zone 1" },
        { "San Bruno",     "37.624167", "-122.407778", "Zone 1" },
        { "Millbrae",      "37.600322", "-122.386735", "Zone 2" },
        { "Burlingame",    "37.58",     "-122.345",    "Zone 2" },
        { "San Mateo",     "37.568056", "-122.307222", "Zone 2" },
        { "Hayward Park",  "37.553333", "-122.309444", "Zone 2" },
        { "Hillsdale",     "37.537778", "-122.2975",   "Zone 2" },
        { "Belmont",       "37.521389", "-122.276389", "Zone 2" },
        { "San Carlos",    "37.508056", "-122.260556", "Zone 2" },
        { "Redwood City",  "37.485833", "-122.231389", "Zone 2" },
        { "Atherton",      "37.464167", "-122.197222", "Zone 3" },
        { "Menlo Park",    "37.454722", "-122.182222", "Zone 3" },
        { "Palo Alto",     "37.443611", "-122.165",    "Zone 3" },
        { "California Ave", "37.428889", "-122.141389", "Zone 3" },
        { "San Antonio",   "37.407222", "-122.106944", "Zone 3" },
        { "Mountain View", "37.394394", "-122.075872", "Zone 3" },
        { "Sunnyvale",     "37.378611", "-122.030833", "Zone 3" },
        { "Lawrence",      "37.370556", "-121.996111", "Zone 4" },
        { "Santa Clara",   "37.353488", "-121.936578", "Zone 4" },
        { "College Park",  "37.342778", "-121.915556", "Zone 4" },
        { "San Jose",      "37.329961", "-121.902795", "Zone 4" },
        { "Tamien",        "37.3127",   "-121.884781", "Zone 4" },
        { "Capitol",       "37.283889", "-121.841667", "Zone 5" },
        { "Blossom Hill",  "37.2525",   "-121.797778", "Zone 5" },
        { "Morgan Hill",   "37.129444", "-121.650556", "Zone 6" },
        { "San Martin",    "37.085833", "-121.610556", "Zone 6" },
        { "Gilroy",        "37.004167", "-122.566667", "Zone 6" },
        { null, null, null, null },
    };
    
    /**
     * Get the nearest Caltrain station to us.
     * 
     * @param ctx
     * @return
     */
    public static String nearestStation(Context ctx) {
        // Uncomment to take a screenshot while "locating"...
        /*
        try {
        Thread.sleep(4000);
        } catch (InterruptedException e) {}
        */
        
        LocationManager locationManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
        
        // http://blogoscoped.com/archive/2008-12-15-n14.html 
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // COARSE might be fine in practice
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String provider = locationManager.getBestProvider(criteria, true);
        
        // We don't automatically need to update nearest station, so
        // we just get the last location instead of registering for location
        // updates.
        Location location = locationManager.getLastKnownLocation(provider);
        
        // XXX should just return null if we don't have current location, and let the caller inform the user
        String nearestStation = stations[0][0]; // Always return something

        if (location != null) {
            float shortestDistance = Float.MAX_VALUE;
            
            Location station = new Location("gps"); // actual provider does not matter since we set it below
            
            for (int i = 0; ; i++) {
                if (stations[i][0] == null) break;
                
                station.setLatitude(Double.parseDouble(stations[i][1]));
                station.setLongitude(Double.parseDouble(stations[i][2]));
                float distance = location.distanceTo(station); // distance in meters
                
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestStation = stations[i][0];
                }
            }
        }

        return nearestStation;
    }
}
