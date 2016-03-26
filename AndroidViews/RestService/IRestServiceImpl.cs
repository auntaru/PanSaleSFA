/*
 * 
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
 * http://msdn.microsoft.com/en-us/library/dd203052.aspx
 * 
 * 

  http://www.eggheadcafe.com/tutorials/wcf/b480ba4e-b59c-43d4-ac4b-2990ca19daec/restful-aspnet-wcf--jquery--json-service-with-get-post-put-and-delete.aspx
  
The following HTTP verbs may be used when creating RESTful services:
GET - Requests a specific representation of a resource
PUT - Create or update a resoure with the supplied representation
DELETE - Delete the specified resource
POST - Submit data to be processed by the identified resource
HEAD - Similar to GET but only retrieves headers
OPTIONS - Returns the methods supported by the identified resource
  
 * 
 * REST in WCF – Part VII (HI-REST – Implementing Insert and Update)
 * http://www.robbagby.com/rest/rest-in-wcf-part-vii-hi-rest-implementing-insert-and-update/
 * 
 * Modeling Insert and Update the HTTP way
 * 
Most folks I talk to view the HTTP POST as in insert and PUT as an update.  However, I have met more than a few that see things the other way around.  
 * I have even seen some folks pull out the verb PATCH.  Part of the challenge with this seemingly unanswerable debate is that HTTP was developed to deal
 * with documents and when we discuss these verbs we are usually thinking about data (rows and columns).  At least that is my thought.
Well, if I am going to provide an implementation in this post, I am going to have to make a decision on how I want to handle the HTTP verbs.  
 * To start, I am going to need a bit of help from the HTTP specs.  Here are some excerpts from POST and PUT:
PUT- "The PUT method requests that the enclosed entity be stored under the supplied Request-URI. If the Request-URI refers to an already existing resource, 
 * the enclosed entity SHOULD be considered as a modified version of the one residing on the origin server. If the Request-URI does not point to an existing resource,
 * and that URI is capable of being defined as a new resource by the requesting user agent, the origin server can create the resource with that URI. 
 * If a new resource is created, the origin server MUST inform the user agent via the 201 (Created) response. If an existing resource is modified, 
 * either the 200 (OK) or 204 (No Content) response codes SHOULD be sent to indicate successful completion of the request." (1)
POST – "The POST method is used to request that the origin server accept the entity enclosed in the request as a new subordinate of the resource 
 * identified by the Request-URI in the Request-Line. POST is designed to allow a uniform method to cover the following functions: 
       – Annotation of existing resources; 
      – Posting a message to a bulletin board, newsgroup, mailing list, 
        or similar group of articles; 
      – Providing a block of data, such as the result of submitting a 
        form, to a data-handling process; 
      – Extending a database through an append operation."(1)
The way I read this, in the event that a resource does not exist, a PUT will perform an insert.  
 * In the event that a resource already exists at the designated URI, the resource is modified (or updated).  With regards to a POST, 
 * it is designed for operations that I would describe as append.  Again, by simply writing this passage, I have opened myself up to endless debate. 
 * There are those that will state that if the resource exists, what really is happening is a delete and an insert and that is != to an Update.  
 * In my (probably naive) opinion, as long as you have a logical basis for your API design and you are consistent, you will be able to defend its efficacy. 
 * So, given this explanation, I have decided that I will use PUT for both insert and update in my implementation.
 * 
*/

using System.ServiceModel;
using System.ServiceModel.Web;

namespace RestService
{
    
    [ServiceContract]
    public interface IRestServiceImpl
    {

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "json/{id}")]
        string JSONData(string id);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "xml/{user}")]
        string ReturnEmail(string user);


        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "xml/getallpersons")]
        PersonData[] getAllPersons();

/*
         GET :
        http://192.168.61.3/RestServicePost/RestServiceImpl.svc/xml/getallpersons

        => 

        HTTP/1.1 200 OK
        Content-Length: 1195
        Content-Type: application/xml; charset=utf-8
        Server: Microsoft-IIS/7.0
        X-Powered-By: ASP.NET
        Date: Tue, 25 Oct 2011 16:20:15 GMT

        <getAllPersonsResponse xmlns="http://tempuri.org/">
        <getAllPersonsResult xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
        <PersonData xmlns="">
        <Email>asmith@gmail.com</Email><Name>smith</Name><Password>secret</Password><User>adrian</User></PersonData><PersonData xmlns="">
        <Email>bobaba@asdcaswhitehouse.us</Email><Name>bobama2bobama </Name><Password>egrmiti</Password><User>barack789</User></PersonData><PersonData xmlns="">
        <Email>jblack@white.com</Email><Name>bjack </Name><Password>negro</Password><User>black</User></PersonData><PersonData xmlns="">
        <Email>solo@band.com</Email><Name>Celine</Name><Password>myheart</Password><User>dion</User></PersonData><PersonData xmlns="">
        <Email>n@m.com</Email><Name>nero</Name><Password>pass1</Password><User>don</User></PersonData><PersonData xmlns="">
        <Email>lgaga@gmail.com</Email><Name>lgaga</Name><Password>gagagaga</Password><User>LadyG </User></PersonData><PersonData xmlns="">
        <Email>steven@domain.org</Email><Name>sgates</Name><Password>bigsecret</Password><User>Steven</User></PersonData><PersonData xmlns="">
        <Email>em1</Email><Name>n1</Name><Password>pass1</Password><User>user1</User></PersonData>
        </getAllPersonsResult>
        </getAllPersonsResponse>
*/

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "json/getallpersons")]
        PersonData[] getJsonPersons();

        /*
 
        GET : 
        http://192.168.61.3/RestServicePost/RestServiceImpl.svc/json/getallpersons

        => 

        HTTP/1.1 200 OK
        Content-Length: 659
        Content-Type: application/json; charset=utf-8
        Server: Microsoft-IIS/7.0
        X-Powered-By: ASP.NET
        Date: Tue, 25 Oct 2011 16:17:55 GMT

        {"getJsonPersonsResult":[{"Email":"asmith@gmail.com","Name":"smith","Password":"secret","User":"adrian"},
        {"Email":"bobaba@asdcaswhitehouse.us","Name":"bobama2bobama ","Password":"egrmiti","User":"barack789"},
        {"Email":"jblack@white.com","Name":"bjack ","Password":"negro","User":"black"},
        {"Email":"solo@band.com","Name":"Celine","Password":"myheart","User":"dion"},
        {"Email":"n@m.com","Name":"nero","Password":"pass1","User":"don"},
        {"Email":"lgaga@gmail.com","Name":"lgaga","Password":"gagagaga","User":"LadyG "},
        {"Email":"steven@domain.org","Name":"sgates","Password":"bigsecret","User":"Steven"},
        {"Email":"em1","Name":"n1","Password":"pass1","User":"user1"}]}

         */

        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "adduser")]
        PersonData AddUser(RequestData rData);

