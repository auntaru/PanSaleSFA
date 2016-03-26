/*
 * http://pantestmb.blogspot.com/2011/02/web-service-call-with-string-as.html
 * http://stackoverflow.com/questions/2717220/call-webservice-from-android-using-ksoap-simply-returning-error-string
 * http://w3schools.com/webservices/tempconvert.asmx
 * http://w3schools.com/webservices/tempconvert.asmx?op=CelsiusToFahrenheit
 */

package net.learn2develop.AndroidViews;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity; 

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml; 
import android.widget.ArrayAdapter;
import android.widget.Toast;

import net.learn2develop.R;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.xmlpull.v1.XmlPullParserException;



public class Copy2OfLateOrdersByZone extends ListActivity {
	

    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        // String URL = "http://192.168.61.3/TestWeb/PersonPassport4a.asmx";
        // String URL = "http://192.168.61.3/TestWeb/Comenzi.asmx";
        String URL = "http://192.168.61.3/TestWeb/Orders2.asmx";
        //String MethodName = "GetPersonArrayList";
        // ReturnArray
        // ReturnOrdersByZone
        // String MethodName = "ReturnArray";
        String MethodName = "ReturnOrdersByZone";
        String NAMESPACE = "http://tempuri.org/";
        
        // String theZone = "TIMISOARA";
        String theZone = "";
        PropertyInfo pi = new PropertyInfo();

        pi.setName("theZone");
        pi.setValue(theZone);

        
        Order[] allOrders;
        Vector<String> vs = new Vector<String>();
        
        
        allOrders = GetAllOrdersByZone(URL, MethodName, NAMESPACE, pi );

        int nrCmd = allOrders.length;
        //nrCmd=20;
        int index1;
        
        //String Resultn = new String();
        String Result0 = new String();
        //String Result1 = new String();
        //String Result2 = new String();

        for ( index1=0 ; index1<nrCmd; index1++) {
        	Result0  = "\n" + allOrders[index1].get_name() + " : " + allOrders[index1].get_client() + "\n" + allOrders[index1].get_control();
        	// presidents[index1] = allOrders[index1].get_name();
        	vs.add(new String(Result0));

        	// Result0 = "" + allOrders[index1].get_name() + " : " + allOrders[index1].get_client() + "" + allOrders[index1].get_control();
        	//presidents[index1] = new String(Result0);
        	System.out.println(Result0);
        }
        //Result0 = "\n" + allOrders[0].get_name() + " : " + allOrders[0].get_client() + "\n" + allOrders[0].get_control();
        //Result1 = "\n" + allOrders[1].get_name() + " : " + allOrders[1].get_client() + "\n" + allOrders[1].get_control();
        //Result2 = "\n" + allOrders[2].get_name() + " : " + allOrders[2].get_client() + "\n" + allOrders[2].get_control();

        //Resultn = "\n" + Result0 + "\n" + Result1 + "\n" + Result2 ;
        
        // System.out.println(Resultn);
        // System.out.println(Result1);

        //presidents[0]=Result0;
        //presidents[1]=Result1;
        //presidents[2]=Result2;

        
        // Toast.makeText(this, "ZONA: " + theZone + Resultn + "\n", Toast.LENGTH_LONG).show() ; 

        
        int count = vs.size();
        String[] presidents = new String[count];
        vs.copyInto(presidents); 
        
        // vs.toArray(presidents);
        
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, presidents));

    }

    
    public static Order[] GetAllOrdersByZone(String URL, String MethodName, String NAMESPACE, PropertyInfo pi )
    {
        SoapObject response = InvokeMethod(URL,MethodName, NAMESPACE, pi );
        return retrieveOrdersFromSoap(response);
        
    }

    public static SoapObject InvokeMethod(String URL, String MethodName, String NAMESPACE, PropertyInfo pi)
    {
        SoapObject request = GetSoapObject(MethodName,NAMESPACE);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = GetEnvelope(request);
        
        return  MakeCall(URL,envelope,NAMESPACE,MethodName);
    }    
    
    
    public static Order[] retrieveOrdersFromSoap(SoapObject soap)
    {
        Order[] orders  = new Order[soap.getPropertyCount()];
        for (int i = 0; i < orders.length; i++) {
            SoapObject pii = (SoapObject)soap.getProperty(i);
            Order passport = new Order();
            passport.set_name( pii.getProperty(0).toString()) ;
            passport.set_surname(pii.getProperty(1).toString());
            passport.set_zone(pii.getProperty(2).toString());
            passport.set_client(pii.getProperty(3).toString());
            passport.set_control(pii.getProperty(4).toString());
            orders[i]  = passport;
        }
        return orders;
    }
    
    
    public static SoapObject GetSoapObject(String MethodName, String NAMESPACE)
    {
        return new SoapObject(NAMESPACE,MethodName);
    }

    public static SoapSerializationEnvelope GetEnvelope(SoapObject Soap)
    {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(Soap);
        return envelope;
    }    
    
    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME)
    {
        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        try
            {
        		Envelope.addMapping(NAMESPACE, Order.ORDER_CLASS.getSimpleName(), Order.ORDER_CLASS);
        	 	androidHttpTransport.call(NAMESPACE + METHOD_NAME, Envelope);
                SoapObject response = (SoapObject)Envelope.getResponse();
                return response;
            }
         catch(Exception e)
         {
             e.printStackTrace();
             
         }
         return null;
    }
    
}


/*
 * http://seesharpgears.blogspot.com/2010/10/web-service-that-returns-array-of.html
 * http://seesharpgears.blogspot.com/2010/10/ksoap-android-web-service-tutorial-with.html
 * http://seesharpgears.blogspot.com/2010/11/implementing-ksoap-marshal-interface.html
 * http://seesharpgears.blogspot.com/2010/11/basic-ksoap-android-tutorial.html
 * http://bimbim.in/post/2010/10/08/Android-Calling-Web-Service-with-complex-types.aspx
 * http://romenlaw.blogspot.com/2008/08/consuming-web-services-from-android.html
 * http://www.telerik.com/help/aspnet-ajax/combo_loadondemandwebservice.html
 * http://www.daniweb.com/forums/thread151766.html
 */

