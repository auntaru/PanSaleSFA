using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace RestService
{
    [DataContract(Namespace = "")]
    public class PersonData
    {
        [DataMember]
        public string Name { get; set; }

        [DataMember]
        public string User { get; set; }

        [DataMember]
        public string Email { get; set; }

        [DataMember]
        public string Password { get; set; }
    }
}
