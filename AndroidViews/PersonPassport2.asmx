<%@ WebService class = "PersonPassport2" Language="C#" Debug = "true"%>

using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.Services;

public class Person

{

    private string _name = string.Empty;

    private int _age = 0;

    private float _salary = 100000.0f;

    private System.DateTime _dob = new DateTime(1980, 01, 15);

 
  public Person() {}

  public Person(string firstname, string lastname, DateTime dob)
    {
        this._name = firstname+lastname;
        this._dob = dob;
    }


    public float Salary

    {

        get { return _salary; }

        set { _salary = value; }

    }

    public System.DateTime Dob

    {

        get { return _dob; }

        set { _dob = value; }

    }

 

    public int Age

    {

        get { return _age; }

        set { _age = value; }

    }

    public string Name

    {

        get { return _name; }

        set { _name = value; }

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

 [WebMethod]
    public Person GetSingle()
    {
        Person person = new Person();
        person.Name = "bimbim.in";
        person.Age = 30;
        person.Dob = new System.DateTime(1980, 01, 15);
        person.Salary = 50000f;
        return person;
    }

[WebMethod]
    public string HelloWorld()
    {
        return "Hello World";
    }

}