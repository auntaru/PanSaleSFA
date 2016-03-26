<%@ WebService class = "ReturnOrders" Language="C#" Debug = "true"%>

using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.SqlClient;
using System.Web.Services;

public class Order
{

    private string _name     = string.Empty;
    private string _surname  = string.Empty;
    private string _zone     = string.Empty;
    private string _client   = string.Empty;
    private string _control  = string.Empty;
    
    public Order() {}

    public string Name
    {
        get { return _name; }
        set { _name = value; }
    }
  
    public string Zone
    {
        get { return _zone; }
        set { _zone = value; }
    }
    
    public string Surname
    {
        get { return _surname; }
        set { _surname = value; }
    }

    public string Client
    {
        get { return _client; }
        set { _client = value; }
    }

    public string Control
    {
        get { return _control; }
        set { _control = value; }
    }
    
}


public class SalesAgent
{
    private string _name     = string.Empty;
    private string _surname  = string.Empty;
    private string _userID   = string.Empty;
    private string _zoneID   = string.Empty;
    private string _zoneName = string.Empty;
    private string _password = string.Empty;
    
    public SalesAgent(){}

    public string Name
    {
        get { return _name;  }
        set { _name = value; }
    }

    public string Surname
    {
        get { return _surname;  }
        set { _surname = value; }
    }

    public string UserID
    {
        get { return _userID;  }
        set { _userID = value; }
    }

    public string ZoneID
    {
        get { return _zoneID;  }
        set { _zoneID = value; }
    }

    public string ZoneName
    {
        get { return _zoneName; }
        set { _zoneName = value; }
    }
    
    public string Password
    {
        get { return _password;  }
        set { _password = value; }
    }

}



