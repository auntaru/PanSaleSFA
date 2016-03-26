using System;
using System.Data;
using System.Data.SqlClient;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace RestService
{
    public class RestServiceImpl : IRestServiceImpl
    {
        #region IRestServiceImpl Members

        const string connStr = "server=localhost;uid=sa;pwd=kash;database=minipassport";

        public string XMLData(string id)
        {
            return "You requested product " + id;
        }

        public string JSONData(string id)
        {
            return "You requested product " + id;
        }


        public string ReturnEmail(string user)
        {
            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "Select email from users where username = '" + user + "';";
            dbConn.Open();
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            SqlDataReader dbReader = dbCommand.ExecuteReader();
            dbReader.Read();
            string _name = dbReader[0].ToString();
            dbReader.Close();
            dbConn.Close();
            return _name;
        }

        public PersonData AddUser(RequestData rData)
        {
            bool returnBool = false;

            var data = rData.details.Split('|');
            var response = new PersonData
            {
                Name = data[0],
                User = data[1],
                Email = data[2],
                Password = data[3]
            };

            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "INSERT INTO users(username,name,email,password) values('" + data[0] + "', '" + data[1] + "', '" + data[2] + "', '" + data[3] + "');";
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            try
            {
                dbConn.Open();
                if (dbCommand.ExecuteNonQuery() != 0)
                {
                    returnBool = true;
                }
                dbConn.Close();
            }
            catch
            {
                returnBool = false;
            }
            return response;
        }

        public PersonData AddJsonUser(RequestData rData)
        { 
            return AddUser(rData);
        }
        
        public PersonData[] getAllPersons()
        {
            SqlConnection dbConn = new SqlConnection(connStr);
            dbConn.Open();
            string sqlSelect = "select * from users ";
            SqlDataAdapter da = new SqlDataAdapter(sqlSelect, dbConn);
            DataTable dt = new DataTable();
            SqlCommand dbCommand = new SqlCommand(sqlSelect, dbConn);
            da.Fill(dt);
            dbConn.Close();
            List<PersonData> list = new List<PersonData>();
            foreach (DataRow row in dt.Rows)
            {
                // Person target = Activator.CreateInstance();
                PersonData target = new PersonData();
                target.Name = row["Name"].ToString();
                target.User = row["UserName"].ToString();
                target.Email = row["EMail"].ToString();
                target.Password = row["Password"].ToString();
                // DataColumnAttribute.Bind(row,target);
                list.Add(target);
            }
            return list.ToArray();
        }

        public PersonData[] getJsonPersons()
        {
            return getAllPersons();
        }


        public bool DeletePerson(string user)
        {
            bool returnBool = false;
            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "DELETE FROM users where username = '" + user + "';";
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            try
            {
                dbConn.Open();
                if (dbCommand.ExecuteNonQuery() != 0)
                {
                    returnBool = true;
                }
            }
            catch
            {
                returnBool = false;
            }
            dbConn.Close();
            return returnBool;
        }

        public bool UpdateUser(string user, PersonData pd)
        {
            bool returnBool = false;
            string username, name, email, password;
            username = pd.User;
            name = pd.Name;
            email = pd.Email;
            password = pd.Password;
            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "UPDATE users SET username = '" + username + "',name = '" + name + "',password = '" + password + "',email= '" + email + "' WHERE username = '" + user + "' ;";
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            try
            {
                dbConn.Open();
                if (dbCommand.ExecuteNonQuery() != 0)
                {
                    returnBool = true;
                }
            }
            catch
            {
                returnBool = false;
            }
            dbConn.Close();
            return returnBool;
        }


        public bool UpdateUserPut(string user, RequestData rData)
        {
            bool returnBool = false;
            var data = rData.details.Split('|');
            var pd = new PersonData
            {
                Name = data[0],
                User = data[1],
                Email = data[2],
                Password = data[3]
            };
            string username, name, email, password;
            username = pd.User;
            name = pd.Name;
            email = pd.Email;
            password = pd.Password;
            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "UPDATE users SET username = '" + username + "',name = '" + name + "',password = '" + password + "',email= '" + email + "' WHERE username = '" + user + "' ;";
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            try
            {
                dbConn.Open();
                if (dbCommand.ExecuteNonQuery() != 0)
                {
                    returnBool = true;
                }
                dbConn.Close();
            }
            catch
            {
                returnBool = false;
            }
            return returnBool;
        }


        public bool UpdateUserPost(PersonData pd)
        {
            bool returnBool = false;
            string username, name, email, password;
            username = pd.User;
            name = pd.Name;
            email = pd.Email;
            password = pd.Password;
            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "UPDATE users SET username = '" + username + "',name = '" + name + "',password = '" + password + "',email= '" + email + "' WHERE username = '" + username + "' ;";
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            try
            {
                dbConn.Open();
                if (dbCommand.ExecuteNonQuery() != 0)
                {
                    returnBool = true;
                }
                dbConn.Close();
            }
            catch
            {
                returnBool = false;
            }
            return returnBool;
        }



        public bool AddUser2(RequestData rData)
        {
            bool returnBool = false;

            var data = rData.details.Split('|');
            var response = new PersonData
            {
                Name = data[0],
                User = data[1],
                Email = data[2],
                Password = data[3]
            };

            SqlConnection dbConn = new SqlConnection(connStr);
            string sqlStr = "INSERT INTO users(username,name,email,password) values('" + data[0] + "', '" + data[1] + "', '" + data[2] + "', '" + data[3] + "');";
            SqlCommand dbCommand = new SqlCommand(sqlStr, dbConn);
            try
            {
                dbConn.Open();
                if (dbCommand.ExecuteNonQuery() != 0)
                {
                    returnBool = true;
                }
                returnBool = true;
            }
            catch
            {
                returnBool = false;
            }
            dbConn.Close();
            return returnBool;
        }

        public PersonData TestPersonDataPost(PersonData pd)
        {
            var response = new PersonData
            {
                Name = pd.Name,
                User = pd.User,
                Email = pd.Email,
                Password = pd.Password
            };
            
            return response;
            // return pd;
        }

        public PersonData JsonTestPersonDataPost(PersonData pd)
        { 
            return TestPersonDataPost(pd);
        }


        #endregion



       
    }
}
