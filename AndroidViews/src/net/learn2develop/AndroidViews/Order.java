package net.learn2develop.AndroidViews;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class Order implements KvmSerializable
{
	public static Class ORDER_CLASS = Order.class;
	private String _name = "";
	private String _surname = "";
	private String _zone = "";
	private String _client = "";	
	private String _control = "";

	public Order()
	{
	}

	public Order(SoapObject obj)
	{
		this._name = obj.getProperty("Name").toString();
		this._surname = obj.getProperty("Surmane").toString();
		this._zone = obj.getProperty("Zone").toString();
		this._client = obj.getProperty("Client").toString();
		this._control = obj.getProperty("Control").toString();
		
	}

	public String get_name()
	{
		return _name;
	}

	public String get_surname()
	{
		return _surname;
	}
	
	public String get_zone()
	{
		return _zone;
	}

	public String get_client()
	{
		return _client;
	}

	public String get_control()
	{
		return _control;
	}
	
	
	
	public void set_name(String name)
	{
		_name = name;
	}

	public void set_surname(String surname)
	{
		_surname = surname;
	}

	public void set_zone(String zone)
	{
		_zone = zone;
	}
	
	public void set_client(String client)
	{
		_client = client;
	}

	public void set_control(String control)
	{
		_control = control;
	}
	
	

	//  @Override
	public Object getProperty(int index)
	{
		Object object = null;
		switch (index)
		{
		case 0:
		{
			object = this._name;
			break;
		}
		case 1:
		{
			object = this._surname;
			break;
		}
		case 2:
		{
			object = this._zone;
			break;
		}
		case 3:
		{
			object = this._client;
			break;
		}
		case 4:
		{
			object = this._control;
			break;
		}
		}
		return object;
	}

	// @Override
	public int getPropertyCount()
	{
		return 5;
	}

	// //@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo propertyInfo)
	{
		switch (index)
		{
		case 0:
		{
			propertyInfo.name = "Name";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 1:
		{
			propertyInfo.name = "Surname";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 2:
		{
			propertyInfo.name = "Zone";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 3:
		{
			propertyInfo.name = "Client";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 4:
		{
			propertyInfo.name = "Control";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		}
	}

	// @Override
	public void setProperty(int index, Object obj)
	{
		switch (index)
		{
		case 0:
		{
			this._name = obj.toString();
			break;
		}
		case 1:
		{
			this._surname = obj.toString();
			break;
		}
		case 2:
		{
			this._zone = obj.toString();
			break;
		}
		case 3:
		{
			this._client = obj.toString();
			break;
		}
		case 4:
		{
			this._control = obj.toString();
			break;
		}

		}
	}
}
