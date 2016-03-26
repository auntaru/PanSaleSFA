using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace RestService
{
    [DataContract(Namespace = "")]
    public class RequestData
    {
        [DataMember]
        public string details { get; set; }
    }
}