/*
********************************************************************************************

http://localhost/RestServicePost/RestServiceImpl.svc/adduser

User-Agent: Fiddler
Host: localhost
Content-Type: application/xml

<RequestData>
<details>barack|bobama|bobaba@whitehouse.us|imtwb</details>
</RequestData>          
          
********************************************************************************************
*/


        [OperationContract]
        [WebInvoke(Method = "DELETE",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "userdelete/{user}")]
        bool DeletePerson(string user);


        [OperationContract]
        [WebInvoke(Method = "PUT",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "putuserupdate/{user}")]
        bool UpdateUserPut(string user, RequestData rData);

        /*
        ********************************************************************************************

        PUT
        http://192.168.61.3/RestServicePost/RestServiceImpl.svc/putuserupdate/bon
        User-Agent: Fiddler
        Content-Type: application/xml

        <RequestData>
        <details>nero|don|n@m.com|pass1</details>
        </RequestData>
          
          
          
         ********************************************************************************************
         */
        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "adduser2bool")]
        bool AddUser2(RequestData rData);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "userupdate/{user}")]
        bool UpdateUser(string user, PersonData pd);

/*
 
        KO : does not work ! ... - WHY ?
        PUT
        http://192.168.61.3/RestServicePost/RestServiceImpl.svc/userupdate/bon

        User-Agent: Fiddler
        Host: 192.168.61.3
        Content-Type: application/xml

        <PersonData>
        <Name>nero</Name>
        <User>njack</User>
        <Email>njack@sun.com</Email>
        <Password>sunysun</Password>
        </PersonData>
*/


        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "updateuser")]
        bool UpdateUserPost(PersonData pd);

        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            RequestFormat = WebMessageFormat.Xml,
            BodyStyle = WebMessageBodyStyle.Bare,
            UriTemplate = "testpersondatapost")]
        PersonData TestPersonDataPost(PersonData pd);

        /*
         POST http://192.168.61.3/RestServicePost/RestServiceImpl.svc/testpersondatapost HTTP/1.1
        User-Agent: Fiddler
        Content-Type: application/xml
        Host: 192.168.61.3
        Content-Length: 141

        <PersonData xmlns="">
                <Email>asmith@gmail.com</Email><Name>smith</Name><Password>secret</Password><User>adrian</User>
        </PersonData>
=>          
HTTP/1.1 200 OK
Content-Length: 172
Content-Type: application/xml; charset=utf-8
Server: Microsoft-IIS/7.0
X-Powered-By: ASP.NET
Date: Tue, 25 Oct 2011 16:51:29 GMT

<PersonData xmlns:i="http://www.w3.org/2001/XMLSchema-instance"><Email>asmith@gmail.com</Email><Name>smith</Name><Password>secret</Password><User>adrian</User></PersonData>
*/
        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Json,
            RequestFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "json/adduser")]
        PersonData AddJsonUser(RequestData rData);


        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Json,
            RequestFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "jsontestpersondatapost")]
        PersonData JsonTestPersonDataPost(PersonData pd);

        /*
                POST http://192.168.61.3/RestServicePost/RestServiceImpl.svc/jsontestpersondatapost HTTP/1.1
                User-Agent: Fiddler
                Content-Type: application/json
                Host: 192.168.61.3
                Content-Length: 86

                { "pd" : { "Email":"bo@se.us" , "Name":"amab" , "Password":"egti" , "User":"brac" } } 
         * 
         *
         * 
         ===>  
       
        HTTP/1.1 200 OK
        Content-Length: 99
        Content-Type: application/json; charset=utf-8
        Server: Microsoft-IIS/7.0
        X-Powered-By: ASP.NET
        Date: Wed, 26 Oct 2011 16:20:09 GMT

        {"JsonTestPersonDataPostResult":{"Email":"bo@se.us","Name":"amab","Password":"egti","User":"brac"}}

        */

    }
}