public class ReturnOrders : WebService
{
   const string connStr = "server=192.168.61.188;uid=sa;pwd=1qt;database=Pg";


[WebMethod(Description = "Method to obtain All User Info")]
public Order[] ReturnArray()
{

    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();
    
    string sqlSelect = " SELECT TOP (100) dbo.PersoanaFizica.Nume as Name , dbo.PersoanaFizica.Prenume as Surname, dbo.Zona.Denumire AS Zone, dbo.Persoana.Denumire AS Client , dbo.[Document].Control " +
                       " FROM dbo.[Document] INNER JOIN " +
                       " dbo.PersoanaFizica ON dbo.[Document].Utilizator_ID = dbo.PersoanaFizica.PersoanaFizica_ID INNER JOIN " +
                       " dbo.Persoana ON dbo.[Document].Persoana_ID = dbo.Persoana.PersoanaID INNER JOIN " +
                       " dbo.Comanda ON dbo.[Document].DocumentID = dbo.Comanda.Comanda_ID INNER JOIN  " +
                       " dbo.Utilizator ON dbo.[Document].Utilizator_ID = dbo.Utilizator.UtilizatorID INNER JOIN " +
                       " dbo.Zona ON dbo.Utilizator.ZonaID = dbo.Zona.Id " +
                       " ORDER BY Control DESC ";    
    
    
    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataTable dt = new DataTable();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(dt);	
    dbConn.Close();
    List<Order> list = new List<Order>();
	foreach(DataRow row in dt.Rows) {
		// Person target = Activator.CreateInstance<Person>();
        Order target = new Order();
        target.Name = row["Name"].ToString();
        target.Surname = row["Surname"].ToString();
        target.Zone = row["Zone"].ToString();
        target.Client = row["Client"].ToString();
        target.Control = row["Control"].ToString();
		list.Add(target);
	}
	return list.ToArray();
}

[WebMethod(Description = "Method to obtain Orders By Zone")]
public Order[] ReturnOrdersByZone(string theZone)
{

    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();

    string sqlSelect = " SELECT TOP (100) dbo.PersoanaFizica.Nume as Name , dbo.PersoanaFizica.Prenume as Surname, dbo.Zona.Denumire AS Zone, dbo.Persoana.Denumire AS Client , dbo.[Document].Control " +
                       " FROM dbo.[Document] INNER JOIN " +
                       " dbo.PersoanaFizica ON dbo.[Document].Utilizator_ID = dbo.PersoanaFizica.PersoanaFizica_ID INNER JOIN " +
                       " dbo.Persoana ON dbo.[Document].Persoana_ID = dbo.Persoana.PersoanaID INNER JOIN " +
                       " dbo.Comanda ON dbo.[Document].DocumentID = dbo.Comanda.Comanda_ID INNER JOIN  " +
                       " dbo.Utilizator ON dbo.[Document].Utilizator_ID = dbo.Utilizator.UtilizatorID INNER JOIN " +
                       " dbo.Zona ON dbo.Utilizator.ZonaID = dbo.Zona.Id " +
                       " WHERE  (dbo.Zona.Denumire LIKE" + "'%" + theZone + "%')" +
                       " ORDER BY Control DESC ";


    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataTable dt = new DataTable();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(dt);
    dbConn.Close();
    List<Order> list = new List<Order>();
    foreach (DataRow row in dt.Rows)
    {
        Order target = new Order();
        target.Name = row["Name"].ToString();
        target.Surname = row["Surname"].ToString();
        target.Zone = row["Zone"].ToString();
        target.Client = row["Client"].ToString();
        target.Control = row["Control"].ToString();
        list.Add(target);
    }
    return list.ToArray();
}


/*
SELECT     TOP (100) PERCENT dbo.PersoanaFizica.PersoanaFizica_ID, dbo.PersoanaFizica.Nume, dbo.PersoanaFizica.Prenume, dbo.PersoanaFizica.CNP, 
            dbo.PersoanaFizica.BI, dbo.Utilizator.UtilizatorID, dbo.Utilizator.Parola, dbo.Utilizator.SefID, dbo.Utilizator.ZonaID
FROM        dbo.PersoanaFizica INNER JOIN
            dbo.Utilizator ON dbo.PersoanaFizica.PersoanaFizica_ID = dbo.Utilizator.UtilizatorID
ORDER BY dbo.PersoanaFizica.Nume, dbo.PersoanaFizica.Prenume

 
====================================================================
 
 SELECT     TOP (100) PERCENT dbo.PersoanaFizica.PersoanaFizica_ID, dbo.PersoanaFizica.Nume, 
			dbo.PersoanaFizica.Prenume, dbo.PersoanaFizica.CNP, 
            dbo.PersoanaFizica.BI, dbo.Utilizator.UtilizatorID, dbo.Utilizator.Parola, 
				dbo.Utilizator.SefID, dbo.Utilizator.ZonaID, Zona.Denumire
FROM        dbo.PersoanaFizica , dbo.Zona , dbo.Utilizator 
WHERE 
  dbo.PersoanaFizica.PersoanaFizica_ID = dbo.Utilizator.UtilizatorID and
  dbo.Utilizator.ZonaID = dbo.Zona.Id and dbo.Utilizator.ZonaID = 18 
ORDER BY dbo.PersoanaFizica.Nume, dbo.PersoanaFizica.Prenume  
 
 */

[WebMethod(Description = "Method to obtain SalesAgents by Zone or by Name")]
public SalesAgent[] ReturnSalesAgents(string theZone, string theAgent)
{

    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();
    string sqlSelect = " ";
    if ( theZone.Equals("") && theAgent.Equals("") ) {
    sqlSelect = " SELECT TOP (100) dbo.PersoanaFizica.Nume as Name, dbo.PersoanaFizica.Prenume as Surname,  " +
                       " dbo.Utilizator.UtilizatorID as UserID, dbo.Utilizator.Parola as Password, dbo.Utilizator.ZonaID as ZoneID, " +
                       " dbo.Zona.Denumire as ZoneName " + 
                       " FROM dbo.PersoanaFizica , dbo.Zona , dbo.Utilizator  " +
                       " WHERE dbo.PersoanaFizica.PersoanaFizica_ID = dbo.Utilizator.UtilizatorID and " +
                       " dbo.Utilizator.ZonaID = dbo.Zona.Id " +
                       " ORDER BY dbo.PersoanaFizica.Nume, dbo.PersoanaFizica.Prenume ";
    }
    else if (theAgent.Equals(""))
    {
        sqlSelect = " SELECT TOP (100) dbo.PersoanaFizica.Nume as Name, dbo.PersoanaFizica.Prenume as Surname,  " +
                           " dbo.Utilizator.UtilizatorID as UserID, dbo.Utilizator.Parola as Password, dbo.Utilizator.ZonaID as ZoneID, " +
                           " dbo.Zona.Denumire as ZoneName" +
                           " FROM dbo.PersoanaFizica , dbo.Zona , dbo.Utilizator  " +
                           " WHERE dbo.PersoanaFizica.PersoanaFizica_ID = dbo.Utilizator.UtilizatorID and " +
                           " dbo.Utilizator.ZonaID = dbo.Zona.Id and " +
                           " dbo.Zona.Denumire LIKE" + "'%" + theZone + "%'" +
                           " ORDER BY dbo.PersoanaFizica.Nume, dbo.PersoanaFizica.Prenume ";
    }
    else {
        sqlSelect = " SELECT TOP (100) dbo.PersoanaFizica.Nume as Name, dbo.PersoanaFizica.Prenume as Surname,  " +
                           " dbo.Utilizator.UtilizatorID as UserID, dbo.Utilizator.Parola as Password, dbo.Utilizator.ZonaID as ZoneID, " +
                           " dbo.Zona.Denumire as ZoneName" +
                           " FROM dbo.PersoanaFizica , dbo.Zona , dbo.Utilizator  " +
                           " WHERE dbo.PersoanaFizica.PersoanaFizica_ID = dbo.Utilizator.UtilizatorID and " +
                           " dbo.Utilizator.ZonaID = dbo.Zona.Id and " +
                           " ( dbo.PersoanaFizica.Nume LIKE " + "'%" + theAgent + "%' or dbo.PersoanaFizica.Prenume LIKE " + "'%" + theAgent + "%' )";
    }
    
    
    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataTable dt = new DataTable();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(dt);
    dbConn.Close();
    List<SalesAgent> list = new List<SalesAgent>();
    foreach (DataRow row in dt.Rows)
    {
        SalesAgent target = new SalesAgent();
        target.Name = row["Name"].ToString();
        target.Surname = row["Surname"].ToString();
        target.ZoneID = row["ZoneID"].ToString();
        target.UserID = row["UserID"].ToString();
        target.Password = row["Password"].ToString();
        target.ZoneName = row["ZoneName"].ToString();
        list.Add(target);
    }
    return list.ToArray();
}
    
[WebMethod]
    public string HelloWorld()
    {
        return "Hello World";
    }

}
