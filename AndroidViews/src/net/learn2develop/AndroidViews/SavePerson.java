/************************************************************************************************************************************************
 * 
 * http://code.google.com/p/jtelmon/source/browse/trunk/AndroidViews/src/net/learn2develop/AndroidViews/SavePerson.java
 * 
 ************************************************************************************************************************************************
 * http://fszlin.dymetis.com/post/2010/05/10/Comsuming-WCF-Services-With-Android.aspx
 * http://localtone.blogspot.com/2009/07/post-json-using-android-and-httpclient.html
 * http://stackoverflow.com/questions/2559948/android-sending-xml-via-http-post-soap
 * http://www.vogella.de/articles/ApacheHttpClient/article.html
 * http://stackoverflow.com/questions/2559948/android-sending-xml-via-http-post-soap
 * http://code.google.com/p/myspace-android/source/browse/trunk/myspace-android/src/com/msdemo/android/myspaceid/MySpaceRESTClient.java
 * http://stackoverflow.com/questions/4032349/how-to-send-a-json-string-to-a-net-rest-service-from-java
 * 
 */

package net.learn2develop.AndroidViews;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import net.learn2develop.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
// import org.json.JSONObject;
import org.json.JSONStringer;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class SavePerson extends Activity {

	private final static String SERVICE_URI = "http://192.168.61.3/RestServicePost/RestServiceImpl.svc";

	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.main);
	        // String plate = new String("test");
	        // POST request to <service>
            HttpPost request = new HttpPost(SERVICE_URI + "/json/adduser");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            
            String not = new String(" ");
            
            // EditText plateEdit = null;
            // Editable plate ;
            // plate =  plateEdit.getText();
            
            try {
            // Build JSON string
            JSONStringer vehicle = new JSONStringer()
                .object()
                    .key("rData")
                        .object()
                            .key("details").value("bar|bob|b@h.us|why")
                        .endObject()
                    .endObject();
            
            StringEntity entity = new StringEntity(vehicle.toString());
            
            Toast.makeText(this, vehicle.toString() + "\n", Toast.LENGTH_LONG).show() ;
            
            request.setEntity(entity);
            
            // Send request to WCF service
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            // Log.d("WebInvoke", "Saving : " + response.getStatusLine().getStatusCode());
            Toast.makeText(this, response.getStatusLine().getStatusCode() + "\n", Toast.LENGTH_LONG).show() ;
            
            }catch (Exception e) {
            	not = "NOT ";
            } 
            
            Toast.makeText(this, not + " OK ! " + "\n", Toast.LENGTH_LONG).show() ;
            
            
	
	}    
}
