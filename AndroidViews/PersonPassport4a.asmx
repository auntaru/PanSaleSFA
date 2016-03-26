<%@ WebService class = "PersonPassport2" Language="C#" Debug = "true"%>

using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.SqlClient;
using System.Web.Services;


public class Person

{

    private string _name = string.Empty;
    private string _user_name = string.Empty;
    private string _eMaiL = string.Empty;
    private string _password = string.Empty;

  public Person() {}


 

    public string Name

    {

        get { return _name; }

        set { _name = value; }

    }

    
        public string Password

    {

        get { return _password; }

        set { _password = value; }

    }

    
    
    public string UserName

    {

        get { return _user_name; }

        set { _user_name = value; }

    }

    public string EMail

    {

        get { return _eMaiL; }

        set { _eMaiL = value; }

    }

         

}

public class PersonPassport2 : WebService
{
const string connStr = "server=localhost;uid=sa;pwd=kash;database=minipassport";

[WebMethod(Description = "Method to Authenticate Users")]
public bool Authenticate(string username, string password)
{
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "Select password from users where username = '" + username + "';";
dbConn.Open();
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
SqlDataReader dbReader = dbCommand.ExecuteReader();

bool returnBool;
if (dbReader.Read())
{
if (dbReader[0].ToString()==password)
{
returnBool = true;
}
else
{
returnBool = false;
}
}
else
{
returnBool=false;
}
dbReader.Close();
dbConn.Close();
return returnBool;
}

[WebMethod(Description = "Method to Add User")]
public bool AddUser(string username, string password, string name, string email)
{
bool returnBool = false;
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "INSERT INTO users(username,password,name,email) values('" + username + "', '" + password + "', '" + name + "', '" + email + "');";
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
try
{
dbConn.Open();
if (dbCommand.ExecuteNonQuery()!=0)
{
returnBool=true;
}
returnBool=true;
}
catch
{
returnBool=false;
}
dbConn.Close();
return returnBool;
}

[WebMethod(Description = "Method to Delete User")]
public bool DeleteUser(string username)
{
bool returnBool = false;
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "DELETE FROM users where username = '" + username +"';";
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
try
{
dbConn.Open();
if (dbCommand.ExecuteNonQuery()!=0)
{
returnBool=true;
}
}
catch
{
returnBool=false;
}
dbConn.Close();
return returnBool;
}

[WebMethod(Description = "Method to Edit User Information")]
public bool EditUser(string username, string name, string email)
{
bool returnBool = false;
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "UPDATE users SET username = '" + username +"',name = '"+name+"',email= '"+email+"';";
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
try
{
dbConn.Open();
if (dbCommand.ExecuteNonQuery()!=0)
{
returnBool=true;
}
}
catch
{
returnBool=false;
}
dbConn.Close();
return returnBool;
}

[WebMethod(Description = "Method to Change User Password")]
public bool ChangePassword(string username, string password)
{
bool returnBool = false;
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "UPDATE users SET password = '"+password+"';";
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
try
{
dbConn.Open();
if (dbCommand.ExecuteNonQuery()!=0)
{
returnBool=true;
}
}
catch
{
returnBool=false;
}
dbConn.Close();
return returnBool;
}

[WebMethod(Description = "Method to Obtain User Name")]
public string ReturnName(string username)
{
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "Select Name from users where username = '" + username + "';";
dbConn.Open();
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
SqlDataReader dbReader = dbCommand.ExecuteReader();
dbReader.Read();
string _name = dbReader[0].ToString();
dbReader.Close();
dbConn.Close();
return _name;
}

[WebMethod(Description = "Method to obtain User Email Address")]
public string ReturnEmail(string username)
{
SqlConnection dbConn = new SqlConnection(connStr);
string sqlStr = "Select email from users where username = '" + username + "';";
dbConn.Open();
SqlCommand dbCommand = new SqlCommand(sqlStr,dbConn);
SqlDataReader dbReader = dbCommand.ExecuteReader();
dbReader.Read();
string _name = dbReader[0].ToString();
dbReader.Close();
dbConn.Close();
return _name;
}

[WebMethod(Description = "Method to obtain All User Info")]
public DataSet ReturnAll()
{
    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();
    string sqlSelect = "select * from users ";
    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataSet ds = new DataSet();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(ds, "users");
    dbConn.Close();
    return ds;
}

[WebMethod(Description = "Method to obtain All User Info")]
public Person[] ReturnArray()
{
    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();
    string sqlSelect = "select * from users ";
    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataTable dt = new DataTable();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(dt);	
    dbConn.Close();
	List<Person> list = new List<Person>();
	foreach(DataRow row in dt.Rows) {
		// Person target = Activator.CreateInstance<Person>();
        Person target = new Person();
        target.Name = row["Name"].ToString();
        target.UserName = row["UserName"].ToString();
        target.EMail = row["EMail"].ToString();
        target.Password = row["Password"].ToString();
		// DataColumnAttribute.Bind(row,target);
		list.Add(target);
	}
	return list.ToArray();
}


[WebMethod]
public ArrayList GetPersonArrayList()
{
    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();
    string sqlSelect = "select * from users ";
    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataTable dt = new DataTable();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(dt);
    dbConn.Close();
    ArrayList myList = new ArrayList();
    foreach (DataRow row in dt.Rows)
    {
        Person target = new Person();
        target.Name = row["Name"].ToString();
        target.UserName = row["UserName"].ToString();
        target.EMail = row["EMail"].ToString();
        target.Password = row["Password"].ToString();
        myList.Add(target);
    }
    return myList;
}  
    
    
    
[WebMethod]
public List<Person> GetPersonList()
{
    SqlConnection dbConn = new SqlConnection(connStr);
    dbConn.Open();
    string sqlSelect = "select * from users ";
    SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
    DataTable dt = new DataTable();
    SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
    da.Fill(dt);
    dbConn.Close();
    List<Person> myList = new List<Person>();
    foreach (DataRow row in dt.Rows)
    {
        Person target = new Person();
        target.Name = row["Name"].ToString();
        target.UserName = row["UserName"].ToString();
        target.EMail = row["EMail"].ToString();
        target.Password = row["Password"].ToString();
        myList.Add(target);
    }
    return myList;
}  
    

 [WebMethod]
    public Person GetSingle()
    {
        Person person = new Person();
        person.Name = "bimbim.in";
        //person.Age = 30;
        //person.Dob = new System.DateTime(1980, 01, 15);
        //person.Salary = 50000f;
        return person;
    }

[WebMethod]
    public string HelloWorld()
    {
        return "Hello World";
    }

}